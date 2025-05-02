package com.rangers.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"userId", "email"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "user_name", nullable = true)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "goal")
    private String goal;

    @Column(name = "age")
    private Integer age;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "physical_activity_level")
    private Integer physicalActivityLevel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workout> workouts;

    // Backward compatibility methods
    public String getFirstName() {
        return userName;
    }

    public void setFirstName(String firstName) {
        this.userName = firstName;
    }

    public String getLastName() {
        return "";
    }

    public void setLastName(String lastName) {
        System.out.println("Warning: setLastName called on User entity");
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
//                ", userName='" + userName + '\'' +
//                ", email='" + email + '\'' +
                "}";
    }
}
