package com.example.wethemanyapp.Model;

import java.util.Set;

public class User {
    private String email;

    private String password;

    private Set<String> roles;

    private String fullName;

    private String currentAddress;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", fullName='" + fullName + '\'' +
                ", currentAddres='" + currentAddress + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCurrentAddres() {
        return currentAddress;
    }

    public void setCurrentAddres(String currentAddres) {
        this.currentAddress = currentAddres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
