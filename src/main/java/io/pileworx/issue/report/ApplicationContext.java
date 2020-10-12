package io.pileworx.issue.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pileworx.issue.report.application.dto.IssueDto;
import io.pileworx.issue.report.common.JsonRowMapper;
import io.pileworx.issue.report.domain.Issue;
import io.pileworx.issue.report.domain.issue.IssuePostgeSqlJsonb;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {

    @Bean
    public JsonRowMapper<Issue, IssuePostgeSqlJsonb> issueJsonRowMapper(ObjectMapper objectMapper) {
        return new JsonRowMapper<>(objectMapper, IssuePostgeSqlJsonb.class);
    }

    @Bean
    public JsonRowMapper<IssueDto, IssueDto> issueDtoJsonRowMapper(ObjectMapper objectMapper) {
        return new JsonRowMapper<>(objectMapper, IssueDto.class);
    }
}