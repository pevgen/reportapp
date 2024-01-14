package ml.pevgen.reportapp.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DbIssueCalculatorAfterConvertCallback implements AfterConvertCallback<DbIssue> {
    @Override
    public DbIssue onAfterConvert(DbIssue dbIssue) {
        return DbIssueMetricCalculator.calculate(dbIssue);
    }

}
