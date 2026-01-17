package com.trbtree.infotree.modules.civic.controller;

import com.trbtree.infotree.modules.civic.dto.DoctorCreateDto;
import com.trbtree.infotree.modules.civic.dto.DoctorResponseDto;
import com.trbtree.infotree.modules.civic.sevice.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info-tree-service/api/v1/private/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<DoctorResponseDto> createDoctor(
            @Valid @RequestBody DoctorCreateDto dto
            ) {

        DoctorResponseDto created = doctorService.createDoctor(dto);

        return ResponseEntity
                .created(URI.create("/api/doctors/" + created.id()))
                .body(created);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctor(@PathVariable Long id) {
        DoctorResponseDto dto = doctorService.getDoctorById(id);
        return ResponseEntity.ok(dto);
    }
}
