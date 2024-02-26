package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.model.DtoIssueTotal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IssuesTotalCalculatorTest {

    private IssuesTotalCalculator totalCalculator;

    @BeforeEach
    void setUp() {
        totalCalculator = new IssuesTotalCalculator();
    }

    @Test
    void should_return_empties_for_null() {
        totalCalculator.calculate(null);
        assertNotNull(totalCalculator.getCycleDaysTotal());
        assertNotNull(totalCalculator.getTestingDaysTotal());
        assertNotNull(totalCalculator.getLeadDaysTotal());
        assertNotNull(totalCalculator.getWaitingTestDaysTotal());
    }

    @Test
    void should_return_empties_for_empty_list() {
        totalCalculator.calculate(Collections.emptyList());
        assertNotNull(totalCalculator.getCycleDaysTotal());
        assertNotNull(totalCalculator.getTestingDaysTotal());
        assertNotNull(totalCalculator.getLeadDaysTotal());
        assertNotNull(totalCalculator.getWaitingTestDaysTotal());
    }

    @Test
    void should_return_empties_for_list_with_all_null_values() {

        List<DtoIssue> issues =
                List.of(DtoIssue.builder()
                                .leadDays(1d)
                                .cycleDays(2d)
                                .waitingTestDays(3d)
                                .testingDays(4d)
                                .build(),
                        DtoIssue.builder()
                                .leadDays(5d)
                                .cycleDays(6d)
                                .waitingTestDays(7d)
                                .testingDays(8d)
                                .build());

        totalCalculator.calculate(issues);

        assertNotNull(totalCalculator.getCycleDaysTotal());
        assertNotNull(totalCalculator.getTestingDaysTotal());
        assertNotNull(totalCalculator.getLeadDaysTotal());
        assertNotNull(totalCalculator.getWaitingTestDaysTotal());
    }

    @Test
    void should_calculate_lead_cycle_days_even() {
        List<DtoIssue> issues =
                List.of(DtoIssue.builder()
                                .leadDays(1d)
                                .cycleDays(2d)
                                .waitingTestDays(3d)
                                .testingDays(4d)
                                .build(),
                        DtoIssue.builder()
                                .leadDays(5d)
                                .cycleDays(6d)
                                .waitingTestDays(7d)
                                .testingDays(8d)
                                .build());

        totalCalculator.calculate(issues);

        DtoIssueTotal leadDaysTotal = totalCalculator.getLeadDaysTotal();
        assertEquals(5d, leadDaysTotal.getMax());
        assertEquals(1d, leadDaysTotal.getMin());
        assertEquals(3d, leadDaysTotal.getAvg());
        assertEquals(3d, leadDaysTotal.getMedian());

        DtoIssueTotal cycleDaysTotal = totalCalculator.getCycleDaysTotal();
        assertEquals(6d, cycleDaysTotal.getMax());
        assertEquals(2d, cycleDaysTotal.getMin());
        assertEquals(4d, cycleDaysTotal.getAvg());
        assertEquals(4d, cycleDaysTotal.getMedian());
    }

    @Test
    void should_calculate_lead_cycle_days_odd() {
        List<DtoIssue> issues =
                List.of(DtoIssue.builder()
                                .leadDays(1d)
                                .cycleDays(2d)
                                .waitingTestDays(3d)
                                .testingDays(4d)
                                .build(),
                        DtoIssue.builder()
                                .leadDays(5d)
                                .cycleDays(6d)
                                .waitingTestDays(7d)
                                .testingDays(8d)
                                .build(),
                        DtoIssue.builder()
                                .leadDays(9d)
                                .cycleDays(10d)
                                .waitingTestDays(11d)
                                .testingDays(12d)
                                .build());

        totalCalculator.calculate(issues);

        DtoIssueTotal leadDaysTotal = totalCalculator.getLeadDaysTotal();
        assertEquals(9d, leadDaysTotal.getMax());
        assertEquals(1d, leadDaysTotal.getMin());
        assertEquals(5d, leadDaysTotal.getAvg());
        assertEquals(5d, leadDaysTotal.getMedian());

        DtoIssueTotal cycleDaysTotal = totalCalculator.getCycleDaysTotal();
        assertEquals(10d, cycleDaysTotal.getMax());
        assertEquals(2d, cycleDaysTotal.getMin());
        assertEquals(6d, cycleDaysTotal.getAvg());
        assertEquals(6d, cycleDaysTotal.getMedian());
    }


    @Test
    void should_calculate_real_testing_days_even() {
        List<DtoIssue> issues =
                List.of(DtoIssue.builder()
                                .leadDays(1d)
                                .cycleDays(1d)
                                .waitingTestDays(null)
                                .testingDays(2d)
                                .build(),
                        DtoIssue.builder()
                                .leadDays(1d)
                                .cycleDays(1d)
                                .waitingTestDays(3d)
                                .testingDays(null)
                                .build());

        totalCalculator.calculate(issues);

        DtoIssueTotal waitingDaysTotal = totalCalculator.getWaitingTestDaysTotal();
        assertEquals(3d, waitingDaysTotal.getMax());
        assertEquals(3d, waitingDaysTotal.getMin());
        assertEquals(3d, waitingDaysTotal.getAvg());
        assertEquals(3d, waitingDaysTotal.getMedian());

        DtoIssueTotal testingDaysTotal = totalCalculator.getTestingDaysTotal();
        assertEquals(2d, testingDaysTotal.getMax());
        assertEquals(2d, testingDaysTotal.getMin());
        assertEquals(2d, testingDaysTotal.getAvg());
        assertEquals(2d, testingDaysTotal.getMedian());
    }

    @Test
    void should_calculate_real_testing_days_odd() {
        List<DtoIssue> issues =
                List.of(DtoIssue.builder()
                                .leadDays(1d)
                                .cycleDays(1d)
                                .waitingTestDays(1d)
                                .testingDays(2d)
                                .build(),
                        DtoIssue.builder()
                                .leadDays(1d)
                                .cycleDays(1d)
                                .waitingTestDays(3d)
                                .testingDays(null)
                                .build(),
                        DtoIssue.builder()
                                .leadDays(1d)
                                .cycleDays(1d)
                                .waitingTestDays(0.5)
                                .testingDays(9d)
                                .build());

        totalCalculator.calculate(issues);

        DtoIssueTotal waitingDaysTotal = totalCalculator.getWaitingTestDaysTotal();
        assertEquals(3d, waitingDaysTotal.getMax());
        assertEquals(0.5d, waitingDaysTotal.getMin());
        assertEquals(1.5d, waitingDaysTotal.getAvg());
        assertEquals(1d, waitingDaysTotal.getMedian());

        DtoIssueTotal testingDaysTotal = totalCalculator.getTestingDaysTotal();
        assertEquals(9d, testingDaysTotal.getMax());
        assertEquals(2d, testingDaysTotal.getMin());
        assertEquals(5.5d, testingDaysTotal.getAvg());
        assertEquals(5.5d, testingDaysTotal.getMedian());
    }

}