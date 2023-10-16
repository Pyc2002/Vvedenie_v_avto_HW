CREATE TABLE customers
(customer_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 name VARCHAR(30) NOT NULL,
 phone_number VARCHAR(20) NOT NULL);


INSERT INTO customers(name, phone_number) VALUES
('Техномир', '+ 7 927 572 3771'),
('М.Видео', '+ 7 927 568 8452');
