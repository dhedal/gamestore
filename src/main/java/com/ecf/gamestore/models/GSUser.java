package com.ecf.gamestore.models;

import com.ecf.gamestore.models.enumerations.Role;
import com.ecf.gamestore.models.interfaces.IEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "user")
public class GSUser implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 255)
    private String password;
    private Role role;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Override
    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    @Override
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
