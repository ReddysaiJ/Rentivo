package com.rental.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String phno;
    private String role;
    private boolean enabled;

    private String photo;
    private String drivingLicenseNo;
}
