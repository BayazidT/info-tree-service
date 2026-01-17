package com.trbtree.infotree.modules.civic.repository;
import com.trbtree.infotree.modules.civic.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    // -------------------------------------------------------------------------
    // Basic lookups
    // -------------------------------------------------------------------------
//    Optional<DoctorEntity> findByApprobationNumber(String approbationNumber);

//    List<DoctorEntity> findByLastNameContainingIgnoreCase(String lastNamePart);
//
//    // -------------------------------------------------------------------------
//    // Specialty & Language filtering (using @ElementCollection join tables)
//    // -------------------------------------------------------------------------
    @Query("""
        SELECT d FROM DoctorEntity d
        WHERE d.acceptsNewPatients = true
          AND d.active = true
        ORDER BY d.lastName
    """)
    List<DoctorEntity> findBySpecialtyAndInsurance(
            @Param("specialty") String specialty,
            @Param("kassenärztlich") boolean kassenärztlich
    );
//
//    @Query("""
//        SELECT d FROM DoctorEntity d
//        WHERE EXISTS (
//            SELECT 1 FROM d.languages l WHERE l IN :languages
//        )
//        AND d.active = true
//        ORDER BY d.lastName
//    """)
//    List<DoctorEntity> findByAnyLanguageIn(@Param("languages") Set<String> languages);

    // -------------------------------------------------------------------------
    // Combined common search (Hausarzt in Berlin example)
    // -------------------------------------------------------------------------
    @Query("""
        SELECT d FROM DoctorEntity d
        JOIN d.category cat
        WHERE cat.name IN ('Allgemeinmediziner (Hausarzt)', 'Hausarzt')
          AND d.acceptsNewPatients = true

          AND d.city.name = :cityName
          AND d.active = true
        ORDER BY d.lastName
        LIMIT :limit
    """)
    List<DoctorEntity> findAvailableHouseDoctors(
            @Param("cityName") String cityName,
            @Param("languages") Set<String> languages,
            @Param("limit") int limit
    );

    // -------------------------------------------------------------------------
    // When location is re-added (PostGIS + Hibernate Spatial)
    // -------------------------------------------------------------------------
    /*
    @Query(value = """
        SELECT d.*,
               ST_Distance(be.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)) AS distance_m
        FROM infotree.doctors d
        JOIN infotree.base_entities be ON d.base_id = be.id
        WHERE be.is_active = true
          AND ST_DWithin(be.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :radiusMeters)
          AND :specialty = ANY(d.specialties)
        ORDER BY distance_m
        LIMIT :limit
        """, nativeQuery = true)
    List<Object[]> findNearestBySpecialty(
        @Param("lon") double longitude,
        @Param("lat") double latitude,
        @Param("radiusMeters") double radiusMeters,
        @Param("specialty") String specialty,
        @Param("limit") int limit
    );
    */
}