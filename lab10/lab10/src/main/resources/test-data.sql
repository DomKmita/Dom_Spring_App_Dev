-- Insert Sample Data into Households
INSERT INTO households (eircode, number_of_occupants, max_number_of_occupants, owner_occupied) VALUES
                                                                                                   ('EIR123', 3, 5, TRUE),
                                                                                                   ('EIR456', 2, 4, FALSE),
                                                                                                   ('EIR789', 0, 2, TRUE),
                                                                                                   ('EIR202', 1, 3, FALSE),
                                                                                                   ('EIR101', 5, 5, TRUE);

-- Insert Sample Data into Pets
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES
                                                              ('Buddy', 'Dog', 'Labrador', 3, 'EIR123'),
                                                              ('Mittens', 'Cat', 'Persian', 2, 'EIR123'),
                                                              ('Charlie', 'Dog', 'Beagle', 5, 'EIR456'),
                                                              ('Goldie', 'Fish', 'Goldfish', 1, 'EIR456');
