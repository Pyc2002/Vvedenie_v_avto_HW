CREATE TABLE IF NOT EXISTS products (
product_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
product_name VARCHAR(80) NOT NULL,
product_quantity integer NOT NULL,
product_price FLOAT NOT NULL,
manufacturer_id integer NOT NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers (manufacturer_id));


INSERT INTO products(product_name, product_quantity, product_price, manufacturer_id) VALUES
('телевизор', 50, 50000, 1),
('телевизор', 20, 45000, 2),
('холодильник', 25, 70000, 1),
('пылесос', 30, 15000, 1),
('пылесос', 35, 10000, 2)
