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
package nl.knaw.dans.virusscan.core;

import java.util.List;

public class DatasetVersion {
    private int id;
    private int datasetId;
    private String datasetPersistentId;
    private int versionNumber;
    private int versionMinorNumber;
    private String versionState;
    private List<DatasetFile> files;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetPersistentId() {
        return datasetPersistentId;
    }

    public void setDatasetPersistentId(String datasetPersistentId) {
        this.datasetPersistentId = datasetPersistentId;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getVersionMinorNumber() {
        return versionMinorNumber;
    }

    public void setVersionMinorNumber(int versionMinorNumber) {
        this.versionMinorNumber = versionMinorNumber;
    }

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
    }

    public List<DatasetFile> getFiles() {
        return files;
    }

    public void setFiles(List<DatasetFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "DatasetVersion{" +
            "id=" + id +
            ", datasetId=" + datasetId +
            ", datasetPersistentId='" + datasetPersistentId + '\'' +
            ", versionNumber=" + versionNumber +
            ", versionMinorNumber=" + versionMinorNumber +
            ", versionState='" + versionState + '\'' +
            ", files=" + files +
            '}';
    }
}
