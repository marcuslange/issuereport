package io.pileworx.issue.report.application.assembler;

import io.pileworx.issue.report.application.dto.IssueDto;
import io.pileworx.issue.report.domain.Issue;
import io.pileworx.issue.report.domain.issue.IssuePostgeSqlJsonb;

import javax.inject.Named;

@Named
public class IssueAssembler {

    public IssueDto toDto(Issue issue) {
        var accessible = (IssuePostgeSqlJsonb) issue;
        return new IssueDto(
                accessible.getId().getId(),
                accessible.getName(),
                accessible.getDescription());
    }
}
