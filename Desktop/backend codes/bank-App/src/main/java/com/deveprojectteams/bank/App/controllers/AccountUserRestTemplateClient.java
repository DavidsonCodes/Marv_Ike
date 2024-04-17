package com.deveprojectteams.bank.App.controllers;

import com.deveprojectteams.bank.App.data.AccountUser;
import com.deveprojectteams.bank.App.data.models.AccountUserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class AccountUserRestTemplateClient {

    private static final String BASE_URL = "http://localhost:8080/api/account-users";

    @Autowired
    private RestTemplate restTemplate;

    public List<AccountUserResource> getAllAccountUsers() {
        ResponseEntity<List<AccountUserResource>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccountUserResource>>() {}
        );
        return response.getBody();
    }

    public AccountUserResource getAccountUserById(Long id) {
        String url = BASE_URL + "/" + id;
        ResponseEntity<AccountUserResource> response = restTemplate.getForEntity(url, AccountUserResource.class);
        return response.getBody();
    }

    public AccountUserResource createAccountUser(AccountUser accountUser) {
        ResponseEntity<AccountUserResource> response = restTemplate.postForEntity(BASE_URL, accountUser, AccountUserResource.class);
        return response.getBody();
    }

    public AccountUserResource updateAccountUser(Long id, AccountUser accountUser) {
        String url = BASE_URL + "/" + id;
        HttpEntity<AccountUser> requestEntity = new HttpEntity<>(accountUser);
        ResponseEntity<AccountUserResource> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, AccountUserResource.class);
        return response.getBody();
    }

    public void deleteAccountUser(Long id) {
        String url = BASE_URL + "/" + id;
        restTemplate.delete(url);
    }
}
