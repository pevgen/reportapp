package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DbIssue;
import ml.pevgen.reportapp.repo.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Set;

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
                Set.of(
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
        var issueList = issueService.getAllIssues();
        assertThat(issueList)
                .isNotNull()
                .hasSize(2);

    }

}