alter table _ent_attr drop constraint FK878416241A45D851;
alter table _ent_attr drop constraint FK87841624F5F404C6;
alter table _obj_attr drop constraint FKA749998ABEEEE28;
alter table _obj_attr drop constraint FKA7499981A45D851;
alter table _val_obj drop constraint FK9A53D19AABEEEE28;
alter table _val_obj drop constraint FK9A53D19AA296589;
drop table _attribute;
drop table _ent_attr;
drop table _entity;
drop table _measurement;
drop table _obj_attr;
drop table _objective;
drop table _socialvalue;
drop table _val_obj;
drop sequence hibernate_sequence;
create table _attribute (
    id int8 not null,
    name varchar(255),
    authorId int8,
    createDate date,
    dataType int4,
    description varchar(255),
    primary key (id)
);
create table _ent_attr (
    ent_id int8 not null,
    attr_id int8 not null,
    id int4 not null,
    primary key (ent_id, id)
);
create table _entity (
    id int8 not null,
    name varchar(255),
    description varchar(255),
    authorId int8,
    createDate date,
    authorType int4,
    primary key (id)
);
create table _measurement (
    id int8 not null,
    time0 date,
    time_s date,
    time_e date,
    time_c int8,
    aid int8,
    rid int8,
    measure_i int4,
    measure_d float8,
    measure_t date,
    eid int8,
    measure_l int8,
    measure_y char(1),
    measure_x varchar(255),
    primary key (id)
);
create table _obj_attr (
    obj_id int8 not null,
    attr_id int8 not null,
    id int4 not null,
    primary key (obj_id, id)
);
create table _objective (
    id int8 not null,
    name varchar(255),
    description varchar(255),
    createdate date,
    authortype varchar(255),
    authorid int8,
    samplequestion varchar(255),
    positive int4,
    primary key (id)
);
create table _socialvalue (
    id int8 not null,
    name varchar(255),
    source varchar(255),
    description varchar(255),
    primary key (id)
);
create table _val_obj (
    val_id int8 not null,
    obj_id int8 not null,
    id int4 not null,
    primary key (val_id, id)
);
alter table _ent_attr 
    add constraint FK878416241A45D851 
    foreign key (attr_id) 
    references _attribute;
alter table _ent_attr 
    add constraint FK87841624F5F404C6 
    foreign key (ent_id) 
    references _entity;
alter table _obj_attr 
    add constraint FKA749998ABEEEE28 
    foreign key (obj_id) 
    references _objective;
alter table _obj_attr 
    add constraint FKA7499981A45D851 
    foreign key (attr_id) 
    references _attribute;
alter table _val_obj 
    add constraint FK9A53D19AABEEEE28 
    foreign key (obj_id) 
    references _objective;
alter table _val_obj 
    add constraint FK9A53D19AA296589 
    foreign key (val_id) 
    references _socialvalue;
create sequence hibernate_sequence;
