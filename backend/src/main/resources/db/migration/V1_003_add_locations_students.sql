-- Insert sample locations
INSERT INTO location (name) VALUES
    ('Bryant Elementary'),
    ('Geer Park Elementary'),
    ('Lowrey Middle School');

-- Insert sample students
INSERT INTO student (first_name, last_name, location_id) VALUES
     ('Amina', 'Hassan', 1),
     ('Omar', 'Rahman', 1),
     ('Layla', 'Karim', 2),
     ('Ali', 'Zidan', 2),
     ('Fatima', 'Nasir', 3),
     ('Zayd', 'Sabbah', 3);
