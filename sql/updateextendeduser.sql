alter table t_user add type VARCHAR(60);
update t_user set type='de.elbe5.user.ExtendedUserData';
alter table t_user alter column type set NOT NULL;

alter table t_user add name VARCHAR(255);
update t_user set name = last_name where first_name = '';
update t_user set name = first_name || ' ' || last_name where first_name <> '';

CREATE TABLE IF NOT EXISTS t_extended_user
(
    id                 INTEGER      NOT NULL,
    first_name         VARCHAR(100) NOT NULL DEFAULT '',
    street             VARCHAR(100) NOT NULL DEFAULT '',
    zipCode            VARCHAR(16)  NOT NULL DEFAULT '',
    city               VARCHAR(50)  NOT NULL DEFAULT '',
    country            VARCHAR(50)  NOT NULL DEFAULT '',
    phone              VARCHAR(50)  NOT NULL DEFAULT '',
    mobile             VARCHAR(50)  NOT NULL DEFAULT '',
    notes              VARCHAR(500) NOT NULL DEFAULT '',
    CONSTRAINT t_extended_user_pk PRIMARY KEY (id),
    CONSTRAINT t_extended_user_fk1 FOREIGN KEY (id) REFERENCES t_user(id) ON DELETE CASCADE
);

insert into t_extended_user (id, first_name, street, zipCode, city, country, phone, mobile, notes)
select id, first_name, street, zipCode, city, country, phone, mobile, notes from t_user;

alter table t_user drop column first_name;
alter table t_user drop column last_name;
alter table t_user drop column street;
alter table t_user drop column zipcode;
alter table t_user drop column city;
alter table t_user drop column country;
alter table t_user drop column phone;
alter table t_user drop column mobile;
alter table t_user drop column notes;

drop view v_preview_file;
alter table t_file alter column file_name type VARCHAR(255);
create view v_preview_file as SELECT t_file.id,
                                     t_file.file_name,
                                     t_file.content_type,
                                     t_image.preview_bytes
                              FROM t_file,
                                   t_image
                              WHERE t_file.id = t_image.id;
alter table t_file alter column display_name type VARCHAR(255);