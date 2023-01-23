

create database RRHH;

use RRHH;

create table Employee(
id int auto_increment,
name varchar(20) not null,
last_name varchar(20) not null,
di varchar(20) not null,
birthday date not null,
departament varchar(20) not null,
hiring_date date not null,
salary int not null,
primary key(id)
);