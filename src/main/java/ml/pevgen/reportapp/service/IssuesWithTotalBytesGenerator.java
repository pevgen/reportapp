package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.DtoIssuesWithTotal;

import java.io.IOException;

public interface IssuesWithTotalBytesGenerator {
    
    byte[] generate(DtoIssuesWithTotal issuesWithTotal) throws IOException;
}
