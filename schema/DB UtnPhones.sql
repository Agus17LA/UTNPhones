create database UtnPhones;

use utnphones;

create table provinces(
	id int auto_increment not null,
    province_name varchar(40) not null,
	constraint pk_id_province primary key (id),
    constraint unq_province_name unique (province_name)
);

create table cities(
	id int auto_increment not null,
    province_id int not null,
    city_name varchar(40),
    city_prefix int,
    constraint pk_id_city primary key (id),
    constraint fk_province_id foreign key (province_id) references provinces (id),
    constraint unq_city_name unique (city_name)
);

create table fares(
	id int auto_increment not null,
    origin_city_id int not null,
    destination_city_id int not null,
    cost_per_minute float not null,
    price_per_minute float not null,
    constraint pk_id_fare primary key (id),
    constraint fk_origin_city_id foreign key (origin_city_id) references cities(id),
    constraint fk_destination_city_id foreign key (destination_city_id) references cities(id)
);

create table users(
	id int auto_increment not null,
    city_id int not null,
    dni varchar(50) not null,
	name varchar(50) not null,
    surname varchar(50) not null,
    username varchar(50) not null,
    password varchar(50) not null,
    user_type ENUM("EMPLOYEE","CLIENT","SYS") not null,
    user_status boolean default true,
    constraint pk_id_user primary key (id),
    constraint fk_city_id foreign key (city_id) references cities(id),
    constraint unq_username unique (username),
    constraint unq_dni unique (dni)
);

create table phone_lines(
	id int auto_increment not null,
    user_id int not null,
    number_line varchar(50) not null,
    line_type ENUM("LANDLINE","MOBILE") not null,
    line_status ENUM("ACTIVE","INACTIVE","SUSPENDED") not null default "ACTIVE",
    constraint pk_id_line primary key (id),
    constraint fk_user_id foreign key (user_id) references users(id),
    constraint unq_number_line unique (number_line)
);

create table invoices(
	id int auto_increment not null,
    number_line varchar(50) not null,
    quantity_calls int,
    invoice_date timestamp,
    invoice_expiry_date datetime,
    total_cost float,
    total_price float,
    is_paid boolean,
    constraint pk_id_invoice primary key (id),
    constraint fk_number_line foreign key (number_line) references phone_lines(number_line)
);

create table calls(
	id int auto_increment not null,
    origin_line_id int,
    origin_number_line varchar(50) not null,
    origin_city_id int,
    destination_line_id int,
    destination_number_line varchar(50) not null,
    destination_city_id int,
    invoice_id int,
    duration int not null,
    date_time timestamp not null,
    total_cost float,
    total_price float,
    constraint pk_id_call primary key (id),
    constraint fk_origin_line_id foreign key (origin_line_id) references phone_lines(id),
    constraint fk_destination_line_id foreign key (destination_line_id) references phone_lines(id),
    constraint fk_call_origin_city_id foreign key (origin_city_id) references cities(id),
    constraint fk_call_destination_city_id foreign key (destination_city_id) references cities(id)
);

