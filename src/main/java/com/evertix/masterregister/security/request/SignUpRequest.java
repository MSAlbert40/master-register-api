package com.evertix.masterregister.security.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
public class SignUpRequest {
    @Column(unique = true)
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 30, message = "Username name must be less than 30 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120, message = "Password name must be less than 120 characters")
    private String password;

    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email")
    @Size(max = 100)
    private String email;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50)
    private String name;

    @NotNull(message = "LastName cannot be null")
    @NotBlank(message = "LastName cannot be blank")
    @Size(max = 50)
    private String lastName;

    @Column(unique = true)
    @NotNull(message = "DNI cannot be null")
    @NotBlank(message = "DNI cannot be blank")
    @Size(max = 10, min = 8)
    private String dni;

    @Min(value = 0)
    private Integer age;

    @NotNull(message = "Gender cannot be null")
    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @Column(unique = true)
    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be blank")
    @Size(max = 200)
    private String address;

    @Column(unique = true)
    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 12, min = 9)
    private String phone;

    @Min(value = 0)
    @Digits(fraction = 2, integer = 5)
    private Integer salary;

    public SignUpRequest(String username, String password, String email, String name, String lastName,
                         String dni, Integer age, String gender, String address, String phone, Integer salary) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.salary = salary;
    }
}
