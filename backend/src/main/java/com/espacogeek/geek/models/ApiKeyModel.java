package com.espacogeek.geek.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "api_keys")
@Getter
public class ApiKeyModel implements Serializable {
    @Id
    @Column(name = "id_api_key")
    private Integer id;

    @Column(name = "name_api")
    private String name;

    @Column(name = "api_key")
    @Setter
    private String key;
}
