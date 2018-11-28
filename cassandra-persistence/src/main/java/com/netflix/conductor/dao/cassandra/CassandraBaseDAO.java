/*
 * Copyright 2016 Netflix, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.conductor.dao.cassandra;

<<<<<<< HEAD
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
=======
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
>>>>>>> refactor
import com.netflix.conductor.cassandra.CassandraConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

<<<<<<< HEAD
import static com.netflix.conductor.util.Constants.CREATE_KEYSPACE_STATEMENT;
import static com.netflix.conductor.util.Constants.CREATE_TASK_LOOKUP_TABLE_STATEMENT;
import static com.netflix.conductor.util.Constants.CREATE_WORKFLOWS_TABLE_STATEMENT;
=======
import static com.netflix.conductor.util.Constants.ENTITY_KEY;
import static com.netflix.conductor.util.Constants.PAYLOAD_KEY;
import static com.netflix.conductor.util.Constants.SHARD_ID_KEY;
import static com.netflix.conductor.util.Constants.TABLE_TASK_LOOKUP;
import static com.netflix.conductor.util.Constants.TABLE_WORKFLOWS;
import static com.netflix.conductor.util.Constants.TASK_ID_KEY;
import static com.netflix.conductor.util.Constants.TOTAL_PARTITIONS_KEY;
import static com.netflix.conductor.util.Constants.TOTAL_TASKS_KEY;
import static com.netflix.conductor.util.Constants.WORKFLOW_ID_KEY;
>>>>>>> refactor

public class CassandraBaseDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraBaseDAO.class);

    protected final Session session;
    protected final ObjectMapper objectMapper;
    protected final CassandraConfiguration config;

    public CassandraBaseDAO(Session session, ObjectMapper objectMapper, CassandraConfiguration config) {
        this.session = session;
        this.objectMapper = objectMapper;
        this.config = config;

        init();
    }

    private void init() {
        try {
<<<<<<< HEAD
            session.execute(String.format(CREATE_KEYSPACE_STATEMENT, config.getKeyspace()));
            session.execute(String.format(CREATE_WORKFLOWS_TABLE_STATEMENT, config.getKeyspace()));
            session.execute(String.format(CREATE_TASK_LOOKUP_TABLE_STATEMENT, config.getKeyspace()));
=======
            session.execute(getCreateKeyspaceStatement());
            session.execute(getCreateWorkflowsTableStatement());
            session.execute(getCreateTaskLookupTableStatement());
>>>>>>> refactor
            LOGGER.info("CassandraDAO initialization complete! Tables created!");
        } catch (Exception e) {
            LOGGER.error("Error initializing and setting up keyspace and table in cassandra", e);
            throw e;
        }
    }

<<<<<<< HEAD
=======
    private String getCreateKeyspaceStatement() {
        return SchemaBuilder.createKeyspace(config.getKeyspace())
                .ifNotExists()
                .with()
                .replication(ImmutableMap.of("class", config.getReplicationStrategy(), config.getReplicationFactorKey(), config.getReplicatioFactorValue()))
                .durableWrites(true)
                .getQueryString();
    }

    private String getCreateWorkflowsTableStatement() {
        return SchemaBuilder.createTable(config.getKeyspace(), TABLE_WORKFLOWS)
                .ifNotExists()
                .addPartitionKey(WORKFLOW_ID_KEY, DataType.uuid())
                .addPartitionKey(SHARD_ID_KEY, DataType.cint())
                .addClusteringColumn(ENTITY_KEY, DataType.text())
                .addClusteringColumn(TASK_ID_KEY, DataType.text())
                .addColumn(PAYLOAD_KEY, DataType.text())
                .addStaticColumn(TOTAL_TASKS_KEY, DataType.cint())
                .addStaticColumn(TOTAL_PARTITIONS_KEY, DataType.cint())
                .getQueryString();
    }

    private String getCreateTaskLookupTableStatement() {
        return SchemaBuilder.createTable(config.getKeyspace(), TABLE_TASK_LOOKUP)
                .ifNotExists()
                .addPartitionKey(TASK_ID_KEY, DataType.uuid())
                .addColumn(WORKFLOW_ID_KEY, DataType.uuid())
                .getQueryString();
    }

>>>>>>> refactor
    String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    <T> T readValue(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class WorkflowMetadata {
        int totalTasks;
        int totalPartitions;

        public int getTotalTasks() {
            return totalTasks;
        }

        public void setTotalTasks(int totalTasks) {
            this.totalTasks = totalTasks;
        }

        public int getTotalPartitions() {
            return totalPartitions;
        }

        public void setTotalPartitions(int totalPartitions) {
            this.totalPartitions = totalPartitions;
        }
    }
}
