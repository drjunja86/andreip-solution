CREATE TABLE product
(
 productId varchar(36) NOT NULL,
 productName varchar(100) NOT NULL,
 stock integer NOT NULL ,
 price numeric(6,2) NOT NULL,
 PRIMARY KEY (productId)
);

CREATE TABLE orders
(
 id bigserial PRIMARY KEY,
 purchaseDate timestamp with time zone NOT NULL,
 total numeric(6,2) NOT NULL
);

CREATE TABLE lines
(
 id bigserial PRIMARY KEY,
 productId varchar(36) REFERENCES product(productId),
 orderId bigint REFERENCES orders(id),
 amount integer NOT NULL,
 subtotal numeric(6,2) NOT NULL
);