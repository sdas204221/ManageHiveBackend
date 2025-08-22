package com.sdas204221.ManageHive.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope("prototype")
public class User {
    @Id
    private String username;
    private String password;
    private String role;
    private String businessName;
    private String address;
    private String phone;
    private String email;
    boolean isAccountLocked;
}
