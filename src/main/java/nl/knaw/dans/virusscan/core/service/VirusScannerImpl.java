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

import nl.knaw.dans.virusscan.core.config.VirusScannerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VirusScannerImpl implements VirusScanner {

    private final VirusScannerConfig virusScannerConfig;

    private final ClamdService clamdService;

    public VirusScannerImpl(VirusScannerConfig virusScannerConfig, ClamdService clamdService) {
        this.virusScannerConfig = virusScannerConfig;
        this.clamdService = clamdService;
    }

    @Override
    public List<String> scanForVirus(InputStream inputStream) throws IOException {
        var result = clamdService.scanStream(inputStream);
        var errorMessages = new ArrayList<String>();

        for (var line : result) {
            var matcher = virusScannerConfig.getResultPositivePattern().matcher(line);

            if (matcher.matches()) {
                errorMessages.add(matcher.group(1));
            }
        }

        return errorMessages;
    }
}
