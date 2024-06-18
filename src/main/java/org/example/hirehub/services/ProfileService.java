package org.example.hirehub.services;

import org.example.hirehub.models.AccountModel;
import org.example.hirehub.models.ProfileModel;
import org.example.hirehub.repos.AccountRepo;
import org.example.hirehub.repos.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private AccountRepo accountRepo;

    public ProfileModel getProfileByUsername(String username) {
        return profileRepo.findByUserUsername(username);
    }

    public void saveProfile(ProfileModel profile) {
        profileRepo.save(profile);
    }

    public AccountModel getAccountByUsername(String username) {
        return accountRepo.findByUsername(username);
    }
}
