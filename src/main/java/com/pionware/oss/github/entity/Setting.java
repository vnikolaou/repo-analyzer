package com.pionware.oss.github.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "setting", schema = "public")
public class Setting {

    @Id
    @Column(name = "key", nullable = false, unique = true)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    // Getters and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
