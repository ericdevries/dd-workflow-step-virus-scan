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

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/invoke")
public interface InvokeResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response invokeVirusScan(@Valid PrePublishWorkflowPayload payload);

    class PrePublishWorkflowPayload {
        private String invocationId;
        private String globalId;
        private String datasetId;
        private String majorVersion;
        private String minorVersion;

        public String getInvocationId() {
            return invocationId;
        }

        public void setInvocationId(String invocationId) {
            this.invocationId = invocationId;
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getDatasetId() {
            return datasetId;
        }

        public void setDatasetId(String datasetId) {
            this.datasetId = datasetId;
        }

        public String getMajorVersion() {
            return majorVersion;
        }

        public void setMajorVersion(String majorVersion) {
            this.majorVersion = majorVersion;
        }

        public String getMinorVersion() {
            return minorVersion;
        }

        public void setMinorVersion(String minorVersion) {
            this.minorVersion = minorVersion;
        }

        public String getVersion() {
            return String.format("%s.%s", this.majorVersion, this.minorVersion);
        }

        @Override
        public String toString() {
            return "PrePublishWorkflowPayload{" +
                "invocationId='" + invocationId + '\'' +
                ", globalId='" + globalId + '\'' +
                ", datasetId='" + datasetId + '\'' +
                ", majorVersion='" + majorVersion + '\'' +
                ", minorVersion='" + minorVersion + '\'' +
                '}';
        }
    }
}
