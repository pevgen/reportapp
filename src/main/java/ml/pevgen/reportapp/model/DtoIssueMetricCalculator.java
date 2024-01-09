package ml.pevgen.reportapp.model;

import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class DtoIssueMetricCalculator {

    public static DtoIssue calculate(DtoIssue issue) {
        return DtoIssue.builder()
                .issueId(issue.getIssueId())
                .issueKey(issue.getIssueKey())
                .issueType(issue.getIssueType())
                .summary(issue.getSummary())
                .created(issue.getCreated())
                .startProcessInit(issue.getStartProcessInit())
                .startProcessUpdate(issue.getStartProcessUpdate())
                .toTestInit(issue.getToTestInit())
                .testingInit(issue.getTestingInit())
                .resolved(issue.getResolved())
                .updated(issue.getUpdated())
                .storyPoints(issue.getStoryPoints())
                .leadDays(calculateLeadDays(issue))
                .cycleDays(calculateCycleDays(issue))
                .testingDays(calculateTestingDays(issue))
                .waitingTestDays(calculateWaitingTestDays(issue))
                .build();
    }

    public static Double calculateLeadDays(DtoIssue issue) {
        Duration duration = Duration.between(issue.getCreated(), issue.getResolved());
        return duration.toHours() / 24d;
    }

    public static Double calculateCycleDays(DtoIssue issue) {
        if (issue.getStartProcessInit() != null) {
            Duration duration = Duration.between(issue.getStartProcessInit(), issue.getResolved());
            return duration.toHours() / 24d;
        }
        return null;
    }

    public static Double calculateWaitingTestDays(DtoIssue issue) {
        if (issue.getTestingInit() != null && issue.getToTestInit() != null) {
            Duration duration = Duration.between(issue.getToTestInit(), issue.getTestingInit());
            return duration.toHours() / 24d;
        }
        return null;
    }

    public static Double calculateTestingDays(DtoIssue issue) {
        if (issue.getTestingInit() != null) {
            Duration duration = Duration.between(issue.getTestingInit(), issue.getResolved());
            return duration.toHours() / 24d;
        }
        return null;
    }
}
