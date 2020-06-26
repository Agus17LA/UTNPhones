/* Trigger que comprueba que el numero de telefono a agregar corresponda a una ciudad existente y que el numero tenga 10 digitos*/
DELIMITER //
DROP TRIGGER IF EXISTS verify_new_number;
CREATE TRIGGER verify_new_number
BEFORE INSERT ON phone_lines
FOR EACH ROW
BEGIN
	IF NOT exists(SELECT * FROM cities WHERE new.number_line LIKE CONCAT(city_prefix, '%') ORDER BY LENGTH(city_prefix) DESC LIMIT 1) THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'The number entered does not correspond to any existing city', MYSQL_ERRNO = 1001;
    END IF;
    IF (SELECT length(new.number_line)) != 10 THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'The number entered dont have 10 digits', MYSQL_ERRNO = 1002;
    END IF;
END;//
DELIMITER ;
/* ------------------------------ */