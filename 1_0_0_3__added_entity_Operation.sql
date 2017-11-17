create table operation (
	id bigint not null AUTO_INCREMENT primary key,
    jhi_date timestamp,
	description varchar(255) not null,
	amount decimal(10,2) not null,
	bank_account_id bigint not null
);

create table operation_label (
	labels_id bigint not null,
    operations_id bigint not null,
	PRIMARY KEY (operations_id, labels_id) 
);