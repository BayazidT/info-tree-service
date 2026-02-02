package com.trbtree.infotree.modules.civic.controller;

import com.trbtree.infotree.modules.civic.dto.DoctorDepartmentDto;
import com.trbtree.infotree.modules.civic.service.DoctorDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info-tree-service/api/v1/private/doctor-departments")
@RequiredArgsConstructor
public class DoctorDepartmentController {

    private final DoctorDepartmentService doctorDepartmentService;

    @GetMapping
    public List<DoctorDepartmentDto> getDoctorDepartments() {
        return doctorDepartmentService.getDoctorDepartments();
    }
}
