package com.deveprojectteams.bank.App.services;

import com.deveprojectteams.bank.App.data.AccountUser;

import java.util.List;

public interface AccountUserService {
    List<AccountUser> getAllAccountUsers();
    AccountUser getAccountUserById(Long id);
    AccountUser createAccountUser(AccountUser accountUser);
    AccountUser updateAccountUser(Long id, AccountUser accountUser);
    void deleteAccountUser(Long id);
}
