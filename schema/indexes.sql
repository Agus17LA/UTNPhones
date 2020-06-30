use utnphones;

CREATE INDEX index_of_dates ON Calls (date_time) USING BTREE;
CREATE INDEX index_of_origin_number ON Calls (origin_number_line) USING BTREE;
EXPLAIN SELECT 
	origin_number_line as 'OriginNumberLine',
    origin_city_id as 'OriginCall',
    destination_number_line as 'DestinationNumberLine',
    destination_city_id as 'DestinationCall',
    total_price as 'TotalPrice',
    date_time as 'CallDate', 
    duration as 'Duration' 
FROM 
	Calls 
WHERE (date_time BETWEEN "2020-01-01T00:00:00" AND "2020-12-12T00:20:00") AND origin_number_line = "2262677713";
 
CREATE INDEX index_of_dates_invoices ON invoices (invoice_date) USING BTREE;
CREATE INDEX index_of_number_line_invoice ON invoices (number_line) USING BTREE;
EXPLAIN SELECT * FROM 
	invoices i inner join phone_lines pl 
    on i.number_line = pl.number_line 
    WHERE (invoice_date BETWEEN "2020-01-01T00:00:00" AND "2020-01-12T00:20:00") AND pl.id = 1;

CREATE INDEX index_of_user_by_name_surname ON users (name,surname);
EXPLAIN SELECT * FROM users WHERE name = "agustin" and surname = "lopez";

CREATE INDEX index_of_user_type ON users (user_type);
EXPLAIN SELECT * FROM users WHERE dni = "41923121" and user_type = "CLIENT";



