package com.trbtree.infotree.modules.civic.dto;

public class DoctorDepartmentDto {

    private Long id;
    private String nameEn;
    private String nameBn;

    public DoctorDepartmentDto() {
    }

    public DoctorDepartmentDto(Long id, String nameEn, String nameBn) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameBn = nameBn;
    }

    public Long getId() {
        return id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameBn() {
        return nameBn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setNameBn(String nameBn) {
        this.nameBn = nameBn;
    }
}
