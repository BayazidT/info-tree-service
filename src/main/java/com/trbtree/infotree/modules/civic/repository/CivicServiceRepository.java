package com.trbtree.infotree.modules.civic.repository;

import com.trbtree.infotree.modules.civic.entity.CivicServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CivicServiceRepository extends JpaRepository<CivicServiceEntity, Long> {
}
