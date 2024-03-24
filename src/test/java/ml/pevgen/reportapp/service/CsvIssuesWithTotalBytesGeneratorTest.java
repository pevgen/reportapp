package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.model.DtoIssueTotal;
import ml.pevgen.reportapp.model.DtoIssuesWithTotal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CsvIssuesWithTotalBytesGeneratorTest {

    @Autowired
    CsvIssuesWithTotalBytesGenerator generator;

    @Test
    void should_generate_bytes_for_csv_ile() throws IOException {

        LocalDateTime createdDateTime = LocalDateTime.now();
        DtoIssuesWithTotal total = DtoIssuesWithTotal
                .builder()
                .issueList(List.of(
                        DtoIssue.builder()
                                .issueId("id-1")
                                .issueType("Task")
                                .issueKey("MMM-1")
                                .summary("description на русском языке")
                                .created(createdDateTime)
                                .startProcessInit(createdDateTime.plusHours(1))
                                .startProcessUpdate(createdDateTime.plusHours(2))
                                .toTestInit(createdDateTime.plusHours(3))
                                .testingInit(createdDateTime.plusHours(4))
                                .resolved(createdDateTime.plusHours(5))
                                .updated(createdDateTime.plusHours(6))
                                .storyPoints(1)
                                .cycleDays(2d)
                                .leadDays(3d)
                                .waitingTestDays(4d)
                                .testingDays(5d)
                                .build(),
                        DtoIssue.builder()
                                .issueId("id-2")
                                .issueType("Task")
                                .issueKey("ЫЫЫ-1")
                                .summary("description на русском языке 2")
                                .build()))
                .cycleDaysTotal(
                        DtoIssueTotal.builder()
                                .min(1d)
                                .max(2d)
                                .avg(1.5)
                                .median(1.7)
                                .build())
                .leadDaysTotal(DtoIssueTotal.builder()
                        .min(1d)
                        .max(2d)
                        .avg(1.5)
                        .median(1.7)
                        .build())
                .waitingTestDaysTotal(DtoIssueTotal.builder()
                        .min(1d)
                        .max(2d)
                        .avg(1.5)
                        .median(1.7)
                        .build())
                .testingDaysTotal(DtoIssueTotal.builder()
                        .min(1d)
                        .max(2d)
                        .avg(1.5)
                        .median(1.7)
                        .build())
                .build();
        assertNotNull(generator.generate(total));
    }
}