/*********************** ATTENTION ***********************/
/** IL FAUT SUIVRE L'ORDRE POUR L'INSERTION DES DONNEES **/
/*********************************************************/

/******************** ROLES ********************/
INSERT INTO role (id, name) VALUES
('1', 'ROLE_PATIENT'),
('2', 'ROLE_ADMIN'),
('3', 'ROLE_DIET'),
('4', 'ROLE_CAREGIVER');

/******************** LES UTILISATEURS DE L'APPLICATION ********************/
INSERT INTO "user" (id, first_name, last_name, email, birth_day, pseudo, password_hash, role_id, image_url, create_date, status) VALUES
('1', 'Angelo', 'DELIESSCHE', 'angelo.d@gmail.com', '1996-12-23', 'deliessche_a', '$2a$10$5yxgKe6eIYfraHbAORTDFOgaWNNCpiIPzLZM29tT9Mz9ZctDXP3GW', '2', null, '2020-03-21', 'true'),
('2', 'Christopher', 'LUKOMBO', 'christopher.l@gmail.com', '1996-08-31', 'lukombo_c', '$2a$10$RqauY9WksnugiHDSi2/4reDLJe2V1dubG5mNtkP6Y2bQNdu0FE3IG', '2', null, '2020-03-21', 'true'),
('3', 'Neal', 'KEMISSI', 'neal.k@gmail.com', '1997-05-23', 'kemissi_n', '$2a$10$RQTz5GoOW5nsLxR13jS/RehkxUh04CQ/WI7uh9K0iRy4kt0zrTQJi', '2', null, '2020-03-21', 'true'),
/** les aides soignants **/
('4', 'Olivier', 'GOULET', 'goulet.o@gmail.com', '1995-06-17', 'goulet_o', '$2a$10$X7vMrgZptyHyxwMmEhnw4e8PdwHxMj0eNWa4NmgOjW18AKXv4Poqi', '4', null, '2020-03-21', 'true'),
('5', 'Marie', 'BISAILLON', 'bisaillon.m@gmail.com', '1994-10-27', 'bisaillon_m', '$2a$10$YbaouKyB8r38OEyln.00tO2F0v0f9.OH0Yf3L5jVwAFgVQwrhoU3S', '4', null, '2020-03-21', 'true'),
('6', 'Laetitia', 'BEAUCHEMIN', 'beauchemin.l@gmail.com', '1994-02-08', 'beauchemin_l', '$2a$10$tTLoZWpck0crX0QiC7Pyz.7cl7p4CZRwGDe9BhlCtHULDvEZcvWX.', '4', null, '2020-03-21', 'true');

/******************** ETAGES DE LA CLINIQUE ********************/
INSERT INTO floor (id, number, state) VALUES
('1', '0', 'true'),
('2', '1', 'true'),
('3', '2', 'true');

/******************** CHAMBRES DE LA CLINIQUE ********************/
INSERT INTO room (id, number, state) VALUES
('1', '1', 'true'),
('2', '2', 'true'),
('3', '3', 'true'),
('4', '4', 'true'),
('5', '5', 'true'),
('6', '6A', 'true'), ('7', '6B', 'true'),
('8', '7A', 'true'), ('9', '7B', 'true'),
('10', '8', 'true'),
('11', '9', 'true'),
('12', '10', 'true'),
('13', '11', 'true'),
('14', '12', 'true'), ('15', '12A', 'true'), ('16', '12B', 'true'),
('17', '14', 'true'),
('18', '15', 'true'),
('19', '16A', 'true'), ('20', '16B', 'true'),
/** 1ere etage **/
('21', '101', 'true'),
('22', '102', 'true'),
('23', '103', 'true'),
('24', '104', 'true'),
('25', '105', 'true'),
('26', '106', 'true'),
('27', '107', 'true'),
('28', '108A', 'true'), ('29', '108B', 'true'),
('30', '109', 'true'),
('31', '110A', 'true'), ('32', '110B', 'true'),
('33', '111', 'true'),
('34', '112', 'true'),
('35', '113', 'true'),
('36', '114', 'true'),
('37', '115', 'true'),
('38', '116', 'true'),
('39', '117A', 'true'), ('40', '117B', 'true'),
('41', '118', 'true'),
('42', '119', 'true'),
('43', '120', 'true'),
('44', '121A', 'true'), ('45', '121B', 'true'),
/** 2eme etage **/
('46', '201', 'true'),
('47', '202', 'true'),
('48', '203', 'true'),
('49', '204', 'true'),
('50', '205', 'true'),
('51', '206', 'true'),
('52', '207', 'true'),
('53', '208A', 'true'), ('54', '208B', 'true'),
('55', '210A', 'true'),
('56', '210B', 'true'),
('57', '211', 'true'),
('58', '212', 'true'),
('59', '213', 'true'),
('60', '214', 'true'),
('61', '215', 'true'),
('62', '216', 'true'),
('63', '217A', 'true'), ('64', '217B', 'true'),
('65', '218', 'true'),
('66', '219', 'true'),
('67', '220', 'true'),
('68', '221', 'true'),
('69', '222A', 'true'), ('70', '222B', 'true');
/** les chambres de chaque etage **/
INSERT INTO floor_rooms (floor_id, rooms_id) VALUES
('1', '1'), ('1', '2'), ('1', '3'), ('1', '4'), ('1', '5'), ('1', '6'), ('1', '7'), ('1', '8'), ('1', '9'), ('1', '10'), ('1', '11'), ('1', '12'), ('1', '13'), ('1', '14'), ('1', '15'), ('1', '16'), ('1', '17'), ('1', '18'), ('1', '19'), ('1', '20'),
('2', '21'), ('2', '22'), ('2', '23'), ('2', '24'), ('2', '25'), ('2', '26'), ('2', '27'), ('2', '28'), ('2', '29'), ('2', '30'), ('2', '31'), ('2', '32'), ('2', '33'), ('2', '34'), ('2', '35'), ('2', '36'), ('2', '37'), ('2', '38'), ('2', '39'), ('2', '40'), ('2', '41'), ('2', '42'), ('2', '43'), ('2', '44'), ('2', '45'),
('3', '46'), ('3', '47'), ('3', '48'), ('3', '49'), ('3', '50'), ('3', '51'), ('3', '52'), ('3', '53'), ('3', '54'), ('3', '55'), ('3', '56'), ('3', '57'), ('3', '58'), ('3', '59'), ('3', '60'), ('3', '61'), ('3', '62'), ('3', '63'), ('3', '64'), ('3', '65'), ('3', '66'), ('3', '67'), ('3', '68'), ('3', '69'), ('3', '70');
/** les utilisateurs qui sont aides soignants **/
INSERT INTO caregiver (id, floor, user_id) VALUES
('1', '1','4'),
('2', '2', '5'),
('3', '3', '6');
/** les etages ayant un ou plusieurs aides soignants **/
INSERT INTO floor_caregivers (floor_id, caregivers_id) VALUES
('1', '1'),
('2', '2'),
('3', '3');

/******************** ALIMENTATIONS DES PATIENTS ********************/
INSERT INTO diet (id, name) VALUES
('1', 'Normale'),
('2', 'Hyperprotéiné'),
('3', 'Diabétique'),
('4', 'Sans porc'),
('5', 'Sans sel'),
('6', 'Sans résidu');

/******************** TEXTURES DES PLATS ********************/
INSERT INTO texture (id, name) VALUES
('1', 'Normale'),
('2', 'Haché'),
('3', 'Mixé');

/******************** ALLERGIES DES PATIENTS ********************/
INSERT INTO allergy (id, name) VALUES
('1', 'Céréales'),
('2', 'Gluten'),
('3', 'Crustacés'),
('4', 'Œufs'),
('5', 'Poissons'),
('6', 'Soja'),
('7', 'Lait'),
('8', 'Arachides'),
('9', 'Fruits à coques'),
('10', 'Céleri'),
('11', 'Moutarde'),
('12', 'Graines de sésame'),
('13', 'Anhydride sulfureux'),
('14', 'Lupin'),
('15', 'Mollusques');

/******************** COMMENTAIRES A PROPOS DES PATIENTS ********************/
INSERT INTO comment (id, content, pseudo) VALUES
('1', 'Souvent fatigué', 'deliessche_a'),
('2', 'A tendances à modifier son menu la veille', 'lukombo_c'),
('3', 'Patient régulier dans la clinique, revient pour la 4eme fois', 'kemissi_n'),
('4', 'Aime beaucoup les aliments sucrées', 'herbulot_s'),
('5', 'A changer de chambre rapidement', 'deliessche_a'),
('6', 'Présente des troubles du sommeil', 'lukombo_c'),
('7', 'Ne mange pas en grande quantité', 'kemissi_n'),
('8', 'Surveiller régulièrement son alimentation, car le patient ne connait pas toutes ses informations sur ses allergies', 'herbulot_s');

/******************** ADRESSES DES PATIENTS ********************/
INSERT INTO address (id, street_name, postal_code, city) VALUES
('1', '28 rue Roussy','93150','Le Blanc-Mesnil'),
('2', '43 rue des Couronnes', '75020', 'Paris'),
('3', '156 avenue Ledru Rollin', '75011', 'Paris'),
('4', '16 rue Bordier', '93300', 'Aubervilliers'),
('5', '67 boulevard de Belleville', '75011', 'Paris'),
('6', '4 rue Emile Zola', '94190', 'Villeneuve-Saint-Georges'),
('7', '95 avenue Anatole France', '94190', 'Villeneuve-Saint-Georges'),
('8', '19 rue Vincent Van Gogh', '94190', 'Villeneuve-Saint-Georges'),
('9', '23 avenue de Valenton', '94190', 'Villeneuve-Saint-Georges'),
('10', '21 avenue de la République', '94190', 'Villeneuve-Saint-Georges'),
('11', '5 rue Paul Bert', '94190', 'Villeneuve-Saint-Georges'),
('12', '8 rue Pommier', '94190', 'Villeneuve-Saint-Georges'),
('13', '12 rue Madeleine', '94190', 'Villeneuve-Saint-Georges'),
('14', '7 rue des Erables', '94190', 'Villeneuve-Saint-Georges'),
('15', '28 square de la Garenne', '95500', 'Gonesse'),
('16', '16 rue Eric Tabarly', '93420', 'Villepinte'),
('17', '26 rue Corneille', '93150', 'Le Blanc-Mesnil'),
('18', '3 rue Corneille', '75006', 'Paris'),
('19', '23 rue Jules Vallès', '93700', 'Drancy'),
('20', '15 rue Jules Vallès', '75011', 'Paris'),
('21', '26 rue Letellier', '75015', 'Paris'),
('22', '14 rue Fernand Ségouin', '95400', 'Arnouville'),
('23', '12 rue Firmin Gémier', '75018', 'Paris'),
('24', '16 rue Edgar Quinet', '93120', 'La Courneuve'),
('25', '149 avenue de la Division Leclerc', '93350', 'Le Bourget'),
('26', '6 rue Pierre Leroux', '75007', 'Paris'),
('27', '24 rue Marcellin Berthelot', '93350', 'Le Bourget'),
('28', '41 rue Marcel Sembat', '93350', 'Le Bourget'),
('29', '29 rue Auguste Mounié', '92160', 'Antony'),
('30', '23 rue du Calitor', '34500', 'Béziers'),
('31', '43 boulevard Frédéric Mistral', '34420', 'Villeneuve-lès-Béziers'),
('32', '8 square de la Poterne', '91300', 'Massy'),
('33', '8 rue Duvergier', '75019', 'Paris'),
('34', '3 avenue Bernard Hirsch', '95021', 'Cergy-Pontoise'),
('35', '160 rue de Sèvres', '75015', 'Paris'),
('36', '75 rue Vincent Fayo', '92290', 'Châtenay-Malabry'),
('37', '3 avenue des Aulnes', '78250', 'Meulan-en-Yvelines'),
('38', '9 allée Abel Gance', '78130', 'Les Mureaux'),
('39', '12 rue des 2 Gares', '78711', 'Mantes-la-Ville'),
('40', '10 avenue du Général de Gaulle', '92160', 'Antony'),
('41', '18 rue Galilée', '78500', 'Sartrouville'),
('42', '3 rue Thomire', '75013', 'Paris'),
('43', '1 rue Jean-François Lépine', '75018', 'Paris'),
('44', '6 rue d''Oslo', '75018', 'Paris'),
('45', '162 rue de Belleville', '75020', 'Paris'),
('46', '3 rue de Tourtille', '75020', 'Paris'),
('47', '53 rue Volta', '75003', 'Paris'),
('48', '35 rue Alphonse Daudet', '93700', 'Drancy'),
('49', '27 rue de Charonne', '75011', 'Paris'),
('50', '3 rue Mercoeur', '75011', 'Paris'),
('51', '24 rue de Pali-Kao', '75020', 'Paris'),
('52', '9 boulevard Souchet', '93160', 'Noisy-le-Grand'),
('53', '9 rue Dessous l''Eglise', '52400', 'Serqueux'),
('54', '15 rue Maitre Albert', '52400', 'Bourbonne-les-Bains'),
('55', '68 avenue du Président Salvador Allende', '93100', 'Montreuil'),
('56', '159 avenue Parmentier', '75010', 'Paris'),
('57', '6-8 impasse des 2 Cousins', '75017', 'Paris'),
('58', '4 bis rue Léon Blum', '91300', 'Massy'),
('59', '22 rue Guynemer', '93350', 'Le Bourget'),
('60', '4 avenue Pablo Picasso', '94190', 'Villeneuve-Saint-Georges'),
('61', '5 rue Henri Sellier', '94190', 'Villeneuve-Saint-Georges'),
('62', '9 rue Pasteur', '94190', 'Villeneuve-Saint-Georges'),
('63', '31 rue Émile Zola', '94190', 'Villeneuve-Saint-Georges'),
('64', '16 rue Diderot', '94190', 'Villeneuve-Saint-Georges'),
('65', '3 rue Gutenberg', '94190', 'Villeneuve-Saint-Georges'),
('66', '1 rue Jeanne d''Arc', '94190', 'Villeneuve-Saint-Georges'),
('67', '17 rue des Peupliers', '94190', 'Villeneuve-Saint-Georges'),
('68', '68 rue Gambetta', '94190', 'Villeneuve-Saint-Georges'),
('69', '6 rue Thimonnier', '94190', 'Villeneuve-Saint-Georges'),
('70', '58 avenue Benoit Frachon', '75020', 'Paris');

/******************** PATIENTS (68) ********************/
INSERT INTO patient (id, first_name, last_name, sex, date_of_birth, situation, job, email, phone_number, mobile_phone, address, height, weight, blood_group, texture_id, comment_id, room_id, state) VALUES
('1', 'Valérie', 'BORDIN', 'Femme', '1955-07-09', 'Marié', 'Retraité', 'bordin.v@gmail.com', '0126642363', '0652148965', '1', '163', '51.1', 'B+', '1', null, '1', 'true'),
('2', 'Charles', 'RAMASSAMY', 'Homme', '1975-02-13', 'Célibataire', 'job', 'ram.gaston@hotmail.fr', '0108216508', '0666658412', '2', '178', '98', 'O+', '1', null, '2', 'true'),
('3', 'Alena', 'KRAWCZYNSKI', 'Femme', '1957-11-14', 'Marié', 'Retraité', 'clarice_kr@hotmail.com', '0153003774', '0658697442', '3', '155', '51.1', 'A+', '1', null, '3', 'true'),
('4', 'Emmanuel', 'NUNES', 'Homme', '1935-03-12', 'Célibataire', 'Retraité', 'manu.nunes@gmail.com', '0154471366', '0659698443', '4', '171', '62.2', 'A+', '1', null, '4', 'true'),
('5', 'Émilie', 'GIRODOLLE', 'Femme', '1955-05-10', 'Divorcé', 'Retraité', 'girodolle3.e@hotmail.com', '0127149527', '0608064101', '5', '161', '68.9', 'A+', '1', null, '5', 'true'),
('6', 'Gueïda', 'FOFANA', 'Homme', '1969-09-10', 'Célibataire', 'Retraité', 'fofana.gueida@hotmail.fr', '0103988376', '0683137530', '6', '163', '85.4', 'A+', '1', '1', '6', 'true'),
('7', 'Aubin', 'GRIMBERT', 'Homme', '1957-01-27', 'Marié', 'Retraité', 'grim.aubin@gmail.com', '0155992564', '0699500743', '7', '185', '89.7', 'O+', '3', null, '7', 'true'),
('8', 'Laurence', 'SUINOT', 'Femme', '1945-01-29', 'Marié', 'Retraité', 'laurence.suin@hotmail.com', '0131797575', '0606726272', '8', '155', '99.1', 'O+', '1', null, '8', 'true'),
('9', 'Olivier', 'EIGERMAN', 'Homme', '1992-10-04', 'Célibataire', 'En activité', 'eigerman.olivier@yahoo.fr', '0113971175', '0678976377', '9', '168', '70.5', 'B+', '1', null, '9', 'true'),
('10', 'Jewel', 'SCHEIDEL', 'Femme', '1991-08-01', 'Veuf', 'En activité', 'jewel.scheidel@yahoo.fr', '0135852685', '0660244089', '10', '171', '91.4', 'O+', '1', '2', '10', 'true'),
('11', 'Nathalie', 'BANCO', 'Femme', '1983-05-05', 'Marié', 'En activité', 'banco.nat@laposte.fr', '0192292029', '0647074823', '11', '159', '72.9', 'O+', '1', null, '11', 'true'),
('12', 'Gauthier', 'DAGAN', 'Homme', '1959-03-17', 'Divorcé', 'En activité', 'gauthier.dan@hotmail.fr', '0110289014', '0629918261', '12', '166', '76.2', 'O-', '1', null, '12', 'true'),
('13', 'Camille', 'BRIGITTE', 'Femme', '1964-06-29', 'Divorcé', 'En activité', 'camille.brte@yahoo.fr', '0157728956', '0680683620', '13', '163', '65.8', 'B+', '1', null, '13', 'true'),
('14', 'Inès', 'SALMON', 'Femme', '1974-04-15', 'Célibataire', 'En activité', 'ines.salmon@hotmail.fr', '0194707873', '0604984289', '14', '168', '50.6', 'O-', '1', null, '14', 'true'),
('15', 'Alphonse', 'GINESTET', 'Homme', '1935-04-15', 'Marié', 'Retraité', 'alhponse.gines@hotmail.com', '0199500743', '0628323747', '15', '163', '76.7', 'A+', '1', null, '15', 'true'),
('16', 'Guershon', 'HEDDAD', 'Homme', '1975-09-10', 'Marié', 'En activité', 'headdad.g@gmail.com', '0183137530', '0635852685', '16', '171', '105.6', 'B+', '1', null, '16', 'true'),
('17', 'Delit', 'WARION', 'Femme', '1994-09-10', 'Marié', 'En activité', 'delit.warion@hotmail.fr', '0193640501', '0654471302', '17', '160', '55.7', 'O+', '1', null, '17', 'true'),
('18', 'Inès', 'DEL FRARI', 'Femme', '1974-03-17', 'Marié', 'En activité', 'ines.delfr@hotmail.com', '0120837926', '0650413730', '18', '170', '52.5', 'B-', '3', null, '18', 'true'),
('19', 'Gérard', 'MANSEUR', 'Homme', '1961-10-30', 'Marié', 'En activité', 'manseur.g@hotmail.fr', '0141390230', '0654851366', '19', '170', '72.5', 'O+', '1', null, '19', 'true'),
('20', 'Agnès', 'AVIT', 'Femme', '1994-10-30', 'Marié', 'Sans activité', 'avit.agnes@gmail.com', '0104984289', '0631797575', '20', '169', '92.3', 'B+', '1', null, '20', 'true'),
/** 1ere etage **/
('21', 'Noël', 'HUSER', 'Homme', '1999-08-27', 'Célibataire', 'Sans activité', 'huser.noel@gmail.com', '0184473685', '0651715260', '21', '172', '76.3', 'A-', '1', null, '21', 'true'),
('22', 'Victor', 'DOS SANTOS', 'Homme', '1997-08-27', 'Marié', 'Sans activité', 'santos.victor@hotmail.fr', '0159605521', '0697377231', '22', '157', '72.8', 'O+', '1', null, '22', 'true'),
('23', 'Robert', 'MENDES', 'Homme', '1987-03-20', 'Marié', 'En activité', 'mendes.robert@hotmail.com', '0151325590', '0692290172', '23', '160', '74.7', 'O+', '3', null, '23', 'true'),
('24', 'Paul', 'ROY', 'Homme', '1946-04-23', 'Marié', 'Retraité', 'roy.paul@hotmail.fr', '0123266891', '0671266762', '24', '186', '63.7', 'AB-', '1', null, '24', 'true'),
('25', 'Raina', 'BELLALA', 'Femme', '1984-10-23', 'Célibataire', 'En activité', 'raina.bellala@hotmail.fr', '0193784913', '0647155258', '25', '159', '96.5', 'A+', '1', null, '25', 'true'),
('26', 'Fatna', 'CHERIFI', 'Femme', '1964-09-01', 'Divorcé', 'En activité', 'fatna.ch@gmail.com', '0176665069', '0669110328', '26', '163', '69.3', 'A+', '1', null, '26', 'true'),
('27', 'Alexandre', 'GUEMACHE', 'Homme', '1966-05-15', 'Marié', 'En activité', 'guemache.alex@hotmail.fr', '0197685061', '0617920578', '27', '182', '101.0', 'O+', '1', null, '27', 'true'),
('28', 'Michel', 'BESSAI', 'Homme', '1976-10-23', 'Marié', 'En activité', 'bessai.michel@gmail.com', '0188726064', '0626439250', '28', '160', '58.0', 'A-', '1', '3', '28', 'true'),
('29', 'Rania', 'CHEHIBI', 'Femme', '1953-07-26', 'Marié', 'Retraité', 'chehibi.rania@hotmail.fr', '0151293330', '0604267803', '29', '184', '87.1', 'B+', '1', null, '29', 'true'),
('30', 'Capucine', 'LAMIDIEU', 'Femme', '1935-08-29', 'Marié', 'Retraité', 'capucine.lamidieu@hotmail.com', '0188001239', '0687403132', '30', '162', '95.1', 'A+', '1', null, '30', 'true'),
('31', 'Alexandra', 'HEMONET', 'Femme', '1986-11-26', 'Marié', 'Retraité', 'hemonet.alex@hotmail.fr', '0139105280', '0601407975', '31', '172', '93.7', 'A-', '1', null, '31', 'true'),
('32', 'Maria', 'KISELOV', 'Femme', '1992-02-04', 'Célibataire', 'Sans activité', 'kiselov.a@yahoo.fr', '0129372237', '0603033988', '32', '189', '96.5', 'A-', '1', null, '32', 'true'),
('33', 'Ernest', 'STOJANOVIC', 'Homme', '1968-11-22', 'Marié', 'Sans activité', 'stojernest@yahoo.fr', '0169436796', '0698308834', '33', '185', '83.9', 'A-', '1', null, '33', 'true'),
('34', 'Philippe', 'PASQUALINI', 'Homme', '1966-05-15', 'Célibataire', 'Sans activité', 'pasqualiniphilippe@laposte.fr', '0102003018', '0693059065', '34', '168', '109.8', 'O+', '1', null, '34', 'true'),
('35', 'Julie', 'MATUMUENE', 'Femme', '1966-05-15', 'Divorcé', 'En activité', 'juliematmat@laposte.fr', '0137254473', '0698527899', '35', '158', '88.1', 'AB-', '1', null, '35', 'true'),
('36', 'Aurélie', 'DAMETTE', 'Femme', '1986-02-06', 'Marié', 'En activité', 'aurelie.damette@gmail.com', '0174665426', '0663778623', '36', '184', '85.9', 'B-', '1', '4', '36', 'true'),
('37', 'Marie', 'LORCY', 'Femme', '1988-11-25', 'Marié', 'Sans activité', 'lorcy.marie@hotmail.fr', '0174665426', '0663778623', '37', '181', '78.8', 'B+', '1', null, '37', 'true'),
('38', 'Nathan', 'COUDRAYE', 'Homme', '1967-09-08', 'Célibataire', 'En activité', 'coudraye.nath@gmail.com', '0186048980', '0683017937', '38', '178', '58.2', 'O-', '1', null, '39', 'true'),
('39', 'Aurélien', 'GALLE', 'Homme', '1989-12-12', 'Célibataire', 'En activité', 'galleaurelien@gmail.com', '0124228943', '0681912141', '39', '173', '68.8', 'A-', '1', null, '40', 'true'),
('40', 'Yves', 'BRIGITTE', 'Homme', '1992-04-02', 'Veuf', 'En activité', 'yvesbrigitte@hotmail.fr', '0131773320', '0670533173', '40', '160', '107.6', 'A-', '1', '5', '41', 'true'),
('41', 'Mehdi', 'ADEL', 'Homme', '1992-04-02', 'Divorcé', 'En activité', 'mehdi.a@hotmail.com', '0139954883', '0629669261', '41', '164', '73.1', 'O+', '1', '6', '42', 'true'),
('42', 'Fabiana', 'DOS SANTOS', 'Femme', '1988-09-25', 'Marié', 'En activité', 'fabiana.dsantos@hotmail.fr', '0191375576', '0679464043', '42', '181', '101.2', 'B+', '1', null, '43', 'true'),
('43', 'Victor', 'FALSAPERLA', 'Homme', '1958-04-09', 'Marié', 'Retraité', 'victorvfal@hotmail.com', '0134727187', '0629289147', '43', '158', '59.1', 'A+', '1', null, '44', 'true'),
('44', 'Césaire', 'YOUDE', 'Homme', '2000-01-23', 'Divorcé', 'Sans activité', 'cesaire_youde@gmail.com', '0110625877', '0631886817', '44', '179', '90.6', 'A-', '1', null, '45', 'true'),
/** 2eme etage **/
('45', 'Gaetan', 'CHEVALIER', 'Homme', '1990-03-23', 'Divorcé', 'En activité', 'chevalier_gaetan@yahoo.fr', '0133091239', '0611953961', '45', '190', '82.8', 'O+', '1', null, '46', 'true'),
('46', 'Pauline', 'RANSOU', 'Femme', '1967-07-09', 'Divorcé', 'En activité', 'ransou_p@yahoo.fr', '0135548341', '0631202900', '46', '160', '107.0', 'O+', '1', null, '47', 'true'),
('47', 'Arnaud', 'MITEU', 'Homme', '1958-07-09', 'Veuf', 'En activité', 'arnaud_m@yahoo.fr', '0100307671', '0666903217', '47', '189', '91.5', 'O-', '1', null, '48', 'true'),
('48', 'Catherine', 'CHARLET', 'Femme', '1944-06-19', 'Marié', 'Retraité', 'catherine_chrt@hotmail.fr', '0158250038', '0695309446', '48', '182', '67.6', 'O+', '1', null, '49', 'true'),
('49', 'François', 'REGUIG', 'Homme', '1938-02-18', 'Marié', 'Retraité', 'fr_reg9@hotmail.fr', '0144208020', '0679987088', '49', '173', '93.0', 'A+', '1', null, '50', 'true'),
('50', 'Baptiste', 'GAUREL', 'Homme', '1984-10-23', 'Marié', 'En activité', 'baptiste_bg@hotmail.fr', '0118478422', '0649340863', '50', '183', '100.2', 'A+', '1', null, '51', 'true'),
('51', 'Sébastien', 'DESSEAUVE', 'Homme', '1984-10-23', 'Célibataire', 'En activité', 'seb_de_s@hotmail.fr', '0197076301', '0695276190', '51', '174', '72.1', 'A+', '3', null, '52', 'true'),
('52', 'Alexis', 'AMPIRI', 'Homme', '1984-11-23', 'Divorcé', 'En activité', 'alex_ampi@hotmail.com', '0151631893', '0688162500', '52', '161', '102.1', 'O-', '1', null, '53', 'true'),
('53', 'Jean-Louis', 'PRINCE', 'Homme', '1956-10-27', 'Divorcé', 'Retraité', 'jl.prince@gmail.com', '0139478018', '0669745820', '53', '160', '70.7', 'B+', '1', '7', '54', 'true'),
('54', 'Amélie', 'MAES', 'Femme', '1965-08-17', 'Célibataire', 'En activité', 'amel.maes@gmail.com', '0115463379', '0640983970', '54', '172', '93.3', 'O+', '1', null, '55', 'true'),
('55', 'Christelle', 'CANFRERE', 'Femme', '1992-04-02', 'Marié', 'Sans activité', 'christelle.c@gmail.com', '0155901586', '0655278011', '55', '175', '71.4', 'O+', '1', null, '56', 'true'),
('56', 'Olivier', 'LARROUCAU', 'Homme', '1993-12-12', 'Célibataire', 'Sans activité', 'oliv.larrouc@hotmail.com', '0114645788', '0695453739', '56', '186', '78.2', 'B-', '3', null, '57', 'true'),
('57', 'Annie', 'JACQUET', 'Femme', '1989-02-04', 'Marié', 'Sans activité', 'annie.jacquet@gmail.com', '0104386045', '0602443755', '57', '158', '77.6', 'A-', '1', null, '58', 'true'),
('58', 'William', 'PREVOT', 'Homme', '1992-08-14', 'Marié', 'Sans activité', 'williprevot@gmail.com', '0141276607', '0627313316', '58', '187', '93.7', 'AB+', '1', null, '59', 'true'),
('59', 'Miguel', 'RODRIGUES', 'Homme', '1995-01-01', 'Marié', 'Sans activité', 'miguel.rodri@yahoo.fr', '0108972723', '0656138996', '59', '178', '100.1', 'AB+', '3', null, '60', 'true'),
('60', 'Barbara', 'SALFATI', 'Femme', '1992-11-10', 'Célibataire', 'Sans activité', 'barbara.salfati@laposte.fr', '0199318375', '0628134233', '60', '183', '81.7', 'O+', '1', null, '61', 'true'),
('61', 'Fabien', 'GALLUD', 'Homme', '1966-10-27', 'Veuf', 'En activité', 'fabien_g@laposte.fr', '0162308175', '0689463718', '61', '181', '85.3', 'A-', '1', null, '62', 'true'),
('62', 'Benjamin', 'MOREL', 'Homme', '1947-01-13', 'Marié', 'Retraité', 'benjamin_m@yahoo.fr', '0154032736', '0698656860', '62', '173', '100.7', 'A+', '1', '8', '65', 'true'),
('63', 'Marine', 'VOILLOT', 'Femme', '1950-02-01', 'Célibataire', 'Retraité', 'marine.v@gmail.com', '0112738030', '0687812030', '63', '170', '103.8', 'B+', '1', null, '66', 'true'),
('64', 'Dimitri', 'DUBOIS', 'Homme', '1951-01-13', 'Célibataire', 'Retraité', 'dimitri.d@gmail.com', '0134379522', '0674228990', '64', '188', '90.6', 'A+', '1', null, '67', 'true'),
('65', 'Laura', 'BRIARD', 'Femme', '1947-01-13', 'Divorcé', 'Retraité', 'laura_b@gmail.com', '0107008057', '0654110010', '65', '166', '76.2', 'A+', '1', null, '68', 'true'),
('66', 'Hugo', 'PRIEUR', 'Homme', '1976-01-13', 'Célibataire', 'En activité', 'hugo.prieur@gmail.com', '0166178412', '0626681182', '66', '175', '92.7', 'O+', '1', null, '69', 'true'),
('67', 'Sébastien', 'ROUSSEAU', 'Homme', '1976-06-17', 'Divorcé', 'En activité', 'seb.rousseau@gmail.com', '0164950186', '0679775220', '67', '155', '57.2', 'AB+', '1', null, '70', 'true');
/** type d'alimentation des patients : normale(1), HP(2), diabetique(3), sans porc(4), sans sel(5), sans residu(6) **/
INSERT INTO patient_diets (patient_id, diets_id) VALUES
('1', '5'),
('2', '3'),
('3', '2'), ('3', '3'),
('4', '3'),
('5', '5'),
('6', '3'), ('6', '4'),
('7', '1'),
('8', '1'),
('9', '1'),
('10', '1'),
('11', '1'), ('11', '2'),
('12', '1'),
('13', '1'),
('14', '2'), ('14', '6'),
('15', '1'), ('15', '2'),
('16', '1'), ('16', '4'),
('17', '1'),
('18', '1'),
('19', '1'), ('19', '4'),
('20', '4'), ('20', '6'),
('21', '1'),
('22', '5'),
('23', '1'), ('23', '2'),
('24', '2'), ('24', '3'),
('25', '1'), ('25', '2'),
('26', '2'), ('26', '5'),
('27', '3'), ('27', '4'),
('28', '1'), ('28', '4'),
('29', '2'), ('29', '5'),
('30', '2'), ('30', '3'),
('31', '1'),
('32', '1'), ('32', '2'),
('33', '3'),
('34', '3'),
('35', '1'),
('36', '2'), ('36', '3'),
('37', '3'), ('37', '5'),
('38', '1'),
('39', '2'), ('39', '3'),
('40', '1'), ('39', '2'),
('41', '1'),
('42', '1'),
('43', '1'),
('44', '1'),
('45', '1'), ('45', '2'),
('46', '1'), ('46', '2'),
('47', '1'), ('47', '2'),
('48', '1'),
('49', '1'), ('49', '4'),
('50', '3'),
('51', '2'), ('51', '5'),
('52', '3'),
('53', '3'), ('53', '5'),
('54', '1'),
('55', '1'),
('56', '1'), ('56', '2'),
('57', '1'), ('57', '2'),
('58', '2'), ('58', '3'),
('59', '2'), ('59', '5'),
('60', '3'),
('61', '1'), ('61', '2'),
('62', '3'),
('63', '1'), ('63', '2'),
('64', '2'), ('64', '3'),
('65', '1'), ('65', '2'),
('66', '3'),
('67', '3');
/** les allergies de chaque patient **/
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES
('1', '5'),
('2', '3'),
('3', '2'), ('3', '3'),
('4', '3'),
('5', '5'),
('6', '3'), ('6', '4'),
('7', '6'), ('7', '4'),
('8', '6'),
('9', '10'), ('9', '4'),
('10', '11'),
('11', '1'), ('11', '2'),
('12', '12'),
('13', '11'),
('14', '2'), ('14', '6'), ('14', '7'),
('15', '8'), ('15', '2'),
('16', '4'), ('16', '5'),
('17', '9'),
('18', '10'),
('19', '2'), ('19', '4'), ('19', '15'),
('20', '4'), ('20', '6'),
('21', '1'),
('22', '5'), ('22', '15'),
('23', '1'), ('23', '2'),
('24', '2'), ('24', '13'),
('25', '8'), ('25', '7'),
('26', '2'), 
('27', '3'), ('27', '4'),
('28', '10'), ('28', '4'), ('28', '14'),
('29', '2'), ('29', '5'),
('30', '2'), ('30', '3'),
('31', '11'), ('31', '13'),
('32', '1'), ('32', '12'),
('33', '13'),
('34', '3'), ('34', '3'),
('35', '12'), ('35', '5'),
('36', '2'), ('36', '3'),
('37', '3'), 
('38', '1'), ('38', '5'),
('39', '2'), ('39', '3'), ('39', '13'), ('39', '15'),
('40', '1'), ('39', '2'),
('41', '9'),
('42', '9'), ('42', '3'),
('43', '8'), ('43', '4'), ('43', '5'),
('44', '7'),
('45', '11'), ('45', '6'),
('46', '4'), ('46', '7'),
('47', '7'), ('47', '6'),
('48', '7'),
('49', '1'), ('49', '4'),
('50', '3'),
('51', '2'), ('51', '15'),
('52', '3'),
('53', '4'), ('53', '5'),
('54', '10'),
('55', '5'), ('55', '6'),
('56', '1'), ('56', '2'),
('57', '5'), 
('58', '2'), 
('59', '8'), ('59', '5'),
('60', '3'),
('61', '6'), ('61', '2'),
('62', '3'),
('63', '4'), ('63', '2'),
('64', '2'), 
('65', '1'), ('65', '2'),
('66', '3'), ('66', '4'),
('67', '3'), ('67', '13');