package ml.pevgen.reportapp.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ml.pevgen.reportapp.parser.CSVIssueDateTimeConverter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CSVIssue {

    @CsvBindByPosition(position = 2, required = true)
    private String issueId;
    @CsvBindByPosition(position = 1, required = true)
    private String issueKey;
    @CsvBindByPosition(position = 0, required = true)
    private String issueType;
    @CsvBindByPosition(position = 3, required = true)
    private String summary;
    @CsvBindByPosition(position = 4, required = true)
    @CsvDate(value = "dd/MM/yyyy HH:mm")
    private LocalDateTime created;

    @CsvCustomBindByPosition(position = 5, converter = CSVIssueDateTimeConverter.class)
    private LocalDateTime startProcessInit;
    @CsvCustomBindByPosition(position = 6, converter = CSVIssueDateTimeConverter.class)
    private LocalDateTime startProcessUpdate;
    @CsvCustomBindByPosition(position = 7, converter = CSVIssueDateTimeConverter.class)
    private LocalDateTime toTestInit;
    @CsvCustomBindByPosition(position = 8, converter = CSVIssueDateTimeConverter.class)
    private LocalDateTime testingInit;
    @CsvCustomBindByPosition(position = 9, converter = CSVIssueDateTimeConverter.class)
    private LocalDateTime resolved;
    @CsvCustomBindByPosition(position = 10, converter = CSVIssueDateTimeConverter.class)
    private LocalDateTime updated;

    @CsvBindByPosition(position = 11)
    @CsvNumber("#.#")
    private Integer storyPoints;
}
