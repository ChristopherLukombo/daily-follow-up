/*********************** ATTENTION ***********************/
/** IL FAUT SUIVRE L'ORDRE POUR L'INSERTION DES DONNEES **/
/*********************************************************/

/******************** ROLES ********************/
INSERT INTO role (id, name) VALUES
('1', 'ROLE_PATIENT'),
('2', 'ROLE_ADMIN'),
('3', 'ROLE_DIET'),
('4', 'ROLE_CAREGIVER');

/******************** ALIMENTATIONS ********************/
INSERT INTO diet (id, name) VALUES
('1', 'Normale'),
('2', 'Hyperprotéiné'),
('3', 'Diabétique'),
('4', 'Sans porc'),
('5', 'Sans sel'),
('6', 'Sans résidu');

/******************** TEXTURES ********************/
INSERT INTO texture (id, name) VALUES
('1', 'Normale'),
('2', 'Haché'),
('3', 'Mixé');

/******************** ALLERGIES ********************/
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

/******************** ADRESSES ********************/
INSERT INTO address (id, street_name, postal_code, city) VALUES
('1', '28 rue Roussy','93150','Le Blanc-Mesnil'),
('2', '43 rue des Couronnes', '75020', 'Paris'),
('3', '156 avenue Ledru Rollin', '75011', 'Paris'),
('4', '16 rue Bordier', '93300', 'Aubervilliers'),
('5', '67 boulevard de Belleville', '75011', 'Paris'),
('6', '58 avenue Benoit Frachon', '75020', 'Paris'),
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
('70', '4 rue Emile Zola', '94190', 'Villeneuve-Saint-Georges');



/******************** PATIENTS (68) ********************/
/*TODO : date de naissance, adress object*/
#INSERT INTO patient (id, first_name, last_name, sex, situation, job, email, phone_number, mobile_phone, address, postal_code, ) VALUES