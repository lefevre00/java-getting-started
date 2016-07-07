CREATE TABLE users
(
	id serial NOT NULL,
	email character varying(255) NOT NULL,
	place_id integer,
	password character varying(50) NOT NULL,
	token character varying(100),
	CONSTRAINT prim_key_users PRIMARY KEY (id),
	CONSTRAINT uniq_email UNIQUE (email)
)

CREATE TABLE places
(
	id integer NOT NULL,
	email_occupant character varying(255),
	occupation_date character varying(10) NOT NULL,
	CONSTRAINT places_pkey PRIMARY KEY (place_id, occupation_date),
	CONSTRAINT user_fkey FOREIGN KEY (mail_occupant)
		REFERENCES users (email) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
)


CREATE TABLE sessions
(
	user_id integer NOT NULL,
	creation_date timestamp without time zone NOT NULL DEFAULT now(),
	cookie character varying(100) NOT NULL,
	expiration_date timestamp without time zone NOT NULL,
	CONSTRAINT pkey_session PRIMARY KEY (cookie),
	CONSTRAINT fkey_user_id FOREIGN KEY (user_id)
		REFERENCES users (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
)
