package com.deveprojectteams.bank.App.data.models;

import com.deveprojectteams.bank.App.data.AccountUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountUserResource extends RepresentationModel<AccountUserResource> {
   private AccountUser accountUser;

   public AccountUserResource(AccountUser accountUser) {
      this.accountUser = accountUser;
   }

}
