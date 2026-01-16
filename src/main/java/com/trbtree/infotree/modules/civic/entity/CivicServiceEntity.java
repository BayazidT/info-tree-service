package com.trbtree.infotree.modules.civic.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;

@Entity
@Table(name = "civic_services", schema = "infotree")
@PrimaryKeyJoinColumn(name = "base_id")
@Getter
@Setter
public class CivicServiceEntity extends BaseEntity {

    @Column(name = "base_id", insertable = false, updatable = false)
    private Long baseId;  // optional – can be used for explicit FK reference

    @Column(nullable = false, length = 50)
    private String contactPhone;

    @Column(length = 100)
    private String contactEmail;

    @Column(name = "is_24_7", nullable = false)
    private boolean is24h7 = false;

    @Column(nullable = false)
    private OffsetDateTime lastVerified;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_attributes")
    private Map<String, Object> extraAttributes; // or use @Type(JsonBinaryType.class) + Map<String, Object>
}