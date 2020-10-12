package io.pileworx.issue.report.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueDto {
    private final String id;
    private final String name;
    private final String description;
}