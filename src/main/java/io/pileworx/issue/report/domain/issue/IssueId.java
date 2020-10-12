package io.pileworx.issue.report.domain.issue;

import lombok.Getter;

@Getter
public class IssueId {
    private final String id;

    public IssueId(String id) {
        this.id = id;
    }
}
