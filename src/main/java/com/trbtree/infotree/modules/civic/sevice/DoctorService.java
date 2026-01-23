package com.trbtree.infotree.modules.civic.sevice;

import com.trbtree.infotree.modules.civic.dto.DoctorCreateDto;
import com.trbtree.infotree.modules.civic.dto.DoctorListResponseDto;
import com.trbtree.infotree.modules.civic.dto.DoctorResponseDto;
import com.trbtree.infotree.modules.civic.entity.*;
import com.trbtree.infotree.modules.civic.repository.CategoryRepository;
import com.trbtree.infotree.modules.civic.repository.CityRepository;
import com.trbtree.infotree.modules.civic.repository.DoctorRepository;
import com.trbtree.infotree.modules.user.entity.User;
import com.trbtree.infotree.modules.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional()
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;           // if you track who created it
    public DoctorResponseDto createDoctor(DoctorCreateDto dto) {

        // 1. Fetch required references
        City city = cityRepository.findById(Math.toIntExact(dto.cityId()))
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + dto.cityId()));

        Category category = categoryRepository.findById(Math.toIntExact(dto.categoryId()))
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + dto.categoryId()));

        // Optional business validation
        if (category.getDomain() != DomainEnum.CIVIC) {
            throw new IllegalArgumentException("Category must belong to CIVIC domain");
        }

        // 2. Create doctor entity (extends BaseEntity)
        DoctorEntity doctor = new DoctorEntity();

        // Set inherited/common fields (directly on doctor)
        doctor.setCategory(category);
//        doctor.setTitle(String.format("%s %s %s",
//                dto.title() != null ? dto.title() + " " : "",
//                dto.firstName(), dto.lastName()).trim());
        doctor.setTitle(dto.title());
        doctor.setDescription("");
        doctor.setAddress(dto.address());
        doctor.setCity(city);
        doctor.setActive(true);

        // Set doctor-specific fields
        doctor.setFirstName(dto.firstName());
        doctor.setLastName(dto.lastName());
        doctor.setTitle(dto.title());
//        doctor.setSpecialties(new HashSet<>(dto.specialties()));
        doctor.setPrivatePatientsOnly(false);
        doctor.setAcceptsNewPatients(dto.acceptsNewPatients());
        doctor.setTelemedicineAvailable(false);
        doctor.setAppointmentUrl(dto.appointmentUrl());
        doctor.setEmergencyAppointments(false);
        doctor.setConsultationHours(dto.consultationHours() != null ? new HashMap<>(dto.consultationHours()) : new HashMap<>());
        doctor.setExtraAttributes(dto.extraAttributes() != null ? new HashMap<>(dto.extraAttributes()) : new HashMap<>());

        // 3. Save → Hibernate inserts into base_entities first, then doctors
        DoctorEntity saved = doctorRepository.save(doctor);

        // 4. Return DTO
        return DoctorResponseDto.fromEntity(saved);
    }


    public DoctorResponseDto getDoctorById(Long id) {
        DoctorEntity entity = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        return DoctorResponseDto.fromEntity(entity);  // <-- correct static call
    }

    public List<DoctorResponseDto> searchHouseDoctorsInBerlin(
            Set<String> preferredLanguages,
            int maxResults) {

        List<DoctorEntity> doctors = doctorRepository.findAvailableHouseDoctors(
                "Berlin",
                preferredLanguages,
                maxResults
        );

        return doctors.stream()
                .map(DoctorResponseDto::fromEntity)
                .toList();
    }

    public List<DoctorResponseDto> findDoctorsBySpecialtyAndLanguage(
            String specialty,
            boolean acceptsStatutoryInsurance,
            Set<String> languages) {

        // First filter by specialty + insurance
        List<DoctorEntity> bySpecialty = doctorRepository.findBySpecialtyAndInsurance(
                specialty, acceptsStatutoryInsurance
        );

        // Then filter in-memory by languages (or use more complex query if needed)
        return bySpecialty.stream()
                .map(DoctorResponseDto::fromEntity)
                .toList();
    }

    public DoctorListResponseDto getDoctorList(int page, int size, String search) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<DoctorEntity> entities = doctorRepository.findAll(pageable);

        List<DoctorResponseDto> content = entities
                .getContent()
                .stream()
                .map(DoctorResponseDto::fromEntity)
                .toList();

        return new DoctorListResponseDto(
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.getNumber(),
                entities.getSize(),
                entities.isFirst(),
                entities.isLast(),
                content
        );

    }

    // When location is back:
    /*
    public List<DoctorResponseDto> findNearestDoctors(
            double latitude, double longitude, double radiusKm,
            String specialty, int limit) {

        List<Object[]> results = doctorRepository.findNearestBySpecialty(
            longitude, latitude, radiusKm * 1000, specialty, limit
        );

        return results.stream()
                .map(row -> {
                    DoctorEntity d = (DoctorEntity) row[0];
                    Double distance = (Double) row[1];
                    DoctorResponseDto dto = DoctorResponseDto.fromEntity(d);
                    // You can add distance to DTO if you extend it
                    return dto;
                })
                .toList();
    }
    */
}