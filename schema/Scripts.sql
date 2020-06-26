use utnphones;

select * from provinces;
select * from cities;
select * from users;
select * from fares;
select * from phone_lines;
select * from calls;
select * from invoices;

update users set User_Type = 'EMPLOYEE' where id=1;

INSERT INTO calls (origin_number_line, destination_number_line, duration, date_time) values ("2262677713","2262111111",160,"2020-11-03T03:21:30");
INSERT INTO phone_lines (user_id,number_line,line_type) values (2,"2231234567","MOBILE");

/* Generacion automatica de fares */
truncate fares;
INSERT INTO fares (origin_city_id, destination_city_id, cost_per_minute, price_per_minute) SELECT o.id, d.id, 0, RAND() FROM cities o, cities d;
update fares set cost_per_minute = price_per_minute *.85;
/* ------------------------------ */











