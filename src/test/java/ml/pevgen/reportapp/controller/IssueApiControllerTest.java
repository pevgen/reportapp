package ml.pevgen.reportapp.controller;

import ml.pevgen.reportapp.model.DtoIssue;
import ml.pevgen.reportapp.model.DtoIssuesWithTotal;
import ml.pevgen.reportapp.service.IssueService;
import ml.pevgen.reportapp.service.IssuesWithTotalBytesGenerator;
import ml.pevgen.reportapp.service.LoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IssueApiController.class)
class IssueApiControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;
    @MockBean
    private IssuesWithTotalBytesGenerator generator;
    @MockBean
    private LoaderService loaderService;

    @Test
    void should_return_model_and_template_for_all_issues_empty_list() throws Exception {

        when(issueService.getIssuesWithTotal()).thenReturn(
                DtoIssuesWithTotal
                        .builder()
                        .issueList(List.of())
                        .build());

        this.mockMvc
                .perform(get("/api/v1/issues"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issueList").isEmpty())
                .andExpect(jsonPath("$.cycleDaysTotal").isEmpty())
                .andExpect(jsonPath("$.waitingTestDaysTotal").isEmpty())
                .andExpect(jsonPath("$.testingDaysTotal").isEmpty())
                .andExpect(jsonPath("$.leadDaysTotal").isEmpty());
    }

    @Test
    void should_return_model_and_template_for_all_issues_two_records() throws Exception {
        var dtFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        var createdDateTime = LocalDateTime.now();
        var resolvedDateTime = createdDateTime.plusDays(1);

        when(issueService.getIssuesWithTotal()).thenReturn(
                DtoIssuesWithTotal
                        .builder()
                        .issueList(List.of(
                                DtoIssue.builder()
                                        .issueId("id-1")
                                        .created(createdDateTime)
                                        .resolved(resolvedDateTime)
                                        .build(),
                                DtoIssue.builder()
                                        .issueId("id-2")
                                        .build()))
                        .build());

        this.mockMvc
                .perform(get("/api/v1/issues"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issueList").isNotEmpty())
                .andExpect(jsonPath("$.issueList").isArray())
                .andExpect(content().string(containsString("id-1")))
                .andExpect(content().string(containsString("id-2")))
                .andExpect(content().string(containsString(createdDateTime.format(dtFormatter))))
                .andExpect(content().string(containsString(resolvedDateTime.format(dtFormatter))));
    }

    @Test
    void should_return_exception_using_global_exception_controller() throws Exception {
        when(issueService.getIssuesWithTotal()).thenThrow(
                new RuntimeException("specific runtime exception"));

        this.mockMvc
                .perform(get("/api/v1/issues"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.statusCode", is(500)))
                .andExpect(jsonPath("$.message", is("specific runtime exception")));
    }

    @Test
    void should_return_ok_if_all_delete_ok() throws Exception {
        doNothing().when(issueService).deleteAllIssues();

        this.mockMvc
                .perform(delete("/api/v1/issues"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void should_delete_issue_by_id() throws Exception {
        when(issueService.deleteById("1")).thenReturn("1");
        this.mockMvc
                .perform(delete("/api/v1/issues/{issueId}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Deleted issue with id = [1]")));
    }

    @Test
    void should_return_bytes_of_csv() throws Exception {
        when(generator.generate(any())).thenReturn(new byte[]{1, 2, 3});
        this.mockMvc
                .perform(get("/api/v1/issues/files/csv", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "form-data; name=\"attachment\"; filename=\"issues_with_total.csv\""))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_OCTET_STREAM.toString()))
                .andExpect(content().bytes(new byte[]{1, 2, 3}));
    }

}