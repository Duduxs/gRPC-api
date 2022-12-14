CREATE TABLE IF NOT EXISTS PRODUCT (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    quantity_in_stock INTEGER NOT NULL,
    CONSTRAINT id UNIQUE (id)
);

INSERT INTO product (name, price, quantity_in_stock) VALUES ('Product A', 10.99, 10);
INSERT INTO product (name, price, quantity_in_stock) VALUES ('Product B', 10.99, 10);
