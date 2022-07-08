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

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import nl.knaw.dans.virusscan.core.model.PrePublishWorkflowPayload;
import nl.knaw.dans.virusscan.core.service.DatasetScanTaskFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class InvokeResourceImplTest {


    private static final DatasetScanTaskFactory datasetScanTaskFactory = Mockito.mock(DatasetScanTaskFactory.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
        .addResource(new InvokeResourceImpl(datasetScanTaskFactory))
        .build();

    @BeforeEach
    void beforeEach() {
        Mockito.reset(datasetScanTaskFactory);
    }

    @Test
    void invokeVirusScan() {
        var entity = new PrePublishWorkflowPayload();
        entity.setInvocationId("invocation_id");
        entity.setGlobalId("global_id");
        entity.setMajorVersion("3");
        entity.setMinorVersion("1");
        entity.setDatasetId("dataset_id");

        try (var response = EXT.target("/invoke").request().post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE))) {
            assertEquals(200, response.getStatus());

            Mockito.verify(datasetScanTaskFactory).startTask(Mockito.eq(entity));
        }
    }

    @Test
    void invokeVirusScanWithWrongPayload() {
        try (var response = EXT.target("/invoke").request().post(Entity.entity("string", MediaType.APPLICATION_JSON_TYPE))) {
            assertEquals(400, response.getStatus());
        }
    }

    @Test
    void rollback() {
        try (var response = EXT.target("/rollback").request().post(null)) {
            assertEquals(200, response.getStatus());
            Mockito.verifyNoInteractions(datasetScanTaskFactory);
        }
    }
}