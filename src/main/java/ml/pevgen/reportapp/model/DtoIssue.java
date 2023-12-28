package ml.pevgen.reportapp.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
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

}
