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
package nl.knaw.dans.virusscan.core.model;

public class DataverseVersionResponse {
    private String status;
    private DataverseVersion data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataverseVersion getData() {
        return data;
    }

    public void setData(DataverseVersion data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataverseVersionResponse{" +
            "status='" + status + '\'' +
            ", data=" + data +
            '}';
    }

    static class DataverseVersion {
        private String version;
        private String build;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        @Override
        public String toString() {
            return "DataverseVersion{" +
                "version='" + version + '\'' +
                ", build='" + build + '\'' +
                '}';
        }
    }
}
