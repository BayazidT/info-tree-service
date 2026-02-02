package com.trbtree.infotree.modules.civic.service.impl;

import com.trbtree.infotree.modules.civic.dto.DoctorDepartmentDto;
import com.trbtree.infotree.modules.civic.mapper.DoctorDepartmentMapper;
import com.trbtree.infotree.modules.civic.repository.DoctorDepartmentRepository;
import com.trbtree.infotree.modules.civic.service.DoctorDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorDepartmentServiceImpl implements DoctorDepartmentService {

    private final DoctorDepartmentRepository doctorDepartmentRepository;

    @Override
    public List<DoctorDepartmentDto> getDoctorDepartments() {
        return doctorDepartmentRepository.findAll()
                .stream()
                .map(DoctorDepartmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
