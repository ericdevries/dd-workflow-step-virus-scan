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

public class DatasetFile {
    private String label;
    private int version;
    private int datasetVersionId;
    private DatasetFileData dataFile;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getDatasetVersionId() {
        return datasetVersionId;
    }

    public void setDatasetVersionId(int datasetVersionId) {
        this.datasetVersionId = datasetVersionId;
    }

    public DatasetFileData getDataFile() {
        return dataFile;
    }

    public void setDataFile(DatasetFileData dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public String toString() {
        return "DatasetFile{" +
            "label='" + label + '\'' +
            ", version=" + version +
            ", datasetVersionId=" + datasetVersionId +
            ", dataFile=" + dataFile +
            '}';
    }
}
