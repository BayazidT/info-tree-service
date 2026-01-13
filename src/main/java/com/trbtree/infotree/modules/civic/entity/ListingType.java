package com.trbtree.infotree.modules.civic.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "listing_types", schema = "infotree")
@Getter
@Setter
public class ListingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_model", nullable = false)
    private BusinessModel businessModel;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    // Optional: description for admin UI
    @Column(columnDefinition = "TEXT")
    private String description;
}

