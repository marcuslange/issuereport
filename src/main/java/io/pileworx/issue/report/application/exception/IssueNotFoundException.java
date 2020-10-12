package io.pileworx.issue.report.application.exception;

import io.pileworx.issue.report.domain.issue.IssueId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IssueNotFoundException extends RuntimeException {
    public IssueNotFoundException(IssueId id) {
        super(String.format("Issue with ID %s was not found.", id.getId()));
    }
}
