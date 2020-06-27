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
	   count_menu INTEGER;
	   count_order INTEGER;
    begin
	   select into count_menu count(*) from menu m inner join menu_weeks mw on m.id = mw.menu_id inner join week w on w.id = mw.weeks_id inner join week_days wd on wd.week_id = w.id
	   inner join "day" d on d.id = wd.days_id inner join day_moment_days dm on d.id = dm.day_id inner join moment_day md on md.id = dm.moment_days_id 
	   where m.end_date >= current_date and (md.dairy_product_id = old.id or md.dessert_id = old.id or md.dish_id = old.id or md.entry_id = old.id or md.garnish_id = old.id);
		
	   select into count_order COUNT(*) from "order" o inner join order_dairy_products odp on o.id = odp.order_id 
	   inner join order_desserts od on od.order_id = o.id 
	   inner join order_entries oe on oe.order_id = o.id 
	   inner join order_starchy_foods osf on osf.order_id = o.id 
	   inner join order_vegetables ov on ov.order_id = o.id where o.delivery_date >= current_date
	   and (odp.dairy_products_id = old.id or  od.desserts_id = old.id or oe.entries_id = old.id or osf.starchy_foods_id  = old.id or ov.vegetables_id = old.id);
	   
	   IF (count_menu > 0 OR count_order > 0) THEN 
	       RAISE EXCEPTION 'Le plat ne peut être supprimé, car il sera utilisé dans une prochaine commande ou un menu';
	   END IF;
	   RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verify_content
	BEFORE DELETE ON content
	FOR EACH ROW EXECUTE PROCEDURE verify_content();
