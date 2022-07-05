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

import nl.knaw.dans.lib.dataverse.model.file.FileMeta;
import nl.knaw.dans.virusscan.resource.PrePublishWorkflowPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DatasetScanTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(DataverseApiServiceImpl.class);
    private final DataverseApiService dataverseApiService;
    private final VirusScanner virusScanner;
    private final PrePublishWorkflowPayload payload;

    public DatasetScanTask(DataverseApiService dataverseApiService, VirusScanner virusScanner, PrePublishWorkflowPayload payload) {
        this.dataverseApiService = dataverseApiService;
        this.virusScanner = virusScanner;
        this.payload = payload;
    }

    @Override
    public void run() {

        try {
            // fetch all files by ID
            var files = dataverseApiService.listFiles(payload.getGlobalId(), payload.getInvocationId(), payload.getVersion());
            var fileMatches = new HashMap<FileMeta, List<String>>();

            for (var file : files) {
                log.debug("Checking status for file {}", file);
                var fileInputStream = dataverseApiService.getFile(file.getDataFile().getId());
                var viruses = virusScanner.scanForVirus(fileInputStream);

                if (viruses.size() > 0) {
                    log.warn("Found {} viruses in file {}", viruses.size(), file);
                    fileMatches.put(file, viruses);
                }
            }

            if (fileMatches.isEmpty()) {
                dataverseApiService.completeWorkflow(payload.getInvocationId());
            }
            else {
                var messages = fileMatches.entrySet().stream().map(file -> {
                    var filename = file.getKey().getDataFile().getFilename();
                    var errors = String.join(" and ", file.getValue());

                    return String.format("%s -> %s FOUND", filename, errors);
                }).collect(Collectors.joining(", "));

                dataverseApiService.failWorkflow(payload.getInvocationId(), messages, messages);
            }

        }
        catch (Exception e) {
            log.error("Error checking files", e);
        }

    }
}
