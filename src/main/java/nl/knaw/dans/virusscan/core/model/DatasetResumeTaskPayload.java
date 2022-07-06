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

import nl.knaw.dans.lib.dataverse.model.file.FileMeta;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DatasetResumeTaskPayload {
    private String id;
    private Map<FileMeta, List<String>> matches;
    private String invocationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<FileMeta, List<String>> getMatches() {
        return matches;
    }

    public void setMatches(Map<FileMeta, List<String>> matches) {
        this.matches = matches;
    }

    public String getInvocationId() {
        return invocationId;
    }

    public void setInvocationId(String invocationId) {
        this.invocationId = invocationId;
    }

    @Override
    public String toString() {
        return "DatasetResumeTaskPayload{" +
            "id='" + id + '\'' +
            ", matches=" + matches +
            ", invocationId='" + invocationId + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DatasetResumeTaskPayload that = (DatasetResumeTaskPayload) o;
        return Objects.equals(id, that.id) && Objects.equals(matches, that.matches) && Objects.equals(invocationId, that.invocationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matches, invocationId);
    }
}
