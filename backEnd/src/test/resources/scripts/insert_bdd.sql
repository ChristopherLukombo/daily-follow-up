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
INSERT INTO "user" (first_name, last_name, email, birth_day, pseudo, password_hash, role_id, image_url, create_date, status) VALUES
('Angelo', 'DELIESSCHE', 'angelo.d@gmail.com', '1996-12-23', 'deliessche_a', '$2a$10$5yxgKe6eIYfraHbAORTDFOgaWNNCpiIPzLZM29tT9Mz9ZctDXP3GW', '2', null, '2020-03-21', 'true'),
('Christopher', 'LUKOMBO', 'christopher.l@gmail.com', '1996-08-31', 'lukombo_c', '$2a$10$RqauY9WksnugiHDSi2/4reDLJe2V1dubG5mNtkP6Y2bQNdu0FE3IG', '2', null, '2020-03-21', 'true'),
('Neal', 'KEMISSI', 'neal.k@gmail.com', '1997-05-23', 'kemissi_n', '$2a$10$RQTz5GoOW5nsLxR13jS/RehkxUh04CQ/WI7uh9K0iRy4kt0zrTQJi', '2', null, '2020-03-21', 'true'),
/** les aides soignants **/
('Olivier', 'GOULET', 'goulet.o@gmail.com', '1995-06-17', 'goulet_o', '$2a$10$/ESoewZYHNOXx4FwI.23NeA6RvqdcHZ6IHRWVcNFc.sXVez4p2feS', '4', 2, '2020-03-21', 'true'),
('Marie', 'BISAILLON', 'bisaillon.m@gmail.com', '1994-10-27', 'bisaillon_m', '$2a$10$YbaouKyB8r38OEyln.00tO2F0v0f9.OH0Yf3L5jVwAFgVQwrhoU3S', '4', null, '2020-03-21', 'true'),
('Laetitia', 'BEAUCHEMIN', 'beauchemin.l@gmail.com', '1994-02-08', 'beauchemin_l', '$2a$10$tTLoZWpck0crX0QiC7Pyz.7cl7p4CZRwGDe9BhlCtHULDvEZcvWX.', '4', null, '2020-03-21', 'true');

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