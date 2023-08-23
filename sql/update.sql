ALTER TABLE t_user DROP CONSTRAINT t_user_fk1;
ALTER TABLE t_user DROP COLUMN company_id;

DROP TABLE t_company;
DROP SEQUENCE s_company_id;

ALTER TABLE t_user DROP COLUMN title;
ALTER TABLE t_user DROP COLUMN fax;
ALTER TABLE t_user DROP COLUMN portrait_name;
ALTER TABLE t_user DROP COLUMN portrait;

