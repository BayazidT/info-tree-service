-- Add doctor_department_id to doctor table
ALTER TABLE doctors
    ADD COLUMN doctor_department_id BIGINT;

-- Add foreign key constraint
ALTER TABLE doctors
    ADD CONSTRAINT fk_doctor_department
        FOREIGN KEY (doctor_department_id)
            REFERENCES doctor_department(id)
            ON DELETE SET NULL;
