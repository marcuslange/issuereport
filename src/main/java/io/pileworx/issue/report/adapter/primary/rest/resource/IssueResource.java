package io.pileworx.issue.report.adapter.primary.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueResource extends RepresentationModel<IssueResource>{
    private final String name;
    private final String description;
}