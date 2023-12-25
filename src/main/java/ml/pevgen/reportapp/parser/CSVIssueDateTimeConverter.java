package ml.pevgen.reportapp.parser;

import com.opencsv.bean.AbstractBeanField;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class CSVIssueDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
    private final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    protected Object convert(String dateTimeAsString) {
        if (dateTimeAsString == null || dateTimeAsString.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeAsString, formatter1);
        } catch (DateTimeParseException dtExc) {
            try {
                return LocalDateTime.parse(dateTimeAsString, formatter2);
            } catch (DateTimeParseException dtExc2) {
                log.warn("Can't parse datetime field. Value: [{}]", dateTimeAsString);
                return null;
            }
        }
    }
}
