package ml.pevgen.reportapp.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.model.DtoIssueTotal;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
@Slf4j
@Getter
public class IssuesTotalCalculator implements TotalCalculator {

    private DtoIssueTotal leadDaysTotal = new DtoIssueTotal();
    private DtoIssueTotal cycleDaysTotal = new DtoIssueTotal();
    private DtoIssueTotal waitingTestDaysTotal = new DtoIssueTotal();
    private DtoIssueTotal testingDaysTotal = new DtoIssueTotal();

    @Override
    public void calculate(List<DtoIssue> issues) {
        if (issues == null || issues.isEmpty()) {
            return;
        }
        leadDaysTotal = calcIndicator(issues, DtoIssue::getLeadDays);
        cycleDaysTotal = calcIndicator(issues, DtoIssue::getCycleDays);
        waitingTestDaysTotal = calcIndicator(issues, DtoIssue::getWaitingTestDays);
        testingDaysTotal = calcIndicator(issues, DtoIssue::getTestingDays);
    }

    private DtoIssueTotal calcIndicator(List<DtoIssue> issues, Function<DtoIssue, Double> function) {

        double[] daysAsSortedArray = getDaysAsSortedArray(issues, function);
        DtoIssueTotal total = new DtoIssueTotal();
        if (daysAsSortedArray.length > 0) {
            total.setMin(daysAsSortedArray[0]);
            total.setMax(daysAsSortedArray[daysAsSortedArray.length - 1]);
            Arrays.stream(daysAsSortedArray).average()
                    .ifPresent(total::setAvg);
            total.setMedian(calcMedian(daysAsSortedArray));
        }
        return total;
    }

    private static double[] getDaysAsSortedArray(List<DtoIssue> issues, Function<DtoIssue, Double> function) {
        return issues.stream()
                .map(function)
                .filter(Objects::nonNull)
                .sorted()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }

    private double calcMedian(double[] arr) {
        if (arr.length % 2 == 0) {
            return (arr[arr.length / 2 - 1] + arr[arr.length / 2]) / 2;
        } else {
            return arr[arr.length / 2];
        }
    }


}
