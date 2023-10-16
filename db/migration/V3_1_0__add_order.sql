CREATE TABLE orders
(order_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 customer_id INT NOT NULL,
 product_id INT NOT NULL,
 quantity INT NOT NULL,
     FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
     FOREIGN KEY (product_id) REFERENCES products (customer_id));

INSERT INTO orders(customer_id, product_id, quantity) VALUES
(1, 2, 5),
(1, 1, 10),
(2, 4, 15);

