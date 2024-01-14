package ml.pevgen.reportapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DbIssueMetricCalculatorTest {


    @Test
    void should_calculate_lead_days() {
        var created = LocalDateTime.now();
        var resolved = created.plusHours(6);
        DbIssue dbIssue = DbIssue.builder()
                .created(created)
                .resolved(resolved)
                .build();
        assertThat(DbIssueMetricCalculator.calculate(dbIssue).getLeadDays())
                .isEqualTo(0.25);
    }

    @Test
    void should_calculate_cycle_days() {
        var init = LocalDateTime.now();
        var resolved = init.plusHours(30);
        DbIssue dbIssue = DbIssue.builder()
                .created(LocalDateTime.now())
                .startProcessUpdate(init)
                .resolved(resolved)
                .build();
        assertThat(DbIssueMetricCalculator.calculate(dbIssue).getCycleDays())
                .isEqualTo(1.25);
    }

    @Test
    void should_return_null_cycle_days_if_start_time_is_null() {
        var resolved = LocalDateTime.now().plusHours(30);
        DbIssue dbIssue = DbIssue.builder()
                .created(LocalDateTime.now())
                .startProcessInit(null)
                .resolved(resolved)
                .build();
        assertThat(DbIssueMetricCalculator.calculate(dbIssue).getCycleDays())
                .isNull();
    }

    @Test
    void should_return_waiting_test_days() {
        var created = LocalDateTime.now();
        var toTestInit = created.plusHours(24);
        var testingInit = created.plusHours(54);
        var resolved = created.plusHours(100);
        DbIssue dbIssue = DbIssue.builder()
                .created(created)
                .toTestInit(toTestInit)
                .testingInit(testingInit)
                .resolved(resolved)
                .build();
        assertThat(DbIssueMetricCalculator.calculate(dbIssue).getWaitingTestDays())
                .isEqualTo(1.25);
    }

    @Test
    void should_return_null_waiting_test_days_if_testing_init_is_null() {
        var resolved = LocalDateTime.now().plusHours(30);
        DbIssue dbIssue = DbIssue.builder()
                .created(LocalDateTime.now())
                .testingInit(null)
                .toTestInit(LocalDateTime.now())
                .resolved(resolved)
                .build();
        assertThat(DbIssueMetricCalculator.calculate(dbIssue).getWaitingTestDays())
                .isNull();
    }

    @Test
    void should_return_null_waiting_test_days_if_to_test_init_is_null() {
        var resolved = LocalDateTime.now().plusHours(30);
        DbIssue dbIssue = DbIssue.builder()
                .created(LocalDateTime.now())
                .testingInit(LocalDateTime.now())
                .toTestInit(null)
                .resolved(resolved)
                .build();
        assertThat(DbIssueMetricCalculator.calculate(dbIssue).getWaitingTestDays())
                .isNull();

    }


    @Test
    void should_return_testing_days() {
        var created = LocalDateTime.now();
        var toTestInit = created.plusHours(24);
        var testingInit = created.plusHours(54);
        var resolved = created.plusHours(60);
        DbIssue dbIssue = DbIssue.builder()
                .created(created)
                .toTestInit(toTestInit)
                .testingInit(testingInit)
                .resolved(resolved)
                .build();
        assertThat(DbIssueMetricCalculator.calculate(dbIssue).getTestingDays())
                .isEqualTo(0.25);
    }

    @Test
    void should_return_null_testing_days_if_testing_init_is_null() {
        var resolved = LocalDateTime.now().plusHours(30);
        DbIssue dbIssue = DbIssue.builder()
                .created(LocalDateTime.now())
                .testingInit(null)
                .resolved(resolved)
                .build();
        assertThat(DbIssueMetricCalculator.calculate(dbIssue).getTestingDays())
                .isNull();
    }

}