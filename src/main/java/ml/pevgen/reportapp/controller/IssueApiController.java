package ml.pevgen.reportapp.controller;

import lombok.extern.slf4j.Slf4j;
import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.model.DtoIssuesWithTotal;
import ml.pevgen.reportapp.service.IssueService;
import ml.pevgen.reportapp.service.IssuesWithTotalBytesGenerator;
import ml.pevgen.reportapp.service.LoaderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/api/v1/issues")
@Slf4j
public class IssueApiController {

    static final String CSV_FILE_NAME = "issues_with_total.csv";

    private final IssueService issueService;
    private final LoaderService loaderService;
    private final IssuesWithTotalBytesGenerator issuesWithTotalBytesGenerator;

    public IssueApiController(IssueService issueService, LoaderService loaderService, IssuesWithTotalBytesGenerator issuesWithTotalBytesGenerator) {
        this.issueService = issueService;
        this.loaderService = loaderService;
        this.issuesWithTotalBytesGenerator = issuesWithTotalBytesGenerator;
    }

    @GetMapping("/files/csv")
    public ResponseEntity<byte[]> generateCsvFile() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", CSV_FILE_NAME);

        byte[] csvBytes = issuesWithTotalBytesGenerator.generate(
                issueService.getIssuesWithTotal());

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/files")
    public String parseCSV(@RequestParam MultipartFile file) throws IOException {
        try (InputStreamReader reader =
                     new InputStreamReader(file.getInputStream(), UTF_8)) {
            return "Saved " + loaderService.loadData(reader) + " issues";
        }
    }

    @GetMapping("/noTotal")
    public List<DtoIssue> getIssues() {
        return issueService.getAllIssues();
    }

    @GetMapping
    public DtoIssuesWithTotal getIssuesWithTotal() {
        return issueService.getIssuesWithTotal();
    }

    @DeleteMapping
    public void deleteAllIssues() {
        issueService.deleteAllIssues();
        log.info("Deleted all issues");
    }

    @DeleteMapping("/{issueId}")
    public String deleteIssueById(@PathVariable String issueId) {
        issueService.deleteById(issueId);
        log.info("Deleted issue with id {}", issueId);
        return String.format("Deleted issue with id = [%s]", issueId);
    }

}
