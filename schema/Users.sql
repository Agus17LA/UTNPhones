/* Trigger para suspender/activar lineas de cliente que se acaba de dar de baja/alta */
DELIMITER //
CREATE TRIGGER set_status_of_lines
BEFORE UPDATE ON Users
FOR EACH ROW
BEGIN
	IF new.user_status = false THEN
		UPDATE phone_lines SET line_status = 'INACTIVE' WHERE user_id = new.id;
    ELSE
		UPDATE phone_lines SET line_status = 'ACTIVE' WHERE user_id = new.id;
	END IF;
END;//
DELIMITER ;
/* ------------------------------ */

/* Stored Procedure carga de nuevo cliente (se le auto-genera un numero de telefono) */
DELIMITER // -- PROCEDURE NO IMPLEMENTADO - No se encontró la forma de concatenar un numero al prefijo de forma que sea imposible que se rompa
			 -- ( EJ. prefijo: 220 ---> Debo generar 7 digitos sin que se rompa el prefijo, es decir, 7 digitos pero que el primero no sea un numero que transforme el prefijo 220 en otro prefijo y por lo tanto indique a otra ciudad )
CREATE PROCEDURE charge_new_client_sp(IN i_dni varchar(50), IN i_name varchar(50), IN i_surname varchar(50), IN i_username varchar(50), IN i_password varchar(50), IN i_city_name varchar(40), IN i_line_type varchar(8))
BEGIN
	DECLARE v_last_number_line varchar(50);
    DECLARE v_city_id int;
    DECLARE v_aux int;
    SET v_last_number_line = (SELECT number_line FROM phone_lines ORDER BY id DESC LIMIT 1);
    SET v_city_id = (SELECT id FROM cities WHERE city_name = i_city_name);
    SET v_city_prefix = (SELECT city_prefix FROM cities WHERE city_name = i_city_name);
	IF NOT isnull(city_id) THEN
        INSERT INTO users (city_id,dni,name,surname,username,password,user_type) values (v_city_id,i_dni,i_name,i_surname,i_username,i_password,"CLIENT");
		IF NOT isnull(v_last_number_line) THEN
			INSERT INTO phone_lines(user_id,number_line,line_type,line_status) values (last_insert_id(),RPAD(v_city_prefix, 10, "0"), i_line_type);
		ELSE
			SET v_aux = (10-length(v_city_prefix));
			INSERT INTO phone_lines(user_id,number_line,line_type,line_status) values (last_insert_id(),RPAD(v_city_prefix, 10, "0"), i_line_type); 
		END IF;
	ELSE
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'The name of the entered city has not been found', MYSQL_ERRNO = 1006;
	END IF;
END;//
DELIMITER ;
/* ------------------------------ */ 