--liquibase formatted sql
--changeset raquib:003

CREATE TABLE orders(
    id  VARCHAR(36) PRIMARY KEY DEFAULT (uuid()),
    user_id VARCHAR(100) NOT NULL,
    status VARCHAR(20) DEFAULT 'CREATED' NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_orders_users FOREIGN KEY (user_id)
    REFERENCES users(username)
    ON DELETE CASCADE
);

CREATE TABLE order_items(
    id VARCHAR(36) PRIMARY KEY DEFAULT(uuid()),
    order_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,

    CONSTRAINT fk_order_items_orders FOREIGN KEY (order_id)
    REFERENCES orders(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id)
    REFERENCES product(id)
    ON DELETE CASCADE
);