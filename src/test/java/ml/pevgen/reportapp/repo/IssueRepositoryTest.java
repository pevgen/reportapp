package ml.pevgen.reportapp.repo;

import ml.pevgen.reportapp.model.DbIssue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = ml.pevgen.reportapp.model.DbIssueCalculatorAfterConvertCallback.class)
class IssueRepositoryTest {

    @Autowired
    IssueRepository issueRepository;

    @Test
    void should_select_all_issues() {
        assertThat(issueRepository.findAll()).isNotNull();
    }

    @Test
    @DirtiesContext
    void should_save_issues() {
        for (int i = 0; i < 10; i++) {
            LocalDateTime created = LocalDateTime.now();
            DbIssue issue = DbIssue.builder()
                    .issueId(String.valueOf(i))
                    .issueKey("MMM-" + i)
                    .issueType("Task")
                    .summary("description на русском языке")
                    .created(created)
                    .startProcessUpdate(created.plusHours(6))
                    .toTestInit(created.plusHours(12))
                    .testingInit(created.plusHours(18))
                    .created(created)
                    .resolved(created.plusHours(30))
                    .build();
            issue.setNew();
            issueRepository.save(issue);
        }

        List<DbIssue> issues = issueRepository.findAll();
        assertThat(issues).hasSize(10);

        DbIssue firstRow = issues.get(0);
        assertThat(firstRow.getLeadDays()).isEqualTo(1.25);
        assertThat(firstRow.getCycleDays()).isEqualTo(1);
        assertThat(firstRow.getWaitingTestDays()).isEqualTo(0.25);
        assertThat(firstRow.getTestingDays()).isEqualTo(0.5);
    }

    @Test
    @DirtiesContext
    void should_delete_all_issues() {
        for (int i = 0; i < 10; i++) {
            LocalDateTime created = LocalDateTime.now();
            DbIssue issue = DbIssue.builder()
                    .issueId(String.valueOf(i))
                    .issueKey("MMM-" + i)
                    .issueType("Task")
                    .summary("description на русском языке")
                    .created(created)
                    .startProcessUpdate(created.plusHours(6))
                    .toTestInit(created.plusHours(12))
                    .testingInit(created.plusHours(18))
                    .created(created)
                    .resolved(created.plusHours(30))
                    .build();
            issue.setNew();
            issueRepository.save(issue);
        }

        List<DbIssue> issues = issueRepository.findAll();
        assertThat(issues).hasSize(10);

        issueRepository.deleteAll();

        assertThat(issueRepository.findAll()).isEmpty();
    }

    @Test
    @DirtiesContext
    void should_delete_issue_by_id() {

        String tempIssueId = "5";

        for (int i = 0; i < 10; i++) {
            LocalDateTime created = LocalDateTime.now();
            DbIssue issue = DbIssue.builder()
                    .issueId(String.valueOf(i))
                    .issueKey("MMM-" + i)
                    .issueType("Task")
                    .summary("description на русском языке")
                    .created(created)
                    .startProcessUpdate(created.plusHours(6))
                    .toTestInit(created.plusHours(12))
                    .testingInit(created.plusHours(18))
                    .created(created)
                    .resolved(created.plusHours(30))
                    .build();
            issue.setNew();
            issueRepository.save(issue);
        }

        DbIssue issue = issueRepository.findById(tempIssueId).orElse(new DbIssue());
        assertThat(issue.getIssueId()).isEqualTo(tempIssueId);
        assertThat(issue.getIssueKey()).isEqualTo("MMM-" + tempIssueId);

        issueRepository.deleteById("5");

        assertThat(issueRepository.findAll()).hasSize(9);
        DbIssue emptyIssue = issueRepository.findById(tempIssueId).orElse(new DbIssue());
        assertThat(emptyIssue.getIssueId()).isNullOrEmpty();
    }

}