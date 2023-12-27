package ml.pevgen.reportapp.repo;

import ml.pevgen.reportapp.model.DbIssue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IssueRepositoryTest {

    @Autowired
    IssueRepository issueRepository;

    @Test
    void should_select_all_issues() {
        assertThat(issueRepository.findAll()).isNotNull();
    }

    @Test
    void should_save_issues() {
        for (int i = 0; i < 10; i++) {
            DbIssue isuue =
                    new DbIssue(
                            String.valueOf(i),
                            "MMM-" + i,
                            "Task",
                            "description на русском языке",
                            LocalDateTime.now(), null, null, null, null, null, null, 1);
            isuue.setNew();
            issueRepository.save(isuue);
        }
        assertThat(issueRepository.findAll()).hasSize(10);
    }

}