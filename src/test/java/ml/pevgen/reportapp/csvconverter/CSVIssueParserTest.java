package ml.pevgen.reportapp.csvconverter;

import ml.pevgen.reportapp.model.CSVIssue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class CSVIssueParserTest {

    @Value("classpath:files/issues_source.csv")
    Resource resource;

    @Test
    void should_parse_example_file() throws IOException {
        assertThat(resource).isNotNull();
        CSVIssueParser parser = new CSVIssueParser();
        try (InputStreamReader reader =
                     new InputStreamReader(resource.getInputStream(), UTF_8)) {

            List<CSVIssue> issues = parser.parseIssues(reader);
            assertThat(issues).hasSize(14);
            assertThat(issues.get(0)).isNotNull();
            assertThat(issues.get(0).getIssueId()).isEqualTo("367839");
            assertThat(issues.get(0).getIssueKey()).isEqualTo("ЫЫЫ-698");
            assertThat(issues.get(0).getIssueType()).isEqualTo("Task");
            assertThat(issues.get(0).getSummary()).isEqualTo("[AT] Добавить окружения в регрессионный прогон mmm");
            assertThat(issues.get(0).getCreated()).isNotNull();
            assertThat(issues.get(0).getStartProcessInit()).isNotNull();
            assertThat(issues.get(0).getStoryPoints()).isEqualTo(13);
        }
    }
}