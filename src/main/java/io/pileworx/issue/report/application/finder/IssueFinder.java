package io.pileworx.issue.report.application.finder;

import io.pileworx.issue.report.application.dto.IssueDto;
import io.pileworx.issue.report.domain.issue.IssueId;

import java.util.List;
import java.util.Optional;

public interface IssueFinder {
    List<IssueDto> findAll();
    Optional<IssueDto> findById(IssueId id);
}
