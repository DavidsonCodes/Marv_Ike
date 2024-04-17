package com.deveprojectteams.bank.App.controllers;

import com.deveprojectteams.bank.App.data.AccountUser;
import com.deveprojectteams.bank.App.services.AccountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.deveprojectteams.bank.App.data.models.AccountUserResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account-users")
public class AccountUserController {

    @Autowired
    private AccountUserService accountUserService;

    @Autowired
    private HateoasAccountUserResourceAssembler assembler;

    @GetMapping
    public List<AccountUserResource> getAllAccountUsers() {
        List<AccountUser> accountUsers = accountUserService.getAllAccountUsers();
        return accountUsers.stream().map(assembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountUserResource> getAccountUserById(@PathVariable Long id) {
        AccountUser accountUser = accountUserService.getAccountUserById(id);
        if (accountUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(accountUser));
    }

    @PostMapping
    public ResponseEntity<AccountUserResource> createAccountUser(@RequestBody AccountUser accountUser) {
        AccountUser createdAccountUser = accountUserService.createAccountUser(accountUser);
        AccountUserResource resource = assembler.toModel(createdAccountUser);
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountUserResource> updateAccountUser(@PathVariable Long id, @RequestBody AccountUser accountUser) {
        AccountUser updatedAccountUser = accountUserService.updateAccountUser(id, accountUser);
        if (updatedAccountUser == null) {
            return ResponseEntity.notFound().build();
        }
        AccountUserResource resource = assembler.toModel(updatedAccountUser);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountUser(@PathVariable Long id) {
        AccountUser accountUser = accountUserService.getAccountUserById(id);
        if(accountUser == null) {
            return ResponseEntity.notFound().build();
        }
        accountUserService.deleteAccountUser(id);
        return ResponseEntity.ok().build();
    }
}
