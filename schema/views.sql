use UtnPhones;

CREATE VIEW v_calls_of_user AS
	SELECT
		concat(uO.name,' ',uO.surname) as "FullNameUserOrigin",
        uO.dni as "DniUserOrigin",
        concat(uD.name,' ',uD.surname) as "FullNameUserDest",
        uD.dni as "DniUserDest",
        c.origin_number_line as "OriginNumberLine",
        (SELECT city_name FROM cities WHERE id = c.origin_city_id) as "OriginCall",
        c.destination_number_line as "DestinationNumberLine",
		(SELECT city_name FROM cities WHERE id = c.destination_city_id) as "DestinationCall",
        c.invoice_id as "Invoice",
        c.duration as "Duration",
        c.date_time as "CallDate",
        c.total_price as "TotalPrice"
	FROM
		Calls c
	INNER JOIN
		phone_lines pl
	ON
		c.origin_line_id = pl.id
	INNER JOIN
		phone_lines pl2
	ON
		c.destination_line_id = pl2.id
	INNER JOIN
		Users uO
	ON
		pl.user_id = uO.id
	INNER JOIN
		Users uD
	ON
		pl2.user_id = uD.id
	INNER JOIN
		cities cO
	ON
		uO.city_id = cO.id
	INNER JOIN
		cities cD
	ON
		uD.city_id = cD.id;



