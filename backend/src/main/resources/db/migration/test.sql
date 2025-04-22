-- Insert some instruments
INSERT INTO instrument (type, brand, serial_number, inventory_number, repairs, condition, purchase_date, purchase_price, notes, location_id, student_id)
VALUES
    ('Violin', 'Yamaha', 'SN123456', 'INV-001', 'None', 'Excellent', '2022-08-15', 450.00, 'Student model violin', 1, 1),
    ('Clarinet', 'Buffet Crampon', 'CL456789', 'INV-002', 'Repaired key spring', 'Good', '2021-01-20', 700.00, 'Used in advanced band', 2, NULL),
    ('Trumpet', 'Bach', 'TP789012', 'INV-003', 'Dent removed from bell', 'Fair', '2020-05-10', 1200.00, 'Senior loaner', 3, 2),
    ('Flute', 'Gemeinhardt', 'FL345678', 'INV-004', 'Headjoint replaced', 'Good', '2019-09-01', 600.00, 'Used for beginners', 1, NULL),
    ('Cello', 'Eastman', 'CE234567', 'INV-005', 'Crack repaired in top plate', 'Fair', '2018-03-25', 1500.00, 'Heavy use', 2, 3);
