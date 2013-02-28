# Users schema
 
# --- !Ups
 
CREATE TABLE "User" (
	id SERIAL PRIMARY KEY, 
	name VARCHAR(50) NOT NULL, 
	email VARCHAR(100), 
	phone VARCHAR(20), 
	street VARCHAR(200),
	city VARCHAR(200),
	state VARCHAR(30),
	zip VARCHAR(12)
);

# --- !Downs

DROP TABLE "User";