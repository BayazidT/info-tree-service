package com.trbtree.infotree.modules.civic.repository;

import com.trbtree.infotree.modules.civic.entity.CivicServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CivicServiceRepository extends JpaRepository<CivicServiceEntity, Long> {
    @Query("""
        SELECT cs
        FROM CivicServiceEntity cs
        JOIN FETCH cs.city
        JOIN FETCH cs.category
    """)
    Page<CivicServiceEntity> findAllWithJoins(Pageable pageable);
}
