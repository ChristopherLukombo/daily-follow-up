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

/******************** ETAGES DE LA CLINIQUE ********************/
INSERT INTO floor (number, state) VALUES
('0', 'true'),
('1', 'true'),
('2', 'true');

/******************** CHAMBRES DE LA CLINIQUE ********************/
INSERT INTO room (number, state, max_capacity) VALUES
('1', 'true', 1),
('2', 'true', 1),
('3', 'true', 1),
('4', 'true', 1),
('5', 'true', 1),
('6A', 'true', 1), ('6B', 'true', 1),
('7A', 'true', 1), ('7B', 'true', 1),
('8', 'true', 1),
('9', 'true', 1),
('10', 'true', 1),
('11', 'true', 1),
('12', 'true', 1),
('12A', 'true', 1),
('12B', 'true', 1),
('14', 'true', 1),
('15', 'true', 1),
('16A', 'true', 1),
('16B', 'true', 1),
/** 1ere etage **/
('101', 'true', 1),
('102', 'true', 1),
('103', 'true', 1),
('104', 'true', 1),
('105', 'true', 1),
('106', 'true', 1),
('107', 'true', 1),
('108A', 'true', 1), 
('108B', 'true', 1),
('109', 'true', 1),
('110A', 'true', 1),
('110B', 'true', 1),
('111', 'true', 1),
('112', 'true', 1),
('113', 'true', 1),
('114', 'true', 1),
('115', 'true', 1),
('116', 'true', 1),
('117A', 'true', 1),
('117B', 'true', 1),
('118', 'true', 1),
('119', 'true', 1),
('120', 'true', 1),
('121A', 'true', 1),
('121B', 'true', 1),
/** 2eme etage **/
('201', 'true', 1),
('202', 'true', 1),
('203', 'true', 1),
('204', 'true', 1),
('205', 'true', 1),
('206', 'true', 1),
('207', 'true', 1),
('208A', 'true', 1),
('208B', 'true', 1),
('210A', 'true', 1),
('210B', 'true', 1),
('211', 'true', 1),
('212', 'true', 1),
('213', 'true', 1),
('214', 'true', 1),
('215', 'true', 1),
('216', 'true', 1),
('217A', 'true', 1),
('217B', 'true', 1),
('218', 'true', 1),
('219', 'true', 1),
('220', 'true', 1),
('221', 'true', 1),
('222A', 'true', 1),
('222B', 'true', 1);
