package ml.pevgen.reportapp.controller;

import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.service.IssueService;
import ml.pevgen.reportapp.service.LoaderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueApiController {

    private final IssueService issueService;
    private final LoaderService loaderService;

    public IssueApiController(IssueService issueService, LoaderService loaderService) {
        this.issueService = issueService;
        this.loaderService = loaderService;
    }

    @PostMapping("/files")
    public String parseCSV(@RequestParam MultipartFile file) throws IOException {
        try (InputStreamReader reader =
                     new InputStreamReader(file.getInputStream(), UTF_8)) {
            return "Saved " + loaderService.loadData(reader) + " issues";
        }
    }

    @GetMapping
    public List<DtoIssue> getIssues() {
        return issueService.getAllIssues();
    }
}
