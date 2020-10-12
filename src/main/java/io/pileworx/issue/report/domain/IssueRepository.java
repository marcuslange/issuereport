package io.pileworx.issue.report.domain;

import io.pileworx.issue.report.domain.issue.IssueId;

import java.util.Optional;

public interface IssueRepository {
    IssueId nextId();
    Optional<Issue> findById(IssueId id);
    void save(Issue ar);
    void delete(Issue ar);
}
