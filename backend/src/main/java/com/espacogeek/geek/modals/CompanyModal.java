package com.espacogeek.geek.modals;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "companies")
public class CompanyModal {
    @Id
    @Column(name = "id_company")
    private Integer id;

    @Column(name = "name_company")
    private String name;

    @ManyToMany(mappedBy = "companyModals")
    private Set<MidiaModal> midiaModals;
}
