use slidenetdb;

create table role
(
    id int auto_increment,
    name varchar(45) collate utf8_bin not null,
    constraint id_UNIQUE
        unique (id),
    constraint name_UNIQUE
        unique (name)
);

alter table role
    add primary key (id);

create table account
(
    id int auto_increment
        primary key,
    role_id int not null,
    login varchar(20) not null,
    password varchar(50) not null,
    fname varchar(30) not null,
    lname varchar(30) not null,
    sname varchar(30) null,
    address varchar(150) null,
    phone_number varchar(45) null,
    ip_address varchar(20) null,
    balance int null,
    account_status tinyint not null,
    constraint fk_role_id
        foreign key (role_id) references role (id)
);

create index fk_role_id_idx
    on account (role_id);

create table service
(
    id int auto_increment,
    name varchar(45) null,
    constraint id_UNIQUE
        unique (id),
    constraint name_UNIQUE
        unique (name)
);

alter table service
    add primary key (id);

create table tariff
(
    id int auto_increment
        primary key,
    service_id int not null,
    name varchar(45) not null,
    description varchar(512) not null,
    price int null,
    constraint fk_service_tariffs_service1
        foreign key (service_id) references service (id)
            on update cascade on delete cascade
);

create table account_service_tariff
(
    account_id int not null,
    service_id int not null,
    tariff_id int not null,
    activation_date datetime null,
    enable_status tinyint null,
    next_payment_day date null,
    payed tinyint null,
    payment_amount int null,
    constraint fk_account_services_tariffs_account1
        foreign key (account_id) references account (id)
            on delete cascade,
    constraint fk_account_services_tariffs_service1
        foreign key (service_id) references service (id)
            on update cascade on delete cascade,
    constraint fk_account_services_tariffs_service_tariff1
        foreign key (tariff_id) references tariff (id)
            on update cascade on delete cascade
);

create index fk_account_services_tariffs_account1_idx
    on account_service_tariff (account_id);

create index fk_account_services_tariffs_service1_idx
    on account_service_tariff (service_id);

create index fk_account_services_tariffs_service_tariff1_idx
    on account_service_tariff (tariff_id);

create index fk_service_tariffs_service1_idx
    on tariff (service_id);

Insert into role (name) values ('admin');
insert into role (name) values ('user');

Insert into account (role_id, login, password, fname, lname,sname,address, phone_number, ip_address, balance, account_status)
values (1, 854652030, '474e1e45c52dccc30a340e4b4dc12f56', 'Ben ', 'Nicholson ', 'Chapman ', 'old Street, 15 Ave. Right Side', '+380910203040', '145.171.196.255', 950, true);

Insert into account (role_id, login, password, fname, lname,sname,address, phone_number, ip_address, balance, account_status)
values (2, 123456789, '3cca95088c094b795f861067e4d31bf7', 'Энтони ', 'Эдвард', 'Старк ', '10660 Малибу Поинт 90210', '+000000000000', '255.255.255.255', 1000, true);

Insert into account (role_id, login, password, fname, lname,sname,address, phone_number, ip_address, balance, account_status)
values (2, 77999990, '3e9e5afe084edc6cccd2bd13b4a1a82c', 'Old', 'Man', 'Bob', 'oldest Street, 75 Ave. Ever Side', '+380123456789', '123.123.123.123', 100, false);

INSERT INTO service (name) VALUES ('Internet');
INSERT INTO service (name) VALUES ('Landline');
INSERT INTO service (name) VALUES ('Cable-TV');
INSERT INTO service (name) VALUES ('IP-TV');
INSERT INTO service (name) VALUES ('Additional');

INSERT INTO tariff (service_id, name, description, price) VALUES (1, 'Leader 100', 'Up to 100 Mbps', 180);
INSERT INTO tariff (service_id, name, description, price) VALUES (1, 'Turbo 300', 'Up to 300 Mbps', 225);
INSERT INTO tariff (service_id, name, description, price) VALUES (1, 'Lux 1G', 'Up to 1 Gbps', 270);

INSERT INTO tariff (service_id, name, description, price) VALUES (2, 'Call Ever', '300 min', 40);
INSERT INTO tariff (service_id, name, description, price) VALUES (2, 'Next-Line', '600', 80);
INSERT INTO tariff (service_id, name, description, price) VALUES (2, 'Slide-Line', '1200', 100);

INSERT INTO tariff (service_id, name, description, price) VALUES (3, 'Quick-TV', '25 Channels', 125);
INSERT INTO tariff (service_id, name, description, price) VALUES (3, 'Medium-TV', '70 Channels', 180);
INSERT INTO tariff (service_id, name, description, price) VALUES (3, 'Mega-TV', '100 Channels', 200);

INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'Basic', '22 TV-Programs', 125);
INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'Movie-Hit', '34 TV-Programs', 30);
INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'Sport', '40 TV-Programs', 25);
INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'HD', '50 TV-Programs', 25);
INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'Adult*', '55 TV-Programs', 20);

INSERT INTO tariff (service_id, name, description, price) VALUES (5, 'Monthly maintenance', 'Equipment integrity check.', 120);
INSERT INTO tariff (service_id, name, description, price) VALUES (5, 'Maintence+', 'Equipment check + replacement.', 80);
INSERT INTO tariff (service_id, name, description, price) VALUES (5, 'Personal master', 'Technical support 24/7', 100);

