package ml.pevgen.reportapp.controller;

import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.service.IssueService;
import ml.pevgen.reportapp.service.LoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IssueController.class)
class IssueControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;
    @MockBean
    private LoaderService loaderService;

    @Test
    void should_return_model_and_template_for_all_issues_empty_list() throws Exception {

        when(issueService.getAllIssues()).thenReturn(List.of());

        this.mockMvc
                .perform(get("/issues"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("issues/issues"))
                .andExpect(model().attributeExists("issues"))
                .andExpect(content().string(containsString("No Issues")));
    }

    @Test
    void should_return_model_and_template_for_all_issues_two_records() throws Exception {
        var dtFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        var createdDateTime = LocalDateTime.now();
        var resolvedDateTime = createdDateTime.plusDays(1);
        when(issueService.getAllIssues())
                .thenReturn(
                        List.of(
                                DtoIssue.builder()
                                        .issueId("id-1")
                                        .created(createdDateTime)
                                        .resolved(resolvedDateTime)
                                        .build(),
                                DtoIssue.builder()
                                        .issueId("id-2")
                                        .build()));

        this.mockMvc
                .perform(get("/issues"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("issues/issues"))
                .andExpect(model().attributeExists("issues"))
                .andExpect(content().string(containsString("id-1")))
                .andExpect(content().string(containsString("id-2")))
                .andExpect(content().string(containsString(createdDateTime.format(dtFormatter))))
                .andExpect(content().string(containsString(resolvedDateTime.format(dtFormatter))));
    }
}