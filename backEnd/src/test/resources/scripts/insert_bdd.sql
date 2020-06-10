/*********************** ATTENTION ***********************/
/** IL FAUT SUIVRE L'ORDRE POUR L'INSERTION DES DONNEES **/
/*********************************************************/

/******************** ROLES ********************/
INSERT INTO role (name) VALUES
('ROLE_PATIENT'),
('ROLE_ADMIN'),
('ROLE_DIET'),
('ROLE_CAREGIVER');

/******************** LES UTILISATEURS DE L'APPLICATION ********************/
INSERT INTO "user" (first_name, last_name, pseudo, password_hash, role_id, image_url, create_date, status) VALUES
('Angelo', 'DELIESSCHE', 'deliessche_a', '$2a$10$5yxgKe6eIYfraHbAORTDFOgaWNNCpiIPzLZM29tT9Mz9ZctDXP3GW', '1', null, '2020-03-21', 'true'),
('Christopher', 'LUKOMBO', 'lukombo_c', '$2a$10$RqauY9WksnugiHDSi2/4reDLJe2V1dubG5mNtkP6Y2bQNdu0FE3IG', '1', null, '2020-03-21', 'true'),
('Neal', 'KEMISSI', 'kemissi_n', '$2a$10$RQTz5GoOW5nsLxR13jS/RehkxUh04CQ/WI7uh9K0iRy4kt0zrTQJi', '1', null, '2020-03-21', 'true'),
/** les aides soignants **/
('Olivier', 'GOULET', 'goulet_o', '$2a$10$X7vMrgZptyHyxwMmEhnw4e8PdwHxMj0eNWa4NmgOjW18AKXv4Poqi', '3', null, '2020-03-21', 'true'),
('Marie', 'BISAILLON', 'bisaillon_m', '$2a$10$YbaouKyB8r38OEyln.00tO2F0v0f9.OH0Yf3L5jVwAFgVQwrhoU3S', '3', null, '2020-03-21', 'true'),
('Laetitia', 'BEAUCHEMIN', 'beauchemin_l', '$2a$10$tTLoZWpck0crX0QiC7Pyz.7cl7p4CZRwGDe9BhlCtHULDvEZcvWX.', '3', null, '2020-03-21', 'true');

/******************** ALIMENTATIONS DES PATIENTS ********************/
INSERT INTO diet (name) VALUES
('Normale'),
('Hyperprotéiné'),
('Diabétique'),
('Sans porc'),
('Sans sel'),
('Sans résidu');

/******************** TEXTURES DES PLATS ********************/
INSERT INTO texture (name) VALUES
('Normale'),
('Haché'),
('Mixé');