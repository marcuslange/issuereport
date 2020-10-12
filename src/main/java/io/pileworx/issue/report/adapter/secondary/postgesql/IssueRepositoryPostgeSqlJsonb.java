package io.pileworx.issue.report.adapter.secondary.postgesql;

import io.pileworx.issue.report.common.JsonRowMapper;
import io.pileworx.issue.report.domain.Issue;
import io.pileworx.issue.report.domain.IssueRepository;
import io.pileworx.issue.report.domain.issue.IssueId;
import io.pileworx.issue.report.domain.issue.IssuePostgeSqlJsonb;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Named
@AllArgsConstructor(onConstructor=@__(@Inject))
public class IssueRepositoryPostgeSqlJsonb implements IssueRepository {
    private static final String INSERT_SQL = "INSERT INTO reported_issues (id, json_document, last_modified) VALUES (:id, :document::jsonb, NOW())";
    private static final String UPDATE_SQL = "UPDATE reported_issues SET json_document = :document::jsonb, last_modified = NOW() WHERE id = :id";
    private static final String DELETE_SQL = "DELETE FROM reported_issues WHERE id = :id";
    private static final String EXISTS_SQL = "SELECT COUNT(id) FROM reported_issues WHERE id = :id";
    private static final String FIND_BY_ID_SQL = "SELECT json_document FROM reported_issues WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JsonRowMapper<Issue, IssuePostgeSqlJsonb> rowMapper;

    @Override
    public IssueId nextId() {
        return new IssueId(UUID.randomUUID().toString());
    }

    @Override
    public Optional<Issue> findById(IssueId id) {
        if (null == id)
            throw new IllegalArgumentException("ID cannot be null");

        try {
            var  ar = jdbcTemplate.queryForObject(FIND_BY_ID_SQL, Map.of("id", id.getId()), rowMapper);
            return null == ar ? Optional.empty() : Optional.of(ar);
        } catch (EmptyResultDataAccessException erdaex) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Issue ar) {
        var document = rowMapper.writeDocument(ar);

        if (objectExists(castAr(ar).getId()))
            update(ar, document);
        else
            create(ar, document);
    }

    @Override
    public void delete(Issue ar) {
        jdbcTemplate.update(DELETE_SQL, Map.of("id", castAr(ar).getId().getId()));
    }

    private void create(Issue ar, String document) {
        var params = Map.of(
                "id", castAr(ar).getId().getId(),
                "document", document);

        jdbcTemplate.update(INSERT_SQL, params);
    }

    private void update(Issue ar, String document) {
        var params = Map.of(
                "id", castAr(ar).getId().getId(),
                "document", document);

        jdbcTemplate.update(UPDATE_SQL, params);
    }

    private boolean objectExists(IssueId id) {
        var rowCount = jdbcTemplate.queryForObject(EXISTS_SQL, Map.of("id", id.getId()), Integer.class);
        return Objects.equals(1, rowCount);
    }

    private IssuePostgeSqlJsonb castAr(Issue ar) {
        return (IssuePostgeSqlJsonb) ar;
    }
}
