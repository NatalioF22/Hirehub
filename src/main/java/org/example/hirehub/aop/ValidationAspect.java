package org.example.hirehub.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.hirehub.models.JobPostModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    @Around("execution(* org.example.hirehub.services.JobService.getJobById(..)) && args(jobId)") // Apply to getJobById method
    public Object validateAndUpdateJobId(ProceedingJoinPoint jp, int jobId) throws Throwable {
        if (jobId < 0) {
            LOGGER.info("Job ID is negative, updating it");
            jobId = -jobId;
            LOGGER.info("New Job ID value: {}", jobId);
        }
        return jp.proceed(new Object[]{jobId});
    }

    @Around("execution(* org.example.hirehub.services.JobService.deleteJobByID(..)) && args(jobId)") // Apply to deleteJobByID method
    public Object validateJobIdBeforeDelete(ProceedingJoinPoint jp, int jobId) throws Throwable {
        if (jobId < 0) {
            LOGGER.error("Invalid Job ID: {}. Cannot delete job with negative ID.", jobId);
            throw new IllegalArgumentException("Job ID cannot be negative");
        }
        return jp.proceed();
    }

    @Around("execution(* org.example.hirehub.services.JobService.updateJob(..)) && args(updatedJob)") // Apply to updateJob method
    public Object validateJobBeforeUpdate(ProceedingJoinPoint jp, JobPostModel updatedJob) throws Throwable {
        if (updatedJob.getId() < 0) {
            LOGGER.error("Invalid Job ID: {}. Cannot update job with negative ID.", updatedJob.getId());
            throw new IllegalArgumentException("Job ID cannot be negative");
        }
        if (updatedJob.getName() == null || updatedJob.getName().isEmpty()) {
            LOGGER.error("Invalid Job Name: {}. Job name cannot be null or empty.", updatedJob.getName());
            throw new IllegalArgumentException("Job name cannot be null or empty");
        }
        return jp.proceed();
    }
}