-- Table: shop.products
-- SEQUENCE: shop.table_name_id_seq

-- DROP SEQUENCE shop.table_name_id_seq;

CREATE SEQUENCE shop.table_name_id_seq;

ALTER SEQUENCE shop.table_name_id_seq
    OWNER TO postgres;
-- DROP TABLE shop.products;

CREATE TABLE shop.products
(
    id integer NOT NULL DEFAULT nextval('shop.table_name_id_seq'::regclass),
    picturepath character varying(512) COLLATE pg_catalog."default",
    name character varying(64) COLLATE pg_catalog."default" NOT NULL,
    price double precision,
    addeddate timestamp without time zone,
    CONSTRAINT table_name_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE shop.products
    OWNER to postgres;