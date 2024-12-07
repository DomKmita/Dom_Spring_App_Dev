package com.example.lab10.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "myusers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {
    @Id
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;

}
