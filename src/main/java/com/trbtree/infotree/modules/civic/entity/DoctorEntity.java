package com.trbtree.infotree.modules.civic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "doctors", schema = "infotree")
@PrimaryKeyJoinColumn(name = "base_id")   // ← this line fixes it
@Getter
@Setter
public class DoctorEntity extends BaseEntity {

    // -------------------------------------------------------------------------
    // Personal / Professional Identification
    // -------------------------------------------------------------------------
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;     // Dr. med., Prof. Dr. med., etc.

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;                  // M, F, D, X (divers)

    @Column(name = "id_number", length = 20, unique = true)
    private String idNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_department_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_doctor_department")
    )
    private DoctorDepartment doctorDepartment;
// Approbation / license number

    // -------------------------------------------------------------------------
    // Specialties & Languages
    // -------------------------------------------------------------------------
//    @Column(name = "specialties")
//    private String specialties;

    // -------------------------------------------------------------------------
    // Insurance & Availability
    // -------------------------------------------------------------------------
    @Column(name = "private_patients_only")
    private boolean privatePatientsOnly = false;

    @Column(name = "accepts_new_patients", nullable = false)
    private boolean acceptsNewPatients = true;

    @Column(name = "telemedicine_available")
    private boolean telemedicineAvailable = false;

    @Column(name = "appointment_url", length = 300)
    private String appointmentUrl;

    @Column(name = "emergency_appointments")
    private boolean emergencyAppointments = false;

    // -------------------------------------------------------------------------
    // Scheduling & Extra Data
    // -------------------------------------------------------------------------
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "consultation_hours", columnDefinition = "jsonb default '{}'::jsonb")
    private Map<String, String> consultationHours = new HashMap<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_attributes", columnDefinition = "jsonb default '{}'::jsonb")
    private Map<String, Object> extraAttributes = new HashMap<>();


    // -------------------------------------------------------------------------
    // Enums
    // -------------------------------------------------------------------------
    public enum Gender {
        M, F, D, X
    }
}