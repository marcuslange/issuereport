package io.pileworx.issue.report.adapter.secondary.postgesql;

import io.pileworx.issue.report.application.dto.IssueDto;
import io.pileworx.issue.report.application.finder.IssueFinder;
import io.pileworx.issue.report.common.JsonRowMapper;
import io.pileworx.issue.report.domain.issue.IssueId;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
@AllArgsConstructor(onConstructor=@__(@Inject))
public class IssueFinderPostgeSqlJsonb implements IssueFinder {

    private static final String SELECT_SQL = "SELECT row_to_json(t) AS json_document FROM ( %s ) t";
    private static final String FIND_BY_ID_SQL = "SELECT row_to_json(t) AS json_document FROM ( %s ) t WHERE id = :id";

    private static final String SQL_MAP = "SELECT" +
            " json_document->'id'->>'id' as \"id\"," +
            " json_document->>'name' as \"name\"," +
            " json_document->>'description' as \"description\"" +
            " FROM reported_issues";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JsonRowMapper<IssueDto, IssueDto> rowMapper;
    private final JsonRowMapper<IssueDto, IssueDto> summaryRowMapper;

    @Override
    public List<IssueDto> findAll() {
        return jdbcTemplate.query(String.format(SELECT_SQL, SQL_MAP), summaryRowMapper);
    }

    @Override
    public Optional<IssueDto> findById(IssueId id) {
        try {
            var dto = jdbcTemplate.queryForObject(
                    String.format(FIND_BY_ID_SQL, SQL_MAP),
                    Map.of("id", id.getId()),
                    rowMapper);

            return null == dto ? Optional.empty() : Optional.of(dto);
        } catch (EmptyResultDataAccessException erdaex) {
            return Optional.empty();
        }
    }
}