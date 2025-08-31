CREATE SEQUENCE repository_id_seq START WITH 1 INCREMENT BY 1 CACHE 1;
ALTER TABLE repository ALTER COLUMN id SET DEFAULT nextval('repository_id_seq');

CREATE SEQUENCE run_item_id_seq START WITH 1 INCREMENT BY 1 CACHE 1;
ALTER TABLE run_item ALTER COLUMN id SET DEFAULT nextval('run_item_id_seq');