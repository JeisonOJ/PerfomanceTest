CREATE TABLE shop(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
name varchar(255) NOT NULL,
location varchar(255) NOT NULL
);

CREATE TABLE products(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
name_product varchar(255) NOT NULL,
price decimal(10,2) NOT NULL,
id_shop INT NOT NULL,
FOREIGN KEY (id_shop) REFERENCES shop(id) on delete cascade on update cascade
);

CREATE TABLE clients(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
name_client varchar(255) NOT NULL,
last_name varchar(255) NOT NULL,
email varchar (255) NOT NULL
);


CREATE TABLE purchase(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
created_at timestamp default current_timestamp,
quantity int NOT NULL,
id_clients int NOT NULL,
FOREIGN KEY (id_clients) REFERENCES clients(id) on delete cascade on update cascade,
id_products int NOT NULL,
FOREIGN KEY (id_products) REFERENCES products(id) on delete cascade on update cascade
);

SELECT * FROM purchase WHERE id_products = 1;

ALTER TABLE products ADD COLUMN stock INT NOT NULL;

-- Inserciones para la tabla de shop
INSERT INTO shop (name, location) VALUES ('branchos', "101");
INSERT INTO shop (name, location) VALUES ('helados mimos', "102");

-- Inserciones para la tabla de products
INSERT INTO products (name, price, stock ,id_shop) VALUES ('nike', 200,10, 1);
INSERT INTO products (name, price, stock ,id_shop) VALUES ('copas', 15,12, 2);

-- Inserciones para la tabla de clients
INSERT INTO clients (name, last_name, email) VALUES ('jeison', 'ortiz', 'jeisonortiz1516@gmail.com');
INSERT INTO clients (name, last_name, email) VALUES ('daniela', 'osorio', 'daosorio@gmail.com');

-- Inserciones para la tabla de purchase
INSERT INTO purchase (id_clients,id_products,quantity) VALUES (1, 1, 1);

select * from purchase
inner join products on purchase.id_products = products.id
inner join clients on purchase.id_clients = clients.id
where purchase.id = 1;

select * from purchase
inner join products on purchase.id_products = products.id
inner join clients on purchase.id_clients = clients.id
where purchase.id_products = 1;

select * from purchase
inner join products on purchase.id_products = products.id
inner join clients on purchase.id_clients = clients.id
where products.id = 1;

select * from purchase
inner join products on purchase.id_products = products.id
inner join clients on purchase.id_clients = clients.id;

SELECT * FROM products INNER JOIN shop ON products.id_shop = shop.id WHERE shop.id = 1;

delete from purchase where id = 1;

select * from shop;
select * from products;
select * from clients;
select * from purchase;