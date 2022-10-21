create table purchase(
    id int auto_increment primary key,
    costumer_id int not null,
    nfe varchar(255),
    price decimal(15,2) not null,
    created_at DATETIME not null,
    FOREIGN KEY (costumer_id) references costumer(id)
);
create table purchase_book(
    purchase_id int not null,
    book_id int not null,
    FOREIGN KEY (purchase_id) references purchase(id),
    FOREIGN KEY (book_id) references book(id),

    PRIMARY key (purchase_id, book_id)
);