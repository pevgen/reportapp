package ml.pevgen.reportapp.model;

import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class DbIssueMetricCalculator {

    public static DbIssue calculate(DbIssue issue) {
        return issue.toBuilder()
                .leadDays(calculateLeadDays(issue))
                .cycleDays(calculateCycleDays(issue))
                .testingDays(calculateTestingDays(issue))
                .waitingTestDays(calculateWaitingTestDays(issue))
                .build();
    }

    public static Double calculateLeadDays(DbIssue issue) {
        Duration duration = Duration.between(issue.getCreated(), issue.getResolved());
        return duration.toHours() / 24d;
    }

    public static Double calculateCycleDays(DbIssue issue) {
        if (issue.getStartProcessUpdate() != null) {
            Duration duration = Duration.between(issue.getStartProcessUpdate(), issue.getResolved());
            return duration.toHours() / 24d;
        }
        return null;
    }

    public static Double calculateWaitingTestDays(DbIssue issue) {
        if (issue.getTestingInit() != null && issue.getToTestInit() != null) {
            Duration duration = Duration.between(issue.getToTestInit(), issue.getTestingInit());
            return duration.toHours() / 24d;
        }
        return null;
    }

    public static Double calculateTestingDays(DbIssue issue) {
        if (issue.getTestingInit() != null) {
            Duration duration = Duration.between(issue.getTestingInit(), issue.getResolved());
            return duration.toHours() / 24d;
        }
        return null;
    }
}
