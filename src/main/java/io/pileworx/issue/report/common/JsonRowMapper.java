package io.pileworx.issue.report.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonRowMapper<A, C extends A> implements RowMapper<A> {

    private final ObjectMapper jsonMapper;
    private final Class<C> arClass;

    public JsonRowMapper(ObjectMapper jsonMapper, Class<C> arClass) {
        this.jsonMapper = jsonMapper;
        this.arClass = arClass;
    }

    @Override
    public A mapRow(ResultSet rs, int rowNum) throws SQLException {
        return readDocument(rs.getString("json_document"));
    }

    public Class<C> getAggregateRootClass() {
        return arClass;
    }

    public A readDocument(String source){
        try {
            return jsonMapper.readValue(source, arClass);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while de-serializing document %s", arClass.getName()), e);
        }
    }

    public String writeDocument(A source) {
        try {
            return jsonMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception while serializing document", e);
        }
    }
}
