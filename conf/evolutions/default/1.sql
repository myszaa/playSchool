# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

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


# --- !Downs

drop table if exists users cascade;

