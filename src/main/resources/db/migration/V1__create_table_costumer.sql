CREATE TABLE costumer (
	id int not null AUTO_INCREMENT,
    name varchar(255) not null,
    email varchar(255) not null,
    primary key (id),
    unique key email(email)
);

