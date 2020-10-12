package io.pileworx.issue.report.adapter.primary.rest.assembler;

import io.pileworx.issue.report.adapter.primary.rest.IssueRestApi;
import io.pileworx.issue.report.adapter.primary.rest.resource.IssueResource;
import io.pileworx.issue.report.application.dto.IssueDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import javax.inject.Named;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Named
public class IssueResourceAssembler extends RepresentationModelAssemblerSupport<IssueDto, IssueResource> {

    public IssueResourceAssembler() {
        super(IssueRestApi.class, IssueResource.class);
    }

    @Override
    public IssueResource toModel(IssueDto dto) {
        var resource = new IssueResource(dto.getName(), dto.getDescription());

        resource.add(
                linkTo(methodOn(IssueRestApi.class).getById(dto.getId())).withSelfRel());

        return resource;
    }

    @Override
    public CollectionModel<IssueResource> toCollectionModel(Iterable<? extends IssueDto> entities) {
        var resource = super.toCollectionModel(entities);

        resource.add(linkTo(methodOn(IssueRestApi.class).getAll()).withSelfRel());

        return resource;
    }
}