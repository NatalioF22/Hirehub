package org.example.hirehub.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.hirehub.models.JobPostModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class LoggingAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* org.example.hirehub.services.JobService.*(..))") // Apply to all methods in JobService
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        LOGGER.info("Method '{}' is called with arguments: {}", methodName, jp.getArgs());
    }

    @After("execution(* org.example.hirehub.services.JobService.*(..))") // Apply to all methods in JobService
    public void logMethodExecuted(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        LOGGER.info("Method '{}' execution completed", methodName);
    }

    @AfterThrowing(pointcut = "execution(* org.example.hirehub.services.JobService.*(..))", throwing = "ex") // Apply to all methods in JobService
    public void logMethodCrashed(JoinPoint jp, Throwable ex) {
        String methodName = jp.getSignature().getName();
        LOGGER.error("Method '{}' encountered an exception: {}", methodName, ex.getMessage());
    }

    @AfterReturning(pointcut = "execution(* org.example.hirehub.services.JobService.*(..))", returning = "result") // Apply to all methods in JobService
    public void logMethodExecutedSuccess(JoinPoint jp, Object result) {
        String methodName = jp.getSignature().getName();
        LOGGER.info("Method '{}' executed successfully and returned: {}", methodName, result);
    }

    @Before("execution(* org.example.hirehub.services.JobService.addJob(..))") // Apply to addJob method
    public void logAddJobCall(JoinPoint jp) {
        LOGGER.info("Adding a new job with details: {}", jp.getArgs());
    }

    @AfterReturning("execution(* org.example.hirehub.services.JobService.addJob(..))") // Apply to addJob method
    public void logAddJobSuccess(JoinPoint jp) {
        LOGGER.info("Job added successfully");
    }

    @Before("execution(* org.example.hirehub.services.JobService.getJobById(..))") // Apply to getJobById method
    public void logGetJobByIdCall(JoinPoint jp) {
        LOGGER.info("Retrieving job with ID: {}", jp.getArgs());
    }

    @AfterReturning(pointcut = "execution(* org.example.hirehub.services.JobService.getJobById(..))", returning = "job") // Apply to getJobById method
    public void logGetJobByIdSuccess(JoinPoint jp, Object job) {
        LOGGER.info("Job retrieved successfully: {}", job);
    }

    @Before("execution(* org.example.hirehub.services.JobService.updateJob(..))") // Apply to updateJob method
    public void logUpdateJobCall(JoinPoint jp) {
        LOGGER.info("Updating job with details: {}", jp.getArgs());
    }

    @AfterReturning("execution(* org.example.hirehub.services.JobService.updateJob(..))") // Apply to updateJob method
    public void logUpdateJobSuccess(JoinPoint jp) {
        LOGGER.info("Job updated successfully");
    }

    @Before("execution(* org.example.hirehub.services.JobService.deleteJobByID(..))") // Apply to deleteJobByID method
    public void logDeleteJobByIDCall(JoinPoint jp) {
        LOGGER.info("Deleting job with ID: {}", jp.getArgs());
    }

    @AfterReturning("execution(* org.example.hirehub.services.JobService.deleteJobByID(..))") // Apply to deleteJobByID method
    public void logDeleteJobByIDSuccess(JoinPoint jp) {
        LOGGER.info("Job deleted successfully");
    }

    @Before("execution(* org.example.hirehub.services.JobService.searchJobs(..))") // Apply to searchJobs method
    public void logSearchJobsCall(JoinPoint jp) {
        LOGGER.info("Searching jobs with criteria: {}", jp.getArgs());
    }

    @AfterReturning(pointcut = "execution(* org.example.hirehub.services.JobService.searchJobs(..))", returning = "jobs") // Apply to searchJobs method
    public void logSearchJobsSuccess(JoinPoint jp, List<JobPostModel> jobs) {
        LOGGER.info("Search completed successfully, found {} jobs", jobs.size());
    }
}