package org.example.hirehub.services;

import org.example.hirehub.models.AccountModel;
import org.example.hirehub.models.JobLevel;
import org.example.hirehub.models.JobLocation;
import org.example.hirehub.models.JobPostModel;
import org.example.hirehub.repos.AccountRepo;
import org.example.hirehub.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private AccountRepo accountRepo;

    public void addJob(JobPostModel jobPost, String username) {
        AccountModel user = accountRepo.findByUsername(username);
        jobPost.setUser(user);
        jobRepo.save(jobPost);
    }


    public Optional<JobPostModel> getJobById(int id){
        return jobRepo.findById(id);
    }

    public void updateJob(JobPostModel updatedJob) {
        jobRepo.save(updatedJob);
    }

    public void deleteJobByID(int id){
        jobRepo.deleteById(id);
    }



    public List<JobPostModel> findAll() {
        return jobRepo.findAll();
    }

    public List<JobPostModel> searchJobs(String jobName, String techStacks, BigDecimal minSalary, BigDecimal maxSalary,
                                         String jobProfile, JobLevel jobLevel, JobLocation jobLocation) {
        List<JobPostModel> jobs = jobRepo.findAll();

        if (jobName != null && !jobName.isEmpty() || techStacks != null && !techStacks.isEmpty()) {
            jobs = jobRepo.findByNameContainingIgnoreCaseOrTechStacksContainingIgnoreCase(jobName, techStacks);
        }
        if (minSalary != null && maxSalary != null) {
            jobs = jobRepo.findBySalaryBetween(minSalary, maxSalary);
        }
        if (jobProfile != null && !jobProfile.isEmpty()) {
            jobs = jobRepo.findByJobProfileContainingIgnoreCase(jobProfile);
        }
        if (jobLevel != null) {
            jobs = jobRepo.findByJobLevel(jobLevel);
        }
        if (jobLocation != null) {
            jobs = jobRepo.findByJobLocation(jobLocation);
        }

        return jobs;
    }
}