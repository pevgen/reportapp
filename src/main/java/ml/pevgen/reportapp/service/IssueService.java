package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.model.DtoIssuesWithTotal;

import java.util.List;

public interface IssueService {

    List<DtoIssue> getAllIssues();

    DtoIssuesWithTotal getIssuesWithTotal();

    void deleteAllIssues();

    String deleteById(String issueId);
}
