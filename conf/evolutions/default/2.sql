# --- !Ups
alter table patient_information add column additional_information clob;
alter table bill add column payment_type varchar(255) not null default 'OTHER';
alter table bill add constraint ck_bill_payment_type check (payment_type in ('CASH', 'CHECK', 'CREDIT_CARD', 'OTHER'));
alter table bill drop column consultation;

# --- !Downs
alter table bill drop column payment_type;
alter table patient_information drop column additional_information
alter table bill add column consultation bigint;