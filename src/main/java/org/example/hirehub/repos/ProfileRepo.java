package org.example.hirehub.repos;

import org.example.hirehub.models.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<ProfileModel, Long> {
    ProfileModel findByUserUsername(String username);
}