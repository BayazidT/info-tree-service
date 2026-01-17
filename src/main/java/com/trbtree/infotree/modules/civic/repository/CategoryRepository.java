package com.trbtree.infotree.modules.civic.repository;
import com.trbtree.infotree.modules.civic.entity.Category;
import com.trbtree.infotree.modules.civic.entity.DomainEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Find by name (case-insensitive, useful for seeding/validation)
    Optional<Category> findByNameIgnoreCase(String name);

    // Find by domain (e.g. all CIVIC categories)
    List<Category> findByDomain(DomainEnum domain);

    // Hierarchical: top-level categories
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.name")
    List<Category> findTopLevelCategories();

    // Subcategories under a parent (e.g. all doctor-related under 'Ärzte & Medizinische Dienste')
    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId ORDER BY c.name")
    List<Category> findSubCategories(@Param("parentId") Integer parentId);

    // Doctor/medical related categories (hardcoded names for simplicity)
    @Query("""
        SELECT c FROM Category c
        WHERE c.domain = 'CIVIC'
          AND c.name IN ('Allgemeinmediziner (Hausarzt)', 'Fachärzte', 'Zahnärzte', 'Psychotherapeuten',
                         'Ärzte & Medizinische Dienste')
        ORDER BY c.name
    """)
    List<Category> findDoctorRelatedCategories();

    // Search by name fragment (autocomplete in admin UI)
    List<Category> findByNameContainingIgnoreCaseOrderByNameAsc(String nameFragment);
}