package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.model.DtoIssueMetricCalculator;
import ml.pevgen.reportapp.repo.IssueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;

    public IssueServiceImpl(IssueRepository issueRepository, ModelMapper modelMapper) {
        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DtoIssue> getAllIssues() {
        return issueRepository.findAll()
                .stream()
                .map(dbIssue -> modelMapper.map(dbIssue, DtoIssue.class))
                .map(DtoIssueMetricCalculator::calculate)
                .toList();
    }
}
