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

import nl.knaw.dans.lib.dataverse.DataverseClient;
import nl.knaw.dans.lib.dataverse.DataverseClientConfig;
import nl.knaw.dans.lib.dataverse.DataverseException;
import nl.knaw.dans.lib.dataverse.model.file.FileMeta;
import nl.knaw.dans.lib.dataverse.model.workflow.ResumeMessage;
import nl.knaw.dans.virusscan.core.config.DataverseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class DataverseApiServiceImpl implements DataverseApiService {
    private static final Logger log = LoggerFactory.getLogger(DataverseApiServiceImpl.class);

    private final DataverseConfig dataverseConfig;
    private final DataverseClient client;

    public DataverseApiServiceImpl(DataverseConfig dataverseConfig, Client client) {
        this.dataverseConfig = dataverseConfig;
//        this.client = client;

        var config = new DataverseClientConfig(URI.create(dataverseConfig.getBaseUrl()), dataverseConfig.getApiToken());
        this.client = new DataverseClient(config);

    }

    @Override
    public List<FileMeta> listFiles(String datasetId, String invocationId, String version) throws IOException, DataverseException {
        // TODO make sure this works with this dataverse client, otherwise use raw http
        var dataset = client.dataset(datasetId, invocationId);
        var files = dataset.getFiles(version);

        return files.getData();
        /*
        files.getData().get(0).getDataFile().getPidURL()
        var url = String.format("https://dar.dans.knaw.nl/api/datasets/%s/versions/:draft", datasetId);
        log.debug("Requesting list of files on url {}", url);
        var target = client.target(url);

        var result = target.path("/")
            .request()
            .header("X-Dataverse-key", dataverseConfig.getApiToken())
            .get(DatasetDraftRequest.class);

        log.trace("Result from API call: {}", result);
        return result.getData().getFiles();

         */
    }

    @Override
    public InputStream getFile(int fileId) throws IOException, DataverseException {
        return client.basicFileAccess(fileId).getFile().getEntity().getContent();
        /*
        // TODO make sure this works with this dataverse client, otherwise use raw http (probably is not supported yet for InputStream)
        var url = String.format("https://dar.dans.knaw.nl/api/access/datafile/%s", fileId);

        log.debug("Requesting inputstream for file {} on url {}", fileId, url);
        var target = client.target(url);

        return target.path("/")
            .request()
            .header("X-Dataverse-key", dataverseConfig.getApiToken())
            .get(InputStream.class);

         */
    }

    @Override
    public void completeWorkflow(String invocationId) throws IOException, DataverseException {
        var resumeMessage = new ResumeMessage("Success", null, null);
        this.client.workflows().resume(invocationId, resumeMessage);
    }

    @Override
    public void failWorkflow(String invocationId, String reason, String message) throws IOException, DataverseException {
        var resumeMessage = new ResumeMessage("Failure", reason, message);
        this.client.workflows().resume(invocationId, resumeMessage);
    }
}
