package ml.pevgen.reportapp.service;

import lombok.extern.slf4j.Slf4j;
import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.model.DtoIssuesWithTotal;
import ml.pevgen.reportapp.repo.IssueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;
    private final TotalCalculator totalCalculator;

    public IssueServiceImpl(IssueRepository issueRepository, ModelMapper modelMapper, TotalCalculator totalCalculator) {
        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
        this.totalCalculator = totalCalculator;
    }

    @Override
    public List<DtoIssue> getAllIssues() {
        return issueRepository.findAll()
                .stream()
                .map(dbIssue -> modelMapper.map(dbIssue, DtoIssue.class))
                .toList();
    }

    @Override
    public DtoIssuesWithTotal getIssuesWithTotal() {
        List<DtoIssue> issues = issueRepository.findAll()
                .stream()
                .map(dbIssue -> modelMapper.map(dbIssue, DtoIssue.class))
                .toList();

        totalCalculator.calculate(issues);

        return DtoIssuesWithTotal.builder()
                .issueList(issues)
                .cycleDaysTotal(totalCalculator.getCycleDaysTotal())
                .leadDaysTotal(totalCalculator.getCycleDaysTotal())
                .testingDaysTotal(totalCalculator.getTestingDaysTotal())
                .waitingTestDaysTotal(totalCalculator.getWaitingTestDaysTotal())
                .build();
    }


    public void deleteAllIssues() {
        issueRepository.deleteAll();
    }

    public String deleteById(String issueId) {
        issueRepository.deleteById(issueId);
        return issueId;
    }
}
