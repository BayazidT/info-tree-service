package com.trbtree.infotree.modules.civic.sevice;

import com.trbtree.infotree.exception.DataNotFoundException;
import com.trbtree.infotree.modules.civic.dto.CivicServiceCreateDto;
import com.trbtree.infotree.modules.civic.dto.CivicServiceListResponse;
import com.trbtree.infotree.modules.civic.dto.CivicServiceResponseDto;
import com.trbtree.infotree.modules.civic.entity.*;
import com.trbtree.infotree.modules.civic.repository.CategoryRepository;
import com.trbtree.infotree.modules.civic.repository.CityRepository;
import com.trbtree.infotree.modules.civic.repository.CivicServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CivicService {

    private final CivicServiceRepository civicServiceRepository;
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;

    public CivicServiceResponseDto createCivicService(CivicServiceCreateDto dto) {

        City city = cityRepository.findById(Math.toIntExact(dto.cityId()))
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        Category category = categoryRepository.findById(Math.toIntExact(dto.categoryId()))
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Optional: domain validation
        if (category.getDomain() != DomainEnum.CIVIC) {
            throw new IllegalArgumentException("Category must be CIVIC");
        }

        CivicServiceEntity service = new CivicServiceEntity();

        // Common fields (inherited or from base)
        service.setCategory(category);
        service.setTitle(dto.title());
        service.setDescription(dto.description());
        service.setAddress(dto.address());
        service.setCity(city);
        service.setActive(dto.isActive() != null ? dto.isActive() : true);

        // Civic-specific fields
        service.setContactPhone(dto.contactPhone());
        service.setContactEmail(dto.contactEmail());
        service.set24h7(dto.is24h7());
        service.setLastVerified(dto.lastVerified()); // assuming ISO string

        // Flexible extra data (police, doctor, pharmacy, etc.)
        service.setExtraAttributes(dto.extraAttributes());

        CivicServiceEntity saved = civicServiceRepository.save(service);

        return CivicServiceResponseDto.fromEntity(saved);
    }

    public CivicServiceListResponse getCivicServiceList(int page, int size, String search) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<CivicServiceEntity> pageResult =
                civicServiceRepository.findAllWithJoins(pageable);

        List<CivicServiceResponseDto> data = pageResult
                .getContent()
                .stream()
                .map(CivicServiceResponseDto::fromEntity)
                .toList();

        return new CivicServiceListResponse(
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.isFirst(),
                pageResult.isLast(),
                data
        );
    }

    public CivicServiceResponseDto findById(Long id) {
        CivicServiceEntity entity = civicServiceRepository
                .findByIdWithJoins(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Civic service not found with id: " + id)
                );

        return CivicServiceResponseDto.fromEntity(entity);
    }
}
