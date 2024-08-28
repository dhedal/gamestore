package com.ecf.gamestore.models;

import com.ecf.gamestore.models.enumerations.CommandStatus;
import com.ecf.gamestore.models.interfaces.IEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
public class Command implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    @Column(nullable = false)
    private LocalDateTime pickupDate;
    @Column(nullable = false)
    private CommandStatus commandStatus;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private GSUser user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "agence_id", nullable = false)
    private Agence agence;
    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandLine> commandLines;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
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

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public CommandStatus getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(CommandStatus commandStatus) {
        this.commandStatus = commandStatus;
    }

    public GSUser getUser() {
        return user;
    }

    public void setUser(GSUser user) {
        this.user = user;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public List<CommandLine> getCommandLines() {
        return Collections.unmodifiableList(this.commandLines);
    }

    public void setCommandLines(List<CommandLine> commandLines) {
        this.commandLines = commandLines;
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
