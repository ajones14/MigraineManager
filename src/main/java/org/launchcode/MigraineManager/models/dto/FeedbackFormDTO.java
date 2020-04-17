package org.launchcode.MigraineManager.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class FeedbackFormDTO {

    @NotBlank(message = "Please enter a valid name")
    private String name;

    @NotBlank(message = "Please enter a valid email")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Please enter a message")
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
