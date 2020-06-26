/* Evento periodico que se ejecutará cada 1ro de cada mes para corroborar facturas */
DELIMITER //
CREATE EVENT sch_invoice_creation
ON SCHEDULE EVERY '1' MONTH
STARTS CURRENT_TIMESTAMP
DO
BEGIN
	call load_invoices_sp();
END;//
DELIMITER ;
/* ------------------------------ */

/* Stored Procedures para carga de facturas */
DELIMITER //
CREATE PROCEDURE load_invoices_sp()
BEGIN
    DECLARE finished INT DEFAULT 0;
    DECLARE id_line INT;
    DECLARE cur_for_each_phone_line CURSOR FOR
		SELECT id FROM phone_lines;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
	OPEN cur_for_each_phone_line;
		loop_for_cur : LOOP
			FETCH cur_for_each_phone_line INTO id_line;
            IF finished = 1 THEN
				LEAVE loop_for_cur;
			ELSE
				START TRANSACTION;
					call create_invoice_sp(id_line);
				COMMIT;
			END IF;
		END LOOP loop_for_cur;
    CLOSE cur_for_each_phone_line;
END;//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE create_invoice_sp(IN in_id_line int)
BEGIN
	DECLARE id_of_call INT;
    DECLARE total_cost_call FLOAT;
    DECLARE total_price_call FLOAT;
	
	DECLARE sum_total_cost_for_invoice FLOAT DEFAULT 0;
    DECLARE sum_total_price_for_invoice FLOAT DEFAULT 0;
    DECLARE count_of_calls INT DEFAULT 0;
    DECLARE v_number_line varchar(50);
    DECLARE v_id_invoice INT;
    
	DECLARE finished INT DEFAULT 0;
	DECLARE cur_for_each_call CURSOR FOR
		SELECT
			id as "id_of_call",
            total_cost as "total_cost_call",
            total_price as "total_price_call" 
		FROM Calls 
        WHERE origin_line_id = in_id_line 
        AND invoice_id IS NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    
	SELECT number_line INTO v_number_line FROM phone_lines WHERE id = in_id_line;
    INSERT INTO invoices(number_line,invoice_date,invoice_expiry_date) 
		values (v_number_line,NOW(),DATE_ADD(NOW(), INTERVAL 15 DAY));
    SET v_id_invoice = last_insert_id();
    
    OPEN cur_for_each_call;
		loop_for_cur : LOOP
			FETCH cur_for_each_call INTO id_of_call,total_cost_call,total_price_call;
			IF finished = 1 THEN
				LEAVE loop_for_cur;
			ELSE
				SET sum_total_cost_for_invoice = (total_cost_call + sum_total_cost_for_invoice);
                SET sum_total_price_for_invoice = (total_price_call + sum_total_price_for_invoice);
                SET count_of_calls = (count_of_calls + 1);
                UPDATE calls c SET c.invoice_id = v_id_invoice WHERE c.id = id_of_call;
            END IF;
		END LOOP loop_for_cur;
	CLOSE cur_for_each_call;
    
    UPDATE 
		invoices
    SET 
		quantity_calls = count_of_calls,
		total_cost = sum_total_cost_for_invoice,
        total_price = sum_total_price_for_invoice,
        is_paid = (CASE WHEN count_of_calls = 0 THEN true ELSE false END)
	WHERE id = v_id_invoice;
    
END;//
DELIMITER ;
/* ------------------------------ */
