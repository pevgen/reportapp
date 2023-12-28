package ml.pevgen.reportapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IssueServiceImplTest {

    @Autowired
    private IssueService issueService;

    @Test
    void getAllIssues() {
        assertThat(issueService.getAllIssues()).isNotNull();
    }

    // TODO add tests
}