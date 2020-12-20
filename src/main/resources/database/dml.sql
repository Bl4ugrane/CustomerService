
INSERT INTO address (id, country, region, city, street, house, flat, created, modified) VALUES
(1,'Russia','Novosibirsk region','Novosibirsk','Sovetskaya', '64','7',now(),now());

INSERT INTO address (id, country, region, city, street, house, flat, created, modified) VALUES
(2,'Russia','Republic Altay','Gorno-Altaysk','Choros-Gurkina', '14','5',now(),now());

INSERT INTO address (id, country, region, city, street, house, flat, created, modified) VALUES
(3,'Russia','Altai region','Barnaul','Lenina', '57','11',now(),now());

INSERT INTO address (id, country, region, city, street, house, flat, created, modified) VALUES
(4,'Russia','Moscow region','Moscow','Tvesrskaya', '14','25',now(),now());

INSERT INTO customer (id, registred_address_id, actual_address_id, first_name, last_name, middle_name, sex)
VALUES (1,2,1,'Dmitriy','Ugachev','Evgenievich','male');

INSERT INTO customer (id, registred_address_id, actual_address_id, first_name, last_name, middle_name, sex)
VALUES (2,3,4,'Sergey','Antonov','Vasilyevich','male');