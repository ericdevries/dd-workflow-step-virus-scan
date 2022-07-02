/*
 * Copyright (C) 2022 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.virusscan.core.service;

import nl.knaw.dans.virusscan.core.config.ClamdConfig;
import nl.knaw.dans.virusscan.core.config.VirusScannerConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClamdServiceImplTest {

    @Test
    void scanStream() throws IOException {

        var config = new VirusScannerConfig();
        config.setClamd(new ClamdConfig());
        config.getClamd().setChunksize(100);
        config.getClamd().setBuffersize(20);
        config.getClamd().setOverlapsize(20);

        var inputStream = new ByteArrayInputStream(
            "Nesciunt recusandae optio eum veniam et. Magni repellat omnis aut. Beatae enim provident eos dolorum officia ratione. Magnam in impedit sit facilis.".getBytes());
        var outputStream = new ByteArrayOutputStream();
        var socketInputStream = new ByteArrayInputStream("".getBytes());

        var socket = Mockito.mock(Socket.class);

        var service = new ClamdServiceImpl(config.getClamd());
        var spyService = Mockito.spy(service);
        Mockito.when(socket.getInputStream()).thenReturn(socketInputStream);
        Mockito.when(socket.getOutputStream()).thenReturn(outputStream);
        Mockito.doReturn(socket).when(spyService).getConnection();

        spyService.processStreamInBatches(inputStream);

        var output = outputStream.toByteArray();
        assertEquals("zINSTREAM\0", new String(Arrays.copyOfRange(output, 0, 10)));
        assertEquals("Nesciunt recusandae ", new String(Arrays.copyOfRange(output, 14, 34)));
        // this one is 2x20 bytes further than position 14, but it also includes 8 bytes of "headers" so 48 bytes further than 34 = 62
        assertEquals(" Magni repellat omni", new String(Arrays.copyOfRange(output, 62, 82)));
        assertEquals("zINSTREAM\0", new String(Arrays.copyOfRange(output, 134, 134 + 10)));
        // verify a part of the first stream is sent again in the second stream
        assertEquals("rovident eos dolorum", new String(Arrays.copyOfRange(output, 148, 168)));

        System.out.println("String: " + new String(output));
    }
}