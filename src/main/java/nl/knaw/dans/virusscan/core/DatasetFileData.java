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

public class DatasetFileData {
    private int id;
    private String persistentId;
    private String filename;
    private String contentType;
    private long filesize;
    private String storageIdentifier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getStorageIdentifier() {
        return storageIdentifier;
    }

    public void setStorageIdentifier(String storageIdentifier) {
        this.storageIdentifier = storageIdentifier;
    }

    @Override
    public String toString() {
        return "DatasetFileData{" +
            "id=" + id +
            ", persistentId='" + persistentId + '\'' +
            ", filename='" + filename + '\'' +
            ", contentType='" + contentType + '\'' +
            ", filesize=" + filesize +
            ", storageIdentifier='" + storageIdentifier + '\'' +
            '}';
    }
}
