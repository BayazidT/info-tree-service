package com.trbtree.infotree.modules.civic.controller;

import com.trbtree.infotree.modules.civic.dto.CityResponseDto;
import com.trbtree.infotree.modules.civic.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info-tree-service/api/v1/private/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
    @GetMapping
    public List<CityResponseDto> getCities() {
        return cityService.getCities();
    }

}
