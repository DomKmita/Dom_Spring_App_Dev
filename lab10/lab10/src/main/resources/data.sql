INSERT INTO myusers (email, password, first_name, last_name, role, enabled, account_non_expired, credentials_non_expired, account_non_locked)
VALUES ('alice.wonderland@example.com', '$2a$10$jPOV8aXOKupAi7UCJVvMaeEzBXjjLEblskQkB3ZAtNww2ATQjp8QK', 'Alice', 'Wonderland', 'ROLE_ADMIN', TRUE, TRUE, TRUE, TRUE);

INSERT INTO myusers (email, password, first_name, last_name, role, enabled, account_non_expired, credentials_non_expired, account_non_locked)
VALUES ('bob.builder@example.com', '$2a$10$jPOV8aXOKupAi7UCJVvMaeEzBXjjLEblskQkB3ZAtNww2ATQjp8QK', 'Bob', 'Builder', 'ROLE_API', TRUE, TRUE, TRUE, TRUE);

INSERT INTO myusers (email, password, first_name, last_name, role, enabled, account_non_expired, credentials_non_expired, account_non_locked)
VALUES ('charlie.brown@example.com', '$2a$10$jPOV8aXOKupAi7UCJVvMaeEzBXjjLEblskQkB3ZAtNww2ATQjp8QK', 'Charlie', 'Brown', 'ROLE_USER', TRUE, TRUE, TRUE, TRUE);

INSERT INTO myusers (email, password, first_name, last_name, role, enabled, account_non_expired, credentials_non_expired, account_non_locked)
VALUES ('diana.prince@example.com', '$2a$10$jPOV8aXOKupAi7UCJVvMaeEzBXjjLEblskQkB3ZAtNww2ATQjp8QK', 'Diana', 'Prince', 'ROLE_USER', TRUE, TRUE, TRUE, TRUE);

INSERT INTO myusers (email, password, first_name, last_name, role, enabled, account_non_expired, credentials_non_expired, account_non_locked)
VALUES ('edward.snow@example.com', '$2a$10$jPOV8aXOKupAi7UCJVvMaeEzBXjjLEblskQkB3ZAtNww2ATQjp8QK', 'Edward', 'Snow', 'ROLE_ADMIN', TRUE, TRUE, TRUE, FALSE);



INSERT INTO household (eircode, number_of_occupants, max_number_of_occupants, owner_occupied) VALUES
                                                                                                  ('D02XY45', 3, 5, 1),
                                                                                                  ('A94B6F3', 4, 6, 0),
                                                                                                  ('T12AB34', 2, 4, 1),
                                                                                                  ('C15DE67', 5, 7, 1),
                                                                                                  ('F12GH89', 1, 2, 0),
                                                                                                  ('B78IJ01', 3, 5, 1),
                                                                                                  ('M34KL56', 4, 6, 0),
                                                                                                  ('P90QR78', 2, 4, 1),
                                                                                                  ('V23ST01', 5, 7, 1),
                                                                                                  ('X45UV67', 1, 2, 0),
                                                                                                  ('Y67WX89', 3, 5, 1),
                                                                                                  ('Z01YZ23', 4, 6, 0),
                                                                                                  ('Q45AB78', 2, 4, 1),
                                                                                                  ('R67CD01', 5, 7, 1),
                                                                                                  ('S23EF45', 1, 2, 0);

INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Buddy', 'Dog', 'Golden Retriever', 3, 'D02XY45');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Mittens', 'Cat', 'Siamese', 2, 'D02XY45');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Charlie', 'Dog', 'Beagle', 4, 'T12AB34');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Whiskers', 'Cat', 'Persian', 5, 'M34KL56');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Coco', 'Rabbit', 'Holland Lop', 1, 'V23ST01');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Goldie', 'Fish', 'Goldfish', 1, 'Z01YZ23');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Polly', 'Bird', 'Parakeet', 2, 'R67CD01');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Max', 'Dog', 'German Shepherd', 5, 'S23EF45');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Luna', 'Cat', 'Maine Coon', 3, 'Z01YZ23');
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES ('Nibbles', 'Hamster', 'Syrian Hamster', 1, 'D02XY45');
