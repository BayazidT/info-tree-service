package com.trbtree.infotree.modules.civic.repository;
import com.trbtree.infotree.modules.civic.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    // Basic lookups
    Optional<City> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    // Search / autocomplete (useful for frontend dropdowns or search)
    List<City> findByNameContainingIgnoreCaseOrderByNameAsc(String nameFragment);

    // Top/popular cities (if you expand beyond Berlin)
    @Query("SELECT c FROM City c ORDER BY c.population DESC")
    List<City> findAllOrderedByPopulation();

    // When location is re-added (nearest cities)
    /*
    @Query(value = """
        SELECT c.*,
               ST_Distance(c.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)) AS distance_m
        FROM infotree.cities c
        ORDER BY distance_m
        LIMIT :limit
        """, nativeQuery = true)
    List<Object[]> findNearestCities(
        @Param("lon") double longitude,
        @Param("lat") double latitude,
        @Param("limit") int limit
    );
    */
}
