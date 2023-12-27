package ml.pevgen.reportapp.repo;

import ml.pevgen.reportapp.model.DbIssue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IssueRepository extends CrudRepository<DbIssue, String> {

    @Override
    Set<DbIssue> findAll();
}
