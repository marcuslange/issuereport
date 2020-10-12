package io.pileworx.issue.report.adapter.primary.rest;

import io.pileworx.issue.report.adapter.primary.rest.assembler.IssueResourceAssembler;
import io.pileworx.issue.report.adapter.primary.rest.resource.IssueResource;
import io.pileworx.issue.report.application.IssueService;
import io.pileworx.issue.report.domain.command.CreateIssueCmd;
import io.pileworx.issue.report.domain.issue.IssueId;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/issue")
@AllArgsConstructor(onConstructor=@__(@Inject))
public class IssueRestApi {

    private final IssueService service;
    private final IssueResourceAssembler issueAssembler;

    @GetMapping(produces = HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<IssueResource>> getAll() {
        return ResponseEntity.ok(
                issueAssembler.toCollectionModel(
                        service.findAll()));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = HAL_JSON_VALUE)
    public ResponseEntity<IssueResource> create(@RequestBody CreateIssueCmd cmd) {
        var resource = issueAssembler.toModel(service.create(cmd));
        return ResponseEntity
                .created(URI.create(resource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                .body(resource);
    }

    @GetMapping(value = "/{id}", produces = HAL_JSON_VALUE)
    public ResponseEntity<IssueResource> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                issueAssembler.toModel(
                        service.findById(new IssueId(id))));
    }
}
