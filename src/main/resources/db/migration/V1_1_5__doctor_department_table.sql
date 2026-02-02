CREATE TABLE doctor_department (
                                   id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                   name_en VARCHAR(255) NOT NULL,
                                   name_bn VARCHAR(255) NOT NULL
);


INSERT INTO doctor_department (name_en, name_bn) VALUES
                                                     ('Psychiatrist', 'মনোরোগ বিশেষজ্ঞ'),
                                                     ('Cardiologist', 'হৃদরোগ বিশেষজ্ঞ'),
                                                     ('Piles Specialist (Proctologist)', 'পাইলস বিশেষজ্ঞ'),
                                                     ('Dentist', 'ডেন্টিস্ট'),
                                                     ('Dermatologist & Venereologist', 'চর্ম ও যৌন রোগ বিশেষজ্ঞ'),
                                                     ('Diabetes & Hormone Specialist (Endocrinologist)', 'ডায়াবেটিস ও হরমোন'),
                                                     ('ENT Specialist', 'নাক, কান ও গলা বিশেষজ্ঞ'),
                                                     ('Eye Specialist (Ophthalmologist)', 'চক্ষু বিশেষজ্ঞ'),
                                                     ('Liver Specialist (Hepatologist)', 'লিভার বিশেষজ্ঞ'),
                                                     ('Urologist', 'ইউরোলজি'),
                                                     ('General Surgeon', 'সার্জারি'),
                                                     ('Gynecologist', 'গাইনী বিশেষজ্ঞ'),
                                                     ('Hematologist', 'রক্তরোগ বিশেষজ্ঞ'),
                                                     ('Homeopathy Doctor', 'হোমিওপ্যাথি'),
                                                     ('Nasal Surgeon', 'নোজাল সার্জারি'),
                                                     ('Medicine Specialist (Internal Medicine)', 'মেডিসিন বিশেষজ্ঞ'),
                                                     ('Kidney Specialist (Nephrologist)', 'কিডনি রোগ বিশেষজ্ঞ'),
                                                     ('Neurosurgeon', 'নিউরো সার্জারি'),
                                                     ('Oral Disease Specialist', 'মুখ রোগ বিশেষজ্ঞ'),
                                                     ('Nutritionist', 'পুষ্টি বিশেষজ্ঞ'),
                                                     ('Cancer Specialist (Oncologist)', 'ক্যান্সার বিশেষজ্ঞ'),
                                                     ('Orthopedic Specialist', 'অর্থোপেডিক'),
                                                     ('Pain Specialist', 'ব্যথা বিশেষজ্ঞ'),
                                                     ('Child Specialist (Pediatrician)', 'শিশু রোগ বিশেষজ্ঞ'),
                                                     ('Physical Medicine Specialist', 'ফিজিক্যাল মেডিসিন'),
                                                     ('Physiotherapist', 'ফিজিও থেরাপিস্ট'),
                                                     ('Plastic Surgeon', 'প্লাস্টিক সার্জারি'),
                                                     ('Asthma & Chest Disease Specialist (Pulmonologist)', 'অ্যাজমা, বক্ষব্যাধি বিশেষজ্ঞ'),
                                                     ('Anesthesiologist', 'অ্যানেস্থেসিয়া টিম'),
                                                     ('Veterinary Doctor', 'পশু চিকিৎসক');
