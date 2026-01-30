package com.trbtree.infotree.modules.civic.dto;
import com.trbtree.infotree.modules.civic.entity.DomainEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryResponseDto(
        Integer id,
        @NotBlank @Size(min = 1, max = 100) String name,
        String description,
        Integer parentId,
        DomainEnum domain
) {}

