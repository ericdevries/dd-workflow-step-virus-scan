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

import nl.knaw.dans.virusscan.core.DatasetDraftRequest;
import nl.knaw.dans.virusscan.core.DatasetFile;
import nl.knaw.dans.virusscan.core.config.DataverseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.io.InputStream;
import java.util.List;

public class DataverseApiServiceImpl implements DataverseApiService {
    private static final Logger log = LoggerFactory.getLogger(DataverseApiServiceImpl.class);

    private final DataverseConfig dataverseConfig;
    private final Client client;

    public DataverseApiServiceImpl(DataverseConfig dataverseConfig, Client client) {
        this.dataverseConfig = dataverseConfig;
        this.client = client;
    }

    @Override
    public List<DatasetFile> listFiles(String datasetId, String version) {
        var url = String.format("https://dar.dans.knaw.nl/api/datasets/%s/versions/:draft", datasetId);
        log.debug("Requesting list of files on url {}", url);
        var target = client.target(url);

        var result = target.path("/")
            .request()
            .header("X-Dataverse-key", dataverseConfig.getApiToken())
            .get(DatasetDraftRequest.class);

        log.trace("Result from API call: {}", result);
        return result.getData().getFiles();
    }

    @Override
    public InputStream getFile(String fileId) {
        var url = String.format("https://dar.dans.knaw.nl/api/access/datafile/%s", fileId);

        log.debug("Requesting inputstream for file {} on url {}", fileId, url);
        var target = client.target(url);

        return target.path("/")
            .request()
            .header("X-Dataverse-key", dataverseConfig.getApiToken())
            .get(InputStream.class);
    }
}
