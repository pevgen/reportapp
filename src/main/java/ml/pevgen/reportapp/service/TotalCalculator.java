package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DtoIssue;

import java.util.List;

public interface TotalCalculator {
    void calculate(List<DtoIssue> issues);

    ml.pevgen.reportapp.model.DtoIssueTotal getLeadDaysTotal();

    ml.pevgen.reportapp.model.DtoIssueTotal getCycleDaysTotal();

    ml.pevgen.reportapp.model.DtoIssueTotal getWaitingTestDaysTotal();

    ml.pevgen.reportapp.model.DtoIssueTotal getTestingDaysTotal();
}
