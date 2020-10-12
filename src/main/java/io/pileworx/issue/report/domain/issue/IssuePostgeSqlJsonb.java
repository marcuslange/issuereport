package io.pileworx.issue.report.domain.issue;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.pileworx.issue.report.domain.Issue;
import io.pileworx.issue.report.domain.command.CreateIssueCmd;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
public class IssuePostgeSqlJsonb implements Issue {

    private final IssueId id;
    private final String name;
    private final String description;

    public static Issue create(IssueId id,
                               CreateIssueCmd cmd) {
        return new IssuePostgeSqlJsonb(
                id,
                cmd.getName(),
                cmd.getDescription());
    }
}