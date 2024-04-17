package com.deveprojectteams.bank.App.data;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


import lombok.*;

@Entity
@Table(name = "account_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = true)
    private String middleName;

    @NotBlank(message = "Username is mandatory")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    @Min(8) // Enforce minimum password length for security
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    @Column(nullable = false)
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[\\s.-]?[0-9]{3}[\\s.-]?[0-9]{4}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;
}
