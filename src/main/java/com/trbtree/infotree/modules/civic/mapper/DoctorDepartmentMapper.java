package com.trbtree.infotree.modules.civic.mapper;

import com.trbtree.infotree.modules.civic.dto.DoctorDepartmentDto;
import com.trbtree.infotree.modules.civic.entity.DoctorDepartment;

public class DoctorDepartmentMapper {

    private DoctorDepartmentMapper() {
    }

    public static DoctorDepartmentDto toDto(DoctorDepartment entity) {
        return new DoctorDepartmentDto(
                entity.getId(),
                entity.getNameEn(),
                entity.getNameBn()
        );
    }
}
