package io.pileworx.issue.report.application;

import io.pileworx.issue.report.application.assembler.IssueAssembler;
import io.pileworx.issue.report.application.dto.IssueDto;
import io.pileworx.issue.report.application.exception.IssueNotFoundException;
import io.pileworx.issue.report.application.finder.IssueFinder;
import io.pileworx.issue.report.domain.Issue;
import io.pileworx.issue.report.domain.IssueRepository;
import io.pileworx.issue.report.domain.command.CreateIssueCmd;
import io.pileworx.issue.report.domain.issue.IssueId;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@AllArgsConstructor(onConstructor=@__(@Inject))
public class IssueService {

    private final IssueRepository repository;
    private final IssueAssembler assembler;
    private final IssueFinder finder;

    public IssueDto create(CreateIssueCmd cmd) {
        var issue = Issue.create(repository.nextId(), cmd);
        repository.save(issue);
        return assembler.toDto(issue);
    }

    public List<IssueDto> findAll() {
        return finder.findAll();
    }

    public IssueDto findById(IssueId id) {
        return finder
                .findById(id)
                .orElseThrow(() -> new IssueNotFoundException(id));
    }
}