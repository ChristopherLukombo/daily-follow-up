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
