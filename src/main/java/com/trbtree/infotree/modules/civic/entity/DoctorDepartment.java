package com.trbtree.infotree.modules.civic.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "doctor_department", schema ="infotree")
public class DoctorDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en", nullable = false, length = 255)
    private String nameEn;

    @Column(name = "name_bn", nullable = false, length = 255)
    private String nameBn;

    protected DoctorDepartment() {
        // JPA only
    }

    public DoctorDepartment(String nameEn, String nameBn) {
        this.nameEn = nameEn;
        this.nameBn = nameBn;
    }

    public Long getId() {
        return id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameBn() {
        return nameBn;
    }

    public void setNameBn(String nameBn) {
        this.nameBn = nameBn;
    }
}
