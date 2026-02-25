CREATE TABLE cart(
	id VARCHAR(36) PRIMARY KEY DEFAULT (uuid()),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	ON UPDATE CURRENT_TIMESTAMP
);




CREATE TABLE product(
	id VARCHAR(36) PRIMARY KEY DEFAULT (uuid()),
	name VARCHAR(150) NOT NULL,
	description TEXT,
	price Decimal(10,2) NOT NULL,
	stock INT NOT NULL CHECK (stock>=0)
);


CREATE TABLE cart_item(
	id VARCHAR(36) PRIMARY KEY DEFAULT (uuid()),
	cart_id VARCHAR(36) NOT NULL,
	product_id VARCHAR(36) NOT NULL,
	quantity INT NOT NULL DEFAULT 1 CHECK (quantity>0),
	
	CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id)
	REFERENCES product(id)
	ON DELETE CASCADE,
	
	CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id)
	REFERENCES cart(id)
	ON DELETE CASCADE,
	
	CONSTRAINT uq_cart_product UNIQUE (cart_id,product_id)

);


