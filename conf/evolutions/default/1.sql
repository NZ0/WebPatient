# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table antecedent (
  id                        bigint not null,
  constraint pk_antecedent primary key (id))
;

create table bill (
  id                        bigint auto_increment not null,
  hash                      varchar(255),
  price                     float,
  date                      timestamp,
  name                      varchar(255),
  first_name                varchar(255),
  detail                    clob,
  address                   varchar(255),
  town                      varchar(255),
  practitioner_name         varchar(255),
  practitioner_first_name   varchar(255),
  phone                     varchar(255),
  header                    varchar(255),
  footer                    varchar(255),
  zip_code                  varchar(255),
  patient                   bigint,
  consultation              bigint,
  constraint pk_bill primary key (id))
;

create table consultation (
  id                        bigint auto_increment not null,
  date                      timestamp not null,
  title                     varchar(255) not null,
  important                 boolean,
  size                      integer,
  weight                    integer,
  profile                   varchar(8),
  main_reason               clob,
  tests                     clob,
  treatment                 clob,
  sketch                    clob,
  patient_id                bigint,
  bill_id                   bigint,
  constraint ck_consultation_profile check (profile in ('CHILD','PREGNANT','ADULT','BABY')),
  constraint uq_consultation_bill_id unique (bill_id),
  constraint pk_consultation primary key (id))
;

create table patient (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  first_name                varchar(255),
  sex                       varchar(6),
  birth_date                timestamp,
  address                   varchar(255),
  email                     varchar(255),
  phone                     varchar(255),
  dead                      boolean,
  information_id            bigint,
  antecedents_id            bigint,
  constraint ck_patient_sex check (sex in ('FEMALE','MALE')),
  constraint uq_patient_information_id unique (information_id),
  constraint uq_patient_antecedents_id unique (antecedents_id),
  constraint pk_patient primary key (id))
;

create table patient_information (
  id                        bigint auto_increment not null,
  marital_status            varchar(9),
  children                  integer,
  profession                varchar(255),
  hobbits                   varchar(255),
  doctor                    varchar(255),
  mutuel                    boolean,
  health_insurance_number   varchar(255),
  hand                      varchar(5),
  misc                      clob,
  constraint ck_patient_information_marital_status check (marital_status in ('SINGLE','MARRIED','IN_COUPLE','DIVORCED','WIDOW','PACS')),
  constraint ck_patient_information_hand check (hand in ('LEFT','RIGHT')),
  constraint pk_patient_information primary key (id))
;

create table settings (
  id                        bigint auto_increment not null,
  name                      varchar(22),
  value                     varchar(255),
  constraint ck_settings_name check (name in ('PRACTITIONER_NAME','PRACTITIONER_FIRSTNAME','PRACTITIONER_ADDRESS','PRACTITIONER_TOWN','PRACTITIONER_PHONE','PRACTITIONER_EMAIL','BILL_HEADER','BILL_FOOTER','BILL_DEFAULT_PRICE','APPOINTMENT_DURATION','PRACTITIONER_ZIP')),
  constraint uq_settings_name unique (name),
  constraint pk_settings primary key (id))
;

create sequence antecedent_seq;

alter table consultation add constraint fk_consultation_patient_1 foreign key (patient_id) references patient (id) on delete restrict on update restrict;
create index ix_consultation_patient_1 on consultation (patient_id);
alter table consultation add constraint fk_consultation_bill_2 foreign key (bill_id) references bill (id) on delete restrict on update restrict;
create index ix_consultation_bill_2 on consultation (bill_id);
alter table patient add constraint fk_patient_information_3 foreign key (information_id) references patient_information (id) on delete restrict on update restrict;
create index ix_patient_information_3 on patient (information_id);
alter table patient add constraint fk_patient_antecedents_4 foreign key (antecedents_id) references antecedent (id) on delete restrict on update restrict;
create index ix_patient_antecedents_4 on patient (antecedents_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists antecedent;

drop table if exists bill;

drop table if exists consultation;

drop table if exists patient;

drop table if exists patient_information;

drop table if exists settings;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists antecedent_seq;

