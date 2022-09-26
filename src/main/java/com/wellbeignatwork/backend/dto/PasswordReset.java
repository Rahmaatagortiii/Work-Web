package com.wellbeignatwork.backend.dto;

import javax.validation.constraints.NotBlank;

public class PasswordReset {

    @NotBlank
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
