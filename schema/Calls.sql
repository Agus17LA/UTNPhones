/* Trigger llenado de tabla calls */
DELIMITER //
DROP TRIGGER IF EXISTS complete_info_of_call;
CREATE TRIGGER complete_info_of_call
BEFORE INSERT ON calls
FOR EACH ROW
BEGIN
	DECLARE V_id_number_origin int;
    DECLARE V_id_number_destination int;
    DECLARE V_id_city_origin int;
    DECLARE V_id_city_destination int;
    DECLARE V_total_cost float;
    DECLARE V_total_price float;
    
	SELECT o.id, d.id INTO V_id_number_origin, V_id_number_destination
    FROM phone_lines o, phone_lines d 
    WHERE o.number_line = NEW.origin_number_line 
    AND d.number_line = NEW.destination_number_line;
    
    IF ISNULL(V_id_number_origin) OR ISNULL(V_id_number_destination) THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'One of the entered lines does not exist', MYSQL_ERRNO = 1003;
    ELSE IF V_id_number_origin = V_id_number_destination THEN
		SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'The phone lines are the same', MYSQL_ERRNO = 1004;
    ELSE
		START TRANSACTION;
			SELECT o.id, d.id INTO V_id_city_origin, V_id_city_destination
			FROM cities o, cities d 
			WHERE new.origin_number_line LIKE CONCAT(o.city_prefix, '%')
			AND new.destination_number_line LIKE CONCAT(d.city_prefix, '%')
			ORDER BY LENGTH(o.city_prefix), LENGTH(d.city_prefix) DESC LIMIT 1;  -- Cuando se crea una nueva linea se comprueba que pertezca a una ciudad existente, por lo tanto no tenemos que corroborarlo nuevamente
			
			SELECT (cost_per_minute*(new.duration/60)),(price_per_minute*(new.duration/60))
			INTO V_total_cost, V_total_price 
			FROM fares 
			WHERE origin_city_id = V_id_city_origin 
			AND destination_city_id = V_id_city_destination;
			
			IF ISNULL(V_total_cost) OR  ISNULL( V_total_price) THEN
				SIGNAL SQLSTATE '45000'
					SET MESSAGE_TEXT = 'There are still no rates for the lines entered', MYSQL_ERRNO = 1005;
			ELSE
				SET new.origin_line_id = V_id_number_origin;
				SET new.origin_city_id = V_id_city_origin;
				SET new.destination_line_id = V_id_number_destination;
				SET new.destination_city_id = V_id_city_destination;
				SET new.total_cost = V_total_cost;
				SET new.total_price = V_total_price;
			END IF;
        COMMIT;
    END IF;
END;//
DELIMITER ;
truncate calls;
/* ------------------------------ */