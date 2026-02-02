package com.trbtree.infotree.modules.civic.service;

import com.trbtree.infotree.modules.civic.dto.CityResponseDto;
import com.trbtree.infotree.modules.civic.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityResponseDto> getCities() {
        return cityRepository.findAll()
                .stream()
                .map(city -> new CityResponseDto(
                        city.getId(),
                        city.getName(),
                        city.getCountry(),
                        city.getAdminLevel(),
                        city.getPopulation()
                ))
                .toList();
    }

}
