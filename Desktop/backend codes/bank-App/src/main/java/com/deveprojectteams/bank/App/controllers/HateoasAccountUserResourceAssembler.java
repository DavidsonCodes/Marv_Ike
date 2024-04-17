package com.deveprojectteams.bank.App.controllers;

import com.deveprojectteams.bank.App.data.AccountUser;
import com.deveprojectteams.bank.App.data.models.AccountUserResource;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasAccountUserResourceAssembler {

    public AccountUserResource toModel(AccountUser accountUser) {
        AccountUserResource resource = new AccountUserResource(accountUser);

        resource.add(linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class).getAccountUserById(accountUser.getId())).withSelfRel());
        resource.add(linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class).getAllAccountUsers()).withRel("all-account-users"));
        resource.add(linkTo(methodOn(AccountUserController.class).updateAccountUser(accountUser.getId(), null)).withRel("update-account-user"));
        resource.add(linkTo(methodOn(AccountUserController.class).deleteAccountUser(accountUser.getId())).withRel("delete-account-user"));

        return resource;
    }
}
