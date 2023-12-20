
drop view v_preview_file;

alter table t_file alter column file_name type varchar(255);
alter table t_file alter column display_name type varchar(255);

create view v_preview_file as
SELECT t_file.id,
       t_file.file_name,
       t_file.content_type,
       t_image.preview_bytes
FROM t_file,
     t_image
WHERE t_file.id = t_image.id;

alter table t_configuration drop column title;
alter table t_configuration drop column salt;
alter table t_configuration drop column locale;
alter table t_configuration drop column show_date_time;
alter table t_configuration drop column use_read_rights;
alter table t_configuration drop column use_read_group;
alter table t_configuration drop column use_editor_group;