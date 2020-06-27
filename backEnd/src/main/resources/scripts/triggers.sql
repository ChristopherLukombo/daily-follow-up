/*********************** ATTENTION ***********************/
/** IL FAUT SUIVRE L'ORDRE POUR LA CREATION DU TRIGGER **/
/*********************************************************/

/**
 * Ce trigger se charge de vérifier la capacité de la chambre, lorsqu'on crée le patient.
 */
CREATE OR REPLACE FUNCTION verify_capacity_room() RETURNS TRIGGER AS $$
    DECLARE 
        capacity INTEGER;
        max_capacity_room INTEGER;
    BEGIN
	   SELECT INTO capacity COUNT(*) FROM patient p INNER JOIN room r ON p.room_id = r.id WHERE r.id = NEW.room_id;
	   SELECT INTO max_capacity_room  r.max_capacity FROM room r WHERE r.id = NEW.room_id;
	   IF ((OLD.room_id != NEW.room_id OR OLD.room_id IS NULL) AND capacity = max_capacity_room) THEN 
	       RAISE EXCEPTION 'La capacité maximun de la chambre a été atteinte';
	   END IF;
	   RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verify_capacity_room
	BEFORE INSERT OR UPDATE ON public.patient 
	FOR EACH ROW EXECUTE PROCEDURE verify_capacity_room();
	

CREATE OR REPLACE FUNCTION verify_content() RETURNS TRIGGER AS $$
	DECLARE 
	   count_menu INTEGER = 0;
	   count_order INTEGER = 0;
    BEGIN
		SELECT
			INTO count_menu COUNT(*)
		FROM
			menu m
		INNER JOIN menu_weeks mw ON
			m.id = mw.menu_id
		INNER JOIN week w ON
			w.id = mw.weeks_id
		INNER JOIN week_days wd ON
			wd.week_id = w.id
		INNER JOIN "day" d ON
			d.id = wd.days_id
		INNER JOIN day_moment_days dm ON
			d.id = dm.day_id
		INNER JOIN moment_day md ON
			md.id = dm.moment_days_id
		INNER JOIN replacement r ON
			r.id = m.replacement_id
		INNER JOIN replacement_dairy_products rdp ON
			rdp.replacement_id = r.id
		INNER JOIN replacement_desserts rd ON
			rd.replacement_id = r.id
		INNER JOIN replacement_dishes rdi ON
			rdi.replacement_id = r.id
		INNER JOIN replacement_starchy_foods rsf ON
			rsf.replacement_id = r.id
		INNER JOIN replacement_vegetables rv ON
			rv.replacement_id = r.id
		INNER JOIN replacement_entries re ON
			re.replacement_id = r.id
		WHERE
			m.end_date >= CURRENT_DATE
			AND (md.dairy_product_id = OLD.id
			OR md.dessert_id = OLD.id
			OR md.dish_id = OLD.id
			OR md.entry_id = OLD.id
			OR md.garnish_id = OLD.id
			OR rdp.dairy_products_id = OLD.id
			OR rd.desserts_id = OLD.id
			OR rdi.dishes_id = OLD.id
			OR rsf.starchy_foods_id = OLD.id
			OR rv.vegetables_id = OLD.id
			OR re.entries_id = OLD.id);

		SELECT
			INTO count_order COUNT(*)
		FROM
			"order" o
		INNER JOIN order_dairy_products odp ON
			o.id = odp.order_id
		INNER JOIN order_desserts od ON
			od.order_id = o.id
		INNER JOIN order_entries oe ON
			oe.order_id = o.id
		INNER JOIN order_starchy_foods osf ON
			osf.order_id = o.id
		INNER JOIN order_vegetables ov ON
			ov.order_id = o.id
		WHERE
			o.delivery_date >= CURRENT_DATE
			AND (odp.dairy_products_id = OLD.id
			OR od.desserts_id = OLD.id
			OR oe.entries_id = OLD.id
			OR osf.starchy_foods_id = OLD.id
			OR ov.vegetables_id = OLD.id);

	  IF (count_menu > 0 OR count_order > 0) THEN 
	       RAISE EXCEPTION 'Le plat ne peut être supprimé, car il sera utilisé dans une prochaine commande ou un menu';
	   END IF;
	   RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verify_content
	BEFORE DELETE ON "content"
	FOR EACH ROW EXECUTE PROCEDURE verify_content();
