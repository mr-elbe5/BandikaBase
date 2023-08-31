ALTER TABLE t_user DROP CONSTRAINT t_user_fk1;
ALTER TABLE t_user DROP COLUMN company_id;

DROP TABLE t_company;
DROP SEQUENCE s_company_id;

ALTER TABLE t_user DROP COLUMN title;
ALTER TABLE t_user DROP COLUMN fax;
ALTER TABLE t_user DROP COLUMN portrait_name;
ALTER TABLE t_user DROP COLUMN portrait;

ALTER TABLE t_content add open_access BOOLEAN NOT NULL DEFAULT true;
UPDATE t_content set open_access = false where access_type <> 'OPEN';

ALTER TABLE t_content add reader_group_id INTEGER NULL;
ALTER TABLE t_content add editor_group_id INTEGER NULL;
ALTER TABLE t_content add  CONSTRAINT t_content_fk4 FOREIGN KEY (reader_group_id) REFERENCES t_group (id) ON DELETE SET DEFAULT;
ALTER TABLE t_content add  CONSTRAINT t_content_fk5 FOREIGN KEY (editor_group_id) REFERENCES t_group (id) ON DELETE SET DEFAULT;

UPDATE t_content t1 set reader_group_id = (select group_id from t_content_right t2 where t1.id = t2.content_id and t2.value = 'READ')
where exists (select group_id from t_content_right t2 where t1.id = t2.content_id and t2.value = 'READ');
UPDATE t_content t1 set editor_group_id = (select group_id from t_content_right t2 where t1.id = t2.content_id and t2.value = 'EDIT')
where exists (select group_id from t_content_right t2 where t1.id = t2.content_id and t2.value = 'EDIT');

drop table t_content_right;
ALTER TABLE t_content DROP COLUMN access_type;

delete from t_system_right where name = 'CONTENTAPPROVE';

