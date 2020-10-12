package io.pileworx.issue.report.domain;

import io.pileworx.issue.report.domain.command.CreateIssueCmd;
import io.pileworx.issue.report.domain.issue.IssueId;
import io.pileworx.issue.report.domain.issue.IssuePostgeSqlJsonb;

public interface Issue {
    static Issue create(IssueId id,
                        CreateIssueCmd cmd) {
        return IssuePostgeSqlJsonb.create(id, cmd);
    }
}
