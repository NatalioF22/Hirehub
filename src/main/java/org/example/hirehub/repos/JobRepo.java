package org.example.hirehub.repos;

import org.example.hirehub.models.JobLevel;
import org.example.hirehub.models.JobLocation;
import org.example.hirehub.models.JobPostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<JobPostModel, Integer> {

    List<JobPostModel> findByNameContainingIgnoreCaseOrTechStacksContainingIgnoreCase(String name, String techStacks);

    List<JobPostModel> findBySalaryBetween(BigDecimal minSalary, BigDecimal maxSalary);

    List<JobPostModel> findByJobProfileContainingIgnoreCase(String jobProfile);

    List<JobPostModel> findByJobLevel(JobLevel jobLevel);

    List<JobPostModel> findByJobLocation(JobLocation jobLocation);

}