package com.trbtree.infotree.modules.civic.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "civic_services", schema = "infotree")
@Getter
@Setter
public class CivicServiceEntity extends BaseEntity {

    @Column(name = "base_id", insertable = false, updatable = false)
    private Long baseId;  // optional – can be used for explicit FK reference

    @Column(nullable = false, length = 50)
    private String contactPhone;

    @Column(length = 100)
    private String contactEmail;

    @Column(nullable = false)
    private boolean is24h7 = false;

    @Column(nullable = false)
    private OffsetDateTime lastVerified;

    @Column(columnDefinition = "jsonb default '{}'::jsonb")
    private String extraAttributes;  // or use @Type(JsonBinaryType.class) + Map<String, Object>
}