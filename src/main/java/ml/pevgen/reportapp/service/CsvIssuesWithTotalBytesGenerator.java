package ml.pevgen.reportapp.service;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import ml.pevgen.reportapp.model.DtoIssueTotal;
import ml.pevgen.reportapp.model.DtoIssuesWithTotal;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CsvIssuesWithTotalBytesGenerator implements IssuesWithTotalBytesGenerator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public byte[] generate(DtoIssuesWithTotal issuesWithTotal) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (CSVWriter csvWriter = new CSVWriter(
                new OutputStreamWriter(byteArrayOutputStream),
                ICSVWriter.DEFAULT_SEPARATOR,
                ICSVWriter.NO_QUOTE_CHARACTER,
                ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                ICSVWriter.DEFAULT_LINE_END)) {

            csvWriter.writeNext(
                    new String[]{
                            "ID",
                            "Key",
                            "Type",
                            "Summary",
                            "Created",
                            "Start Process Init",
                            "Start Process Update",
                            "To Test Init",
                            "Testing Init",
                            "Resolved",
                            "Updated",
                            "StoryPoint",
                            "CycleTime (Days)",
                            "LeadTime (Days)",
                            "WaitingTest (Days)",
                            "Testing (Days)"});

            for (var dtoIssue : issuesWithTotal.getIssueList()) {

                csvWriter.writeNext(
                        new String[]{
                                dtoIssue.getIssueId(),
                                dtoIssue.getIssueKey(),
                                dtoIssue.getIssueType(),
                                dtoIssue.getSummary(),
                                getStringFromDateTime(dtoIssue.getCreated()),
                                getStringFromDateTime(dtoIssue.getStartProcessInit()),
                                getStringFromDateTime(dtoIssue.getStartProcessUpdate()),
                                getStringFromDateTime(dtoIssue.getToTestInit()),
                                getStringFromDateTime(dtoIssue.getTestingInit()),
                                getStringFromDateTime(dtoIssue.getResolved()),
                                getStringFromDateTime(dtoIssue.getUpdated()),
                                getStringFromNumber(dtoIssue.getStoryPoints()),
                                getStringFromNumber(dtoIssue.getCycleDays()),
                                getStringFromNumber(dtoIssue.getLeadDays()),
                                getStringFromNumber(dtoIssue.getWaitingTestDays()),
                                getStringFromNumber(dtoIssue.getTestingDays())
                        });


            }
            DtoIssueTotal cycleDaysTotal = issuesWithTotal.getCycleDaysTotal();
            DtoIssueTotal leadDaysTotal = issuesWithTotal.getLeadDaysTotal();
            DtoIssueTotal waitingTestDaysTotal = issuesWithTotal.getWaitingTestDaysTotal();
            DtoIssueTotal testingDaysTotal = issuesWithTotal.getTestingDaysTotal();
            csvWriter.writeNext(
                    new String[]{
                            "", "", "", "", "", "", "", "", "", "", "",
                            "Min",
                            getStringFromNumber(cycleDaysTotal.getMin()),
                            getStringFromNumber(leadDaysTotal.getMin()),
                            getStringFromNumber(waitingTestDaysTotal.getMin()),
                            getStringFromNumber(testingDaysTotal.getMin())});
            csvWriter.writeNext(
                    new String[]{
                            "", "", "", "", "", "", "", "", "", "", "",
                            "Max",
                            getStringFromNumber(cycleDaysTotal.getMax()),
                            getStringFromNumber(leadDaysTotal.getMax()),
                            getStringFromNumber(waitingTestDaysTotal.getMax()),
                            getStringFromNumber(testingDaysTotal.getMax())});
            csvWriter.writeNext(
                    new String[]{
                            "", "", "", "", "", "", "", "", "", "", "",
                            "Среднее",
                            getStringFromNumber(cycleDaysTotal.getAvg()),
                            getStringFromNumber(leadDaysTotal.getAvg()),
                            getStringFromNumber(waitingTestDaysTotal.getAvg()),
                            getStringFromNumber(testingDaysTotal.getAvg())});
            csvWriter.writeNext(
                    new String[]{
                            "", "", "", "", "", "", "", "", "", "", "",
                            "Медиана",
                            getStringFromNumber(cycleDaysTotal.getMedian()),
                            getStringFromNumber(leadDaysTotal.getMedian()),
                            getStringFromNumber(waitingTestDaysTotal.getMedian()),
                            getStringFromNumber(testingDaysTotal.getMedian())});
        }

        return byteArrayOutputStream.toByteArray();
    }

    private String getStringFromDateTime(LocalDateTime dt) {
        return dt != null ? DATE_TIME_FORMATTER.format(dt) : "";
    }

    private String getStringFromNumber(Number num) {
        return num != null ? String.valueOf(num) : "";
    }

}
