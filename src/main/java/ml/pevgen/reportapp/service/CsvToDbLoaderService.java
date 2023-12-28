package ml.pevgen.reportapp.service;

import ml.pevgen.reportapp.model.CSVIssue;
import ml.pevgen.reportapp.model.DbIssue;
import ml.pevgen.reportapp.parser.CSVIssueParser;
import ml.pevgen.reportapp.repo.IssueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;

@Service
public class CsvToDbLoaderService implements LoaderService {

    private final IssueRepository issueRepository;
    private final CSVIssueParser parser;
    private final ModelMapper modelMapper;

    public CsvToDbLoaderService(IssueRepository issueRepository, CSVIssueParser parser, ModelMapper modelMapper) {
        this.issueRepository = issueRepository;
        this.parser = parser;
        this.modelMapper = modelMapper;
    }

    @Override
    public int loadData(Reader reader) {
        List<CSVIssue> issues = parser.parseIssues(reader);
        List<DbIssue> dbIssues =
                issues.stream()
                        .map(issue -> {
                            DbIssue dbIssue = modelMapper.map(issue, DbIssue.class);
                            dbIssue.setNew();
                            return dbIssue;
                        })
                        .toList();
        issueRepository.saveAll(dbIssues);
        return dbIssues.size();
    }
}
