package org.example.hirehub.services;

import org.example.hirehub.models.AccountModel;
import org.example.hirehub.repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public AccountModel saveAccount(AccountModel account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepo.save(account);
    }

    public AccountModel findByUsername(String username) {
        return accountRepo.findByUsername(username);
    }
}
