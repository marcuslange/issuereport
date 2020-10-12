package io.pileworx.issue.report.domain.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateIssueCmd {
    private final String name;
    private final String description;
}