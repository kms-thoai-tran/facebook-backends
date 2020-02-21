package com.example.facebookbackend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class DbEntityBase {
    @Column(name = "CreatedBy")
    UUID createdBy;
    @Column(name = "CreatedAt", nullable = false, columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    @Column(name = "ModifiedBy")
    UUID modifiedBy;
    @Column(name = "ModifiedAt", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedAt;

    public UUID getCreatedBy() {
        return createdBy;
    }

    public DbEntityBase(UUID createdBy, Date createdAt, UUID modifiedBy, Date modifiedAt) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UUID modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public DbEntityBase() {
    }

    @PrePersist
    public void init() {
        createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = new Date();
    }
}
