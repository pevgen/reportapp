package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DbIssue;
import ml.pevgen.reportapp.repo.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class IssueServiceImplTest {

    @MockBean
    private IssueRepository issueRepository;

    @Autowired
    private IssueService issueService;

    @Test
    void should_return_all_issues_from_repository() {
        when(issueRepository.findAll()).thenReturn(
                List.of(
                        new DbIssue(
                                "1",
                                "key-1",
                                "Task",
                                "summary-1",
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                1),
                        new DbIssue(
                                "2",
                                "key-2",
                                "Bug",
                                "summary-2",
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                3)));
        var issuesWithTotal = issueService.getIssuesWithTotal();
        assertThat(issuesWithTotal)
                .isNotNull();
        assertThat(issuesWithTotal.getIssueList())
                .isNotNull()
                .hasSize(2);
        assertThat(issuesWithTotal.getCycleDaysTotal())
                .isNotNull();
        assertThat(issuesWithTotal.getLeadDaysTotal())
                .isNotNull();
        assertThat(issuesWithTotal.getWaitingTestDaysTotal())
                .isNotNull();
        assertThat(issuesWithTotal.getTestingDaysTotal())
                .isNotNull();

    }

}