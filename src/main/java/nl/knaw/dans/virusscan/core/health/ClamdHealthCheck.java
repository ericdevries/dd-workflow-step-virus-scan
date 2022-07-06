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
package nl.knaw.dans.virusscan.core.health;

import com.codahale.metrics.health.HealthCheck;
import nl.knaw.dans.virusscan.core.config.ClamdConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClamdHealthCheck extends HealthCheck {
    private static final Logger log = LoggerFactory.getLogger(ClamdHealthCheck.class);

    private final ClamdConfig clamdConfig;

    public ClamdHealthCheck(ClamdConfig clamdConfig) {
        this.clamdConfig = clamdConfig;
    }

    @Override
    protected Result check() {
        try {
            try (var socket = new Socket(clamdConfig.getHost(), clamdConfig.getPort())) {
                socket.getOutputStream().write("PING".getBytes(StandardCharsets.UTF_8));
                var result = new String(socket.getInputStream().readAllBytes());

                if ("PONG\n".equalsIgnoreCase(result)) {
                    return Result.healthy();
                }
                else {
                    throw new IOException(String.format("Unexpected output from ClamAV: %s", result));
                }
            }
        }
        catch (IOException e) {
            return Result.builder()
                .withMessage(e.getMessage())
                .unhealthy(e)
                .build();
        }
    }
}
