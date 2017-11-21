create table jhi_user (
    id bigint not null AUTO_INCREMENT Primary Key,
    login varchar(50) not null,
    password_hash varchar(60),
    first_name varchar(50),
    last_name varchar(50),
    email varchar(100) unique not null,
    image_url varchar(256) null,
    activated boolean not null default 0,
    lang_key varchar(5),
    activation_key varchar(20),
    reset_key varchar(20),
    created_by varchar(50) not null,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
    reset_date TIMESTAMP,
    last_modified_by varchar(50),
    last_modified_date TIMESTAMP
);
CREATE unique INDEX idx_user_login ON jhi_user (login(50));
CREATE unique INDEX idx_user_email ON jhi_user (email(100));
create table jhi_authority (
    name varchar(50) not null Primary Key
);
create table jhi_user_authority (
	user_id bigint not null,
    authority_name varchar(50) not null,
	PRIMARY KEY (user_id, authority_name) 
);
create table jhi_persistent_token (
    series varchar(50) not null PRIMARY key,
    user_id bigint,
    token_value varchar(20) not null,
	token_date DATE,
	ip_address varchar(39),
    user_agent varchar(255)
);
ALTER TABLE jhi_user_authority ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES jhi_authority(name);
ALTER TABLE jhi_user_authority ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id);
ALTER TABLE jhi_persistent_token ADD CONSTRAINT fk_user_persistent_token FOREIGN KEY (user_id) REFERENCES jhi_user(id);
create table jhi_persistent_audit_event (
    event_id bigint AUTO_INCREMENT not null PRIMARY key,
    principal varchar(50) not null,
	event_date TIMESTAMP,
	event_type varchar(255)
);
create table jhi_persistent_audit_evt_data (
    event_id bigint not null,
    name varchar(150) not null,
	value varchar(255),
	primary key (event_id, name)
);
CREATE INDEX idx_persistent_audit_event ON jhi_persistent_audit_event (principal, event_date);
CREATE INDEX idx_persistent_audit_evt_data ON jhi_persistent_audit_evt_data (event_id);
ALTER TABLE jhi_persistent_audit_evt_data ADD CONSTRAINT fk_evt_pers_audit_evt_data FOREIGN KEY (event_id) REFERENCES jhi_persistent_audit_event(event_id);	
