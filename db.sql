create table users
(
  id serial not null
  constraint users_pkey
  primary key,
  username varchar(30)
  constraint users_username_key
  unique,
  password varchar(150),
  admin boolean default false,
  name varchar(150) not null,
  paternalname varchar(150) not null,
  maternalname varchar(150) not null,
  email varchar(150) not null
);

INSERT INTO users(username, password,admin,name,paternalName,maternalName,email) VALUES ('ceo','123',true,'Mark Anthony','Arreguin','Gonzalez','ceo@tienda.com');

create table customers
(
  id serial not null
  constraint customers_pkey
  primary key,
  name varchar(150) not null,
  phone varchar(150) not null,
  email varchar(150) not null,
  latlong point
)
;

create table products
(
  id serial not null
  constraint products_pkey
  primary key,
  name varchar(150) not null,
  description varchar(150) not null,
  image varchar(100),
  stock integer not null,
  available integer
)
;

create table productprices
(
  productid integer
  constraint productprices_productid_fkey
  references products,
  date timestamp,
  price numeric
)
;

create table routes
(
  idemployee integer not null
  constraint routes_idemployee_fkey
  references users,
  idroute integer not null,
  idcustomer integer not null
  constraint routes_idcustomer_fkey
  references customers,
  constraint routes_pkey
  primary key (idemployee, idcustomer, idroute)
)
;

create table orders
(
  id serial not null
  constraint orders_pkey
  primary key,
  customerid integer
  constraint orders_customerid_fkey
  references customers,
  status boolean default false,
  orderdate date,
  employeeid integer
  constraint orders_employeeid_fkey
  references users,
  completeddate date
)
;

create table customer_order
(
  orderid integer not null
  constraint customer_order_orderid_fkey
  references orders,
  productid integer not null
  constraint customer_order_productid_fkey
  references products,
  quantity integer not null,
  constraint customer_order_pkey
  primary key (orderid, productid)
)
;

create table tokens
(
  id serial not null
  constraint tokens_pkey
  primary key,
  userid integer
  constraint tokens_userid_fkey
  references users,
  token text not null
)
;

create table firetokens
(
  id serial not null
  constraint firetoken_pkey
  primary key,
  token text
)
;

create function updateavaliable() returns trigger
  language plpgsql
as $$
BEGIN
UPDATE products SET available = available - NEW.quantity
WHERE products.id = New.productid;
return NEW;
END;
$$
;

create trigger updateavaliable
  after insert
  on customer_order
  for each row
  execute procedure updateavaliable()
;

create function restockavaliable() returns trigger
  language plpgsql
as $$
BEGIN

UPDATE products SET available = available + OLD.quantity
WHERE products.id = OLD.productid;
return NEW;
END;
$$
;

create trigger restockavaliable
  after delete
  on customer_order
  for each row
  execute procedure restockavaliable()
;

create function updateavaliable2() returns trigger
  language plpgsql
as $$
BEGIN
IF NEW.quantity > OLD.quantity then
UPDATE products SET available = available + (NEW.quantity - OLD.quantity)
WHERE products.id = OLD.productid;
END IF;
IF NEW.quantity < OLD.quantity then
UPDATE products SET available = available + (OLD.quantity - NEW.quantity )
WHERE products.id = OLD.productid;
END IF;
return NEW;
END;
$$
;

create trigger updateavaliable2
  after update
  on customer_order
  for each row
  execute procedure updateavaliable2()
;

