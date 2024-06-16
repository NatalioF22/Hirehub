package org.example.hirehub.Controllers;

import org.example.hirehub.models.JobLevel;
import org.example.hirehub.models.JobLocation;
import org.example.hirehub.models.JobPostModel;
import org.example.hirehub.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class JobController {

    @Autowired
    private JobService service;

    @GetMapping({"/", "home"})
    public String home(Model model){
        model.addAttribute("alljobs", service.findAll());
        return "index";
    }

    @GetMapping("/browseJob")
    public String browseJob(
            @RequestParam(required = false) String jobName,
            @RequestParam(required = false) String techStacks,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary,
            @RequestParam(required = false) String jobProfile,
            @RequestParam(required = false) JobLevel jobLevel,
            @RequestParam(required = false) JobLocation jobLocation,
            Model model) {

        List<JobPostModel> jobs = service.searchJobs(jobName, techStacks, minSalary, maxSalary, jobProfile, jobLevel, jobLocation);
        model.addAttribute("jobs", jobs);
        return "browseJob";
    }

    @GetMapping("/addJob")
    public String addJob(Model model) {
        model.addAttribute("jobPostModel", new JobPostModel());
        return "addJob";
    }

    @PostMapping("/addJob")
    public String postJob(@ModelAttribute("jobPostModel") JobPostModel jobPostModel, Principal principal) {
        String username = principal.getName();
        service.addJob(jobPostModel, username);
        return "redirect:/";
    }

    @GetMapping("/jobDetail/{id}")
    public String viewJobDetail(@PathVariable("id") int id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Optional<JobPostModel> optionalJob = service.getJobById(id);
        if (optionalJob.isPresent()) {
            JobPostModel job = optionalJob.get();
            model.addAttribute("job", job);
            model.addAttribute("isOwner", job.getUser() != null && job.getUser().getUsername().equals(principal.getName()));
            return "jobDetail";
        } else {
            System.out.println("Job not found");
            return "redirect:/";
        }
    }

    @GetMapping("/editJob/{id}")
    public String editJob(@PathVariable("id") int id, Model model, Principal principal) {
        Optional<JobPostModel> job = service.getJobById(id);
        if (job.isPresent() && job.get().getUser().getUsername().equals(principal.getName())) {
            model.addAttribute("job", job.get());
            return "editJob";
        } else {
            model.addAttribute("error", "You do not have access to edit this job post.");
            return "error";
        }
    }

    @PostMapping("/updateJob")
    public String updateJob(@ModelAttribute("job") JobPostModel updatedJob, Principal principal, Model model) {
        Optional<JobPostModel> existingJob = service.getJobById(updatedJob.getId());
        if (existingJob.isPresent() && existingJob.get().getUser().getUsername().equals(principal.getName())) {
            service.updateJob(updatedJob);
            return "redirect:/jobDetail/" + updatedJob.getId();
        } else {
            model.addAttribute("error", "You do not have access to update this job post.");
            return "error";
        }
    }

    @PostMapping("/deleteJob/{id}")
    public String deleteJob(@PathVariable("id") int id, Principal principal, Model model) {
        Optional<JobPostModel> job = service.getJobById(id);
        if (job.isPresent() && job.get().getUser().getUsername().equals(principal.getName())) {
            service.deleteJobByID(id);
            return "redirect:/";
        } else {
            model.addAttribute("error", "You do not have access to delete this job post.");
            return "error";
        }
    }
}

