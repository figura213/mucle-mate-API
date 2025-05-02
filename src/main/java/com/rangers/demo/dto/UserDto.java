package com.rangers.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;
    private GeneralInformation generalInformation;
    private Metrics metrics;
   private List<WorkoutDto> workouts;

    // Preserving password field for backward compatibility
    private String password;

    // Backward compatibility methods
    public String getFirstName() {
        return generalInformation != null ? generalInformation.getUserName() : null;
    }

    public void setFirstName(String firstName) {
        if (generalInformation == null) {
            generalInformation = new GeneralInformation();
        }
        generalInformation.setUserName(firstName);
    }

    public String getLastName() {
        return ""; // Not applicable for current model
    }

    public void setLastName(String lastName) {
        System.out.println("Warning: setLastName called on UserDto");
    }

    public String getEmail() {
        return generalInformation != null ? generalInformation.getEmail() : null;
    }

    public void setEmail(String email) {
        if (generalInformation == null) {
            generalInformation = new GeneralInformation();
        }
        generalInformation.setEmail(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralInformation {
        private String userName;
        private String email;
        private String gender;
        private String goal;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metrics {
        private Integer age;
        private Double height;
        private Double weight;
        private Integer physicalActivityLevel;
    }
}
