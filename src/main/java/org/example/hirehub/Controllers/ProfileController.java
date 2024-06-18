package org.example.hirehub.Controllers;

import org.example.hirehub.models.ProfileModel;
import org.example.hirehub.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.util.StringUtils;

@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model, Principal principal) {
        String username = principal.getName();
        ProfileModel profile = profileService.getProfileByUsername(username);
        if (profile == null) {
            profile = new ProfileModel();
            profile.setUser(profileService.getAccountByUsername(username));
        }
        model.addAttribute("profile", profile);
        return "editProfile";
    }

    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable("username") String username, Model model) {
        ProfileModel profile = profileService.getProfileByUsername(username);
        model.addAttribute("profile", profile);
        return "profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("profile") ProfileModel updatedProfile, Principal principal) {
        String username = principal.getName();
        ProfileModel existingProfile = profileService.getProfileByUsername(username);
        if (existingProfile == null) {
            existingProfile = new ProfileModel();
            existingProfile.setUser(profileService.getAccountByUsername(username));
        }
        existingProfile.setFirstName(updatedProfile.getFirstName());
        existingProfile.setLastName(updatedProfile.getLastName());
        existingProfile.setDateOfBirth(updatedProfile.getDateOfBirth());
        existingProfile.setEmail(updatedProfile.getEmail());
        existingProfile.setSex(updatedProfile.getSex());
        existingProfile.setAddress(updatedProfile.getAddress());

        if (updatedProfile.getPictureFile() != null && !updatedProfile.getPictureFile().isEmpty()) {
            try {
                // Save the uploaded picture file
                String fileName = StringUtils.cleanPath(updatedProfile.getPictureFile().getOriginalFilename());
                Path uploadDir = Paths.get("uploads");
                Path filePath = uploadDir.resolve(fileName);
                Files.createDirectories(uploadDir);
                updatedProfile.getPictureFile().transferTo(filePath.toFile());
                existingProfile.setPictureUrl("/uploads/" + fileName);
            } catch (IOException e) {
                // Handle file upload error
                e.printStackTrace();
            }
        }

        profileService.saveProfile(existingProfile);
        return "redirect:/profile/" + username;
    }
}
