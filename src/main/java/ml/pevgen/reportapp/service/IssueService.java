package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DtoIssue;

import java.util.List;

public interface IssueService {

    List<DtoIssue> getAllIssues();

    void deleteAllIssues();

    String deleteById(String issueId);
}
