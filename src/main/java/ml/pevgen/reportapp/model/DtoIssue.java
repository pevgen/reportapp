package ml.pevgen.reportapp.model;

import lombok.*;

import java.time.LocalDateTime;


//@Data
//@Builder
//@NoArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DtoIssue {

    private String issueId;
    private String issueKey;
    private String issueType;
    private String summary;
    private LocalDateTime created;
    private LocalDateTime startProcessInit;
    private LocalDateTime startProcessUpdate;
    private LocalDateTime toTestInit;
    private LocalDateTime testingInit;
    private LocalDateTime resolved;
    private LocalDateTime updated;
    private Integer storyPoints;

    private Double leadDays;
    private Double cycleDays;
    private Double waitingTestDays;
    private Double testingDays;
}
