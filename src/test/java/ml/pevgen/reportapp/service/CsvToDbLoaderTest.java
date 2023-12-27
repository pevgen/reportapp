package ml.pevgen.reportapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CsvToDbLoaderTest {

    @Value("classpath:files/issues_source.csv")
    private Resource resource;

    @Autowired
    private LoaderService csvToDbLoader;

    @Test
    @DirtiesContext
    void should_load_data_form_csv_to_db() throws IOException {
        try (InputStreamReader reader =
                     new InputStreamReader(resource.getInputStream(), UTF_8)) {
            assertThat(csvToDbLoader.loadData(reader)).isEqualTo(14);
        }
    }
}