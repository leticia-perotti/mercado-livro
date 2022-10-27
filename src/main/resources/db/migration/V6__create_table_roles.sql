create table costumer_roles(
costumer_id int not null,
roles varchar(50) not null,
FOREIGN KEY (costumer_id) references costumer(id)
)