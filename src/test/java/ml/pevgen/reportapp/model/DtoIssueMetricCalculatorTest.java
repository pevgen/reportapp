package ml.pevgen.reportapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DtoIssueMetricCalculatorTest {


    @Test
    void should_calculate_lead_days() {
        var created = LocalDateTime.now();
        var resolved = created.plusHours(6);
        DtoIssue dtoIssue = DtoIssue.builder()
                .created(created)
                .resolved(resolved)
                .build();
        assertThat(DtoIssueMetricCalculator.calculate(dtoIssue).getLeadDays())
                .isEqualTo(0.25);
    }

    @Test
    void should_calculate_cycle_days() {
        var init = LocalDateTime.now();
        var resolved = init.plusHours(30);
        DtoIssue dtoIssue = DtoIssue.builder()
                .created(LocalDateTime.now())
                .startProcessInit(init)
                .resolved(resolved)
                .build();
        assertThat(DtoIssueMetricCalculator.calculate(dtoIssue).getCycleDays())
                .isEqualTo(1.25);
    }

    @Test
    void should_return_null_cycle_days_if_start_time_is_null() {
        var resolved = LocalDateTime.now().plusHours(30);
        DtoIssue dtoIssue = DtoIssue.builder()
                .created(LocalDateTime.now())
                .startProcessInit(null)
                .resolved(resolved)
                .build();
        assertThat(DtoIssueMetricCalculator.calculate(dtoIssue).getCycleDays())
                .isNull();
    }

    @Test
    void should_return_waiting_test_days() {
        var created = LocalDateTime.now();
        var toTestInit = created.plusHours(24);
        var testingInit = created.plusHours(54);
        var resolved = created.plusHours(100);
        DtoIssue dtoIssue = DtoIssue.builder()
                .created(created)
                .toTestInit(toTestInit)
                .testingInit(testingInit)
                .resolved(resolved)
                .build();
        assertThat(DtoIssueMetricCalculator.calculate(dtoIssue).getWaitingTestDays())
                .isEqualTo(1.25);
    }

    @Test
    void should_return_null_waiting_test_days_if_testing_init_is_null() {
        var resolved = LocalDateTime.now().plusHours(30);
        DtoIssue dtoIssue = DtoIssue.builder()
                .created(LocalDateTime.now())
                .testingInit(null)
                .toTestInit(LocalDateTime.now())
                .resolved(resolved)
                .build();
        assertThat(DtoIssueMetricCalculator.calculate(dtoIssue).getWaitingTestDays())
                .isNull();
    }

    @Test
    void should_return_null_waiting_test_days_if_to_test_init_is_null() {
        var resolved = LocalDateTime.now().plusHours(30);
        DtoIssue dtoIssue = DtoIssue.builder()
                .created(LocalDateTime.now())
                .testingInit(LocalDateTime.now())
                .toTestInit(null)
                .resolved(resolved)
                .build();
        assertThat(DtoIssueMetricCalculator.calculate(dtoIssue).getWaitingTestDays())
                .isNull();

    }


    @Test
    void should_return_testing_days() {
        var created = LocalDateTime.now();
        var toTestInit = created.plusHours(24);
        var testingInit = created.plusHours(54);
        var resolved = created.plusHours(60);
        DtoIssue dtoIssue = DtoIssue.builder()
                .created(created)
                .toTestInit(toTestInit)
                .testingInit(testingInit)
                .resolved(resolved)
                .build();
        assertThat(DtoIssueMetricCalculator.calculate(dtoIssue).getTestingDays())
                .isEqualTo(0.25);
    }

    @Test
    void should_return_null_testing_days_if_testing_init_is_null() {
        var resolved = LocalDateTime.now().plusHours(30);
        DtoIssue dtoIssue = DtoIssue.builder()
                .created(LocalDateTime.now())
                .testingInit(null)
                .resolved(resolved)
                .build();
        assertThat(DtoIssueMetricCalculator.calculate(dtoIssue).getTestingDays())
                .isNull();
    }

}