package ml.pevgen.reportapp.csvconverter;

import com.opencsv.bean.CsvToBeanBuilder;
import ml.pevgen.reportapp.model.CSVIssue;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.List;

@Component
public class CSVIssueParser {

    public List<CSVIssue> parseIssues(Reader reader) {

        return new CsvToBeanBuilder<CSVIssue>(reader)
                .withType(CSVIssue.class)
                .withSkipLines(1)
                .withSeparator(';')
                .build()
                .parse();
    }

}
