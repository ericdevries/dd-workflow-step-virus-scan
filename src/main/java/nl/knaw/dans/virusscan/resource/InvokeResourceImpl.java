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

import nl.knaw.dans.virusscan.core.service.DatasetScanTaskFactory;
import nl.knaw.dans.virusscan.core.service.DataverseApiService;
import nl.knaw.dans.virusscan.core.service.VirusScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;

public class InvokeResourceImpl implements InvokeResource {
    private static final Logger log = LoggerFactory.getLogger(InvokeResourceImpl.class);

    private DatasetScanTaskFactory taskFactory;

    public InvokeResourceImpl(DatasetScanTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    @Override
    public Response invokeVirusScan(PrePublishWorkflowPayload payload) {
        this.taskFactory.startTask(payload);

        /*
        log.info("Requesting list of files for dataset with id {}", payload.getGlobalId());
        var files = dataverseApiService.listFiles(payload.getDatasetId(), payload.getVersion());

        for (var file: files) {
            var result = dataverseApiService.getFile("" + file.getDataFile().getId());

            try {
                virusScanner.scanForVirus(result);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

         */

        return Response.status(200).build();
    }
}
