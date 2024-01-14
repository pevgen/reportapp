package ml.pevgen.reportapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(value = "issues")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DbIssue implements Persistable<String> {

    @Id
    @Column("issue_id")
    private String issueId;
    @Column("issue_key")
    private String issueKey;
    @Column("issue_type")
    private String issueType;
    @Column("summary")
    private String summary;
    @Column("created")
    @NonNull
    private LocalDateTime created;
    @Column("start_process_init")
    private LocalDateTime startProcessInit;
    @Column("start_process_update")
    private LocalDateTime startProcessUpdate;
    @Column("to_test_init")
    private LocalDateTime toTestInit;
    @Column("testing_init")
    private LocalDateTime testingInit;
    @Column("resolved")
    @NonNull
    private LocalDateTime resolved;
    @Column("updated")
    private LocalDateTime updated;
    @Column("story_points")
    private Integer storyPoints;

    @Transient
    private Double leadDays;
    @Transient
    private Double cycleDays;
    @Transient
    private Double waitingTestDays;
    @Transient
    private Double testingDays;

    @Transient
    private boolean newEntity;



    @SuppressWarnings("java:S107")
    public DbIssue(String issueId, String issueKey, String issueType, String summary, LocalDateTime created, LocalDateTime startProcessInit, LocalDateTime startProcessUpdate, LocalDateTime toTestInit, LocalDateTime testingInit, LocalDateTime resolved, LocalDateTime updated, Integer storyPoints) {
        this.issueId = issueId;
        this.issueKey = issueKey;
        this.issueType = issueType;
        this.summary = summary;
        this.created = created;
        this.startProcessInit = startProcessInit;
        this.startProcessUpdate = startProcessUpdate;
        this.toTestInit = toTestInit;
        this.testingInit = testingInit;
        this.resolved = resolved;
        this.updated = updated;
        this.storyPoints = storyPoints;
    }

    @Override
    public String getId() {
        return issueId;
    }

    @Override
    public boolean isNew() {
        return newEntity;
    }

    public void setNew() {
        this.newEntity = true;
    }
}
