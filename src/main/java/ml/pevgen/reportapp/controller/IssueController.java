package ml.pevgen.reportapp.controller;

import ml.pevgen.reportapp.service.IssueService;
import ml.pevgen.reportapp.service.LoaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    private final LoaderService loaderService;

    public IssueController(IssueService issueService, LoaderService loaderService) {
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
    public String getIssues(Model model) {
        model.addAttribute("issues", issueService.getAllIssues());
        return "issues/issues";

    }
}
