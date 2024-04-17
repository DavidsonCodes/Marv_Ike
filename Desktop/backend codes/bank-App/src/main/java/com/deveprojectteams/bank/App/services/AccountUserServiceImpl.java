package com.deveprojectteams.bank.App.services;

import com.deveprojectteams.bank.App.data.AccountUser;
import com.deveprojectteams.bank.App.data.repositories.AccountUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountUserServiceImpl implements AccountUserService {

    private final AccountUserRepository accountUserRepository;

    public AccountUserServiceImpl(AccountUserRepository accountUserRepository) {
        this.accountUserRepository = accountUserRepository;
    }

    @Override
    public List<AccountUser> getAllAccountUsers() {
        return accountUserRepository.findAll();
    }

    @Override
    public AccountUser getAccountUserById(Long id) {
        Optional<AccountUser> optionalAccountUser = accountUserRepository.findById(id);
        return optionalAccountUser.orElse(null);
    }

    @Override
    public AccountUser createAccountUser(AccountUser accountUser) {
        return accountUserRepository.save(accountUser);
    }

    @Override
    public AccountUser updateAccountUser(Long id, AccountUser accountUser) {
        AccountUser existingAccountUser = getAccountUserById(id);
        if (existingAccountUser != null) {
            existingAccountUser.setFirstName(accountUser.getFirstName());
            existingAccountUser.setLastName(accountUser.getLastName());
            existingAccountUser.setMiddleName(accountUser.getMiddleName());
            existingAccountUser.setUsername(accountUser.getUsername());
            existingAccountUser.setPassword(accountUser.getPassword());
            existingAccountUser.setPhoneNumber(accountUser.getPhoneNumber());
            existingAccountUser.setEmail(accountUser.getEmail());

            return accountUserRepository.save(existingAccountUser);
        }
        return null;
    }

    @Override
    public void deleteAccountUser(Long id) {
        accountUserRepository.deleteById(id);
    }
}

