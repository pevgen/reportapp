package ml.pevgen.reportapp.model;

import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DtoIssuesWithTotal {
    private List<DtoIssue> issueList;
    private DtoIssueTotal leadDaysTotal;
    private DtoIssueTotal cycleDaysTotal;
    private DtoIssueTotal waitingTestDaysTotal;
    private DtoIssueTotal testingDaysTotal;
}
