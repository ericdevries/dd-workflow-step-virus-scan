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
package nl.knaw.dans.virusscan.resource;

import nl.knaw.dans.virusscan.core.service.DataverseApiService;
import nl.knaw.dans.virusscan.core.service.VirusScanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class InvokeResourceImpl implements InvokeResource {
    static final int BUFFER_SIZE = 1024 * 8;
    private final Client client;
    private final DataverseApiService dataverseApiService;

    private final VirusScanner virusScanner;

    public InvokeResourceImpl(Client client, DataverseApiService dataverseApiService, VirusScanner virusScanner) {
        this.client = client;
        this.dataverseApiService = dataverseApiService;
        this.virusScanner = virusScanner;
    }

    public static String scanStream(InputStream inputStream) throws IOException {
        try (var socket = new Socket("localhost",
            3310); var outputStream = new BufferedOutputStream(socket.getOutputStream()); var socketInputStream = socket.getInputStream()) {

            var buffer = new byte[BUFFER_SIZE];
            var bytesRead = inputStream.read(buffer);

            outputStream.write("zINSTREAM\0".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            var sum = 0;

            while (bytesRead >= 0) {
                // create a 4 byte sequence indicating the size of the payload (in network byte order)
                var header = ByteBuffer.allocate(4)
                    .order(ByteOrder.BIG_ENDIAN)
                    .putInt(bytesRead)
                    .array();

                sum += bytesRead;
                // write the size of the payload
                outputStream.write(header);
                outputStream.write(buffer, 0, bytesRead);
                outputStream.flush();

                // check if the virus scan daemon already has something to say
                if (socketInputStream.available() > 0) {
                    throw new IOException(
                        "Error reply from server: " + new String(socketInputStream.readAllBytes()));
                }

                bytesRead = inputStream.read(buffer);
            }

            // last payload should be 0 length to indicate we are done
            outputStream.write(new byte[] { 0, 0, 0, 0 });
            outputStream.flush();

            System.out.println("FLUSHED");

            // now return what it has to say
            // this should be checked against a certain output format to verify it is OK
            return new String(socketInputStream.readAllBytes());
        }
    }

    @Override
    public Response invokeVirusScan(PrePublishWorkflowPayload payload) {
        System.out.println("PAYLOAD: " + payload);
        var files = dataverseApiService.listFiles(payload.getDatasetId(), payload.getVersion());

        for (var file: files) {
            System.out.println("FILE: " + file);

            var result = dataverseApiService.getFile("" + file.getDataFile().getId());
            System.out.println("INPUT STREAM IN RESOURCE: " + result);

            try {
                virusScanner.scanForVirus(result);
                System.out.println("NO VIRUS");
            } catch (Exception e) {
                System.out.println("VIRUS FOUND");
                e.printStackTrace();
            }

        }

        /*
        var url = "https://dar.dans.knaw.nl/api/access/dataset/:persistentId?persistentId=" + payload.getGlobalId(); //doi:10.5072/FK2/O9LNLQ"

        var target = client.target(url);
        var result = target.path("/")
            .request()
            .get(InputStream.class);

        System.out.println("INPUT STREAM: " + result);

        try {
            var output = scanStream(result);
            System.out.println("OUTPUT: " + output);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        *
         */

        return Response.status(200).build();
    }
}
