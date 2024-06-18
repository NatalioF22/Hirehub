package org.example.hirehub.repos;

import org.example.hirehub.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepo extends JpaRepository<AccountModel, Integer> {
    AccountModel findByUsername(String username);
}
