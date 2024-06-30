package com.espacogeek.geek.modals;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
=======
import lombok.Data;

@Entity
@Data
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
@Table(name = "companies")
public class CompanyModal implements Serializable {
    @Id
    @Column(name = "id_company")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_company")
    private String name;

<<<<<<< HEAD
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/CompanyModel.java
    @ManyToMany(mappedBy = "company")
    private List<MediaModel> media;
=======
    @ManyToMany(mappedBy = "companyModals")
    private List<MidiaModal> midiaModals;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/CompanyModal.java
=======
    @ManyToMany(mappedBy = "companyModals")
    private List<MidiaModal> midiaModals;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
