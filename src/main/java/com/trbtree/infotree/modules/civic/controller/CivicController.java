package com.trbtree.infotree.modules.civic.controller;

import com.trbtree.infotree.modules.civic.dto.CivicServiceCreateDto;
import com.trbtree.infotree.modules.civic.dto.CivicServiceListResponse;
import com.trbtree.infotree.modules.civic.dto.CivicServiceResponseDto;
import com.trbtree.infotree.modules.civic.sevice.CivicService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/info-tree-service/api/v1/private/civic")
@RequiredArgsConstructor
public class CivicController {

    private final CivicService civicServiceService;

    /**
     * Creates a new civic service entry (e.g. police station, doctor, pharmacy, fire station).
     *
     * Example for police:
     * POST /api/civic-services
     * {
     *   "title": "Polizeiwache Abschnitt 53",
     *   "address": "Invalidenstraße 50, 10115 Berlin",
     *   "cityId": 1,
     *   "categoryId": 10,
     *   "contactPhone": "+49 30 4664 9530",
     *   "is24h7": true,
     *   "lastVerified": "2026-01-15T00:00:00Z",
     *   "extraAttributes": {
     *     "stationNumber": "Abschnitt 53",
     *     "emergencyPhone": "110",
     *     "hasLostPropertyOffice": true
     *   }
     * }
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<CivicServiceResponseDto> createCivicService(
            @Valid @RequestBody CivicServiceCreateDto dto,
            @AuthenticationPrincipal UserDetails currentUser) {

        // Pass current user (for audit trail / createdBy if you add it later)
        CivicServiceResponseDto created = civicServiceService.createCivicService(dto);

        // Return 201 Created with Location header
        URI location = URI.create("/api/civic-services/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping()
    public ResponseEntity<CivicServiceListResponse> getCivicServiceList(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(required = false) String search){

        return ResponseEntity.ok(civicServiceService.getCivicServiceList(page, size, search));
    }

    /**
     * Optional: GET a single civic service by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CivicServiceResponseDto> getCivicService(@PathVariable Long id) {
        CivicServiceResponseDto dto = civicServiceService.findById(id);

        return ResponseEntity.ok(dto);
    }

    /**
     * Optional: Simple search endpoint (expand later with filters)
     */
//    @GetMapping("/search")
//    public List<CivicServiceResponseDto> searchCivicServices(
//            @RequestParam(required = false) String categoryName,
//            @RequestParam(required = false) String cityName,
//            @RequestParam(defaultValue = "10") int limit) {
//
//        return civicServiceService.search(categoryName, cityName, limit);
//    }
}