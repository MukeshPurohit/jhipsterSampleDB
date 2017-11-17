create table bank_account (
    id bigint not null PRIMARY key AUTO_INCREMENT,
    name varchar(255) not null,
    balance decimal(10,2) not null,
	user_id bigint
);