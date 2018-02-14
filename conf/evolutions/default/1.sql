# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  id                            bigserial not null,
  book                          varchar(255),
  subject_id                    bigint,
  constraint uq_book_subject_id unique (subject_id),
  constraint pk_book primary key (id)
);

create table subject (
  id                            bigserial not null,
  subject                       varchar(255),
  book_id                       bigint,
  constraint uq_subject_book_id unique (book_id),
  constraint pk_subject primary key (id)
);

create table users (
  id                            bigserial not null,
  username                      varchar(255),
  firstname                     varchar(255),
  lastname                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  enabled                       boolean default false not null,
  activationtoken               varchar(255),
  constraint pk_users primary key (id)
);

create table users_user_role (
  users_id                      bigint not null,
  user_role_id                  bigint not null,
  constraint pk_users_user_role primary key (users_id,user_role_id)
);

create table user_role (
  id                            bigserial not null,
  role                          varchar(255),
  constraint pk_user_role primary key (id)
);

alter table book add constraint fk_book_subject_id foreign key (subject_id) references subject (id) on delete restrict on update restrict;

alter table subject add constraint fk_subject_book_id foreign key (book_id) references book (id) on delete restrict on update restrict;

alter table users_user_role add constraint fk_users_user_role_users foreign key (users_id) references users (id) on delete restrict on update restrict;
create index ix_users_user_role_users on users_user_role (users_id);

alter table users_user_role add constraint fk_users_user_role_user_role foreign key (user_role_id) references user_role (id) on delete restrict on update restrict;
create index ix_users_user_role_user_role on users_user_role (user_role_id);


# --- !Downs

alter table if exists book drop constraint if exists fk_book_subject_id;

alter table if exists subject drop constraint if exists fk_subject_book_id;

alter table if exists users_user_role drop constraint if exists fk_users_user_role_users;
drop index if exists ix_users_user_role_users;

alter table if exists users_user_role drop constraint if exists fk_users_user_role_user_role;
drop index if exists ix_users_user_role_user_role;

drop table if exists book cascade;

drop table if exists subject cascade;

drop table if exists users cascade;

drop table if exists users_user_role cascade;

drop table if exists user_role cascade;

