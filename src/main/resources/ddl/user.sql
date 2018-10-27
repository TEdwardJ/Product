-- Table: shop.users

-- DROP TABLE shop.users;

CREATE TABLE shop.users
(
    "user" character varying(32) COLLATE pg_catalog."default" NOT NULL,
    password character varying(32) COLLATE pg_catalog."default",
    role character varying(32) COLLATE pg_catalog."default" NOT NULL,
    sole character varying(32) COLLATE pg_catalog."default" NOT NULL,
    hashedpassword character varying(128) COLLATE pg_catalog."default",
    CONSTRAINT "USERS_PK" PRIMARY KEY ("user"),
    CONSTRAINT users_user_pk UNIQUE ("user")

)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE shop.users
    OWNER to postgres;