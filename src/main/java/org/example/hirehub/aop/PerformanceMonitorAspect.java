package org.example.hirehub.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    @Around("execution(* org.example.hirehub.services.JobService.addJob(..))") // Apply to addJob method
    public Object monitorAddJobPerformance(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info("Time taken by addJob: {} ms", (end - start));
        return result;
    }

    @Around("execution(* org.example.hirehub.services.JobService.getJobById(..))") // Apply to getJobById method
    public Object monitorGetJobByIdPerformance(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info("Time taken by getJobById: {} ms", (end - start));
        return result;
    }

    @Around("execution(* org.example.hirehub.services.JobService.updateJob(..))") // Apply to updateJob method
    public Object monitorUpdateJobPerformance(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info("Time taken by updateJob: {} ms", (end - start));
        return result;
    }

    @Around("execution(* org.example.hirehub.services.JobService.deleteJobByID(..))") // Apply to deleteJobByID method
    public Object monitorDeleteJobByIDPerformance(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info("Time taken by deleteJobByID: {} ms", (end - start));
        return result;
    }

    @Around("execution(* org.example.hirehub.services.JobService.searchJobs(..))") // Apply to searchJobs method
    public Object monitorSearchJobsPerformance(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info("Time taken by searchJobs: {} ms", (end - start));
        return result;
    }
}