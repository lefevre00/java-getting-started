
CREATE TABLE users
(
  id serial NOT NULL,
  email character varying(50) NOT NULL,
  place integer,
  password character varying(50) NOT NULL,
  token character varying(100),
  CONSTRAINT prim_key_users PRIMARY KEY (id)
)