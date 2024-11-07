package com.dbfinal.prac.dto;

import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.index.Indexed;

public class RegisterUserDto{
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email cannot be empty")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Min(value = 1, message = "Age cannot be less than 1")
    @Max(value = 100, message = "Age cannot be greater than 100")
    private int age;
    private String gender;

    public int getAge() {
        return age;
    }

    public RegisterUserDto setAge(int age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public RegisterUserDto setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public RegisterUserDto setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + name + '\'' +
                '}';
    }
}
