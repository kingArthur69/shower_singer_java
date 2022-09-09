drop table if exists roles;

drop table if exists user_files;

drop table if exists user_roles;

drop table if exists users;

create table roles
(
    role_id char(36) not null,
    name    varchar(255),
    primary key (role_id)
) engine = InnoDB;


create table user_files
(
    file_id   char(36) not null,
    file_name varchar(255),
    file_type varchar(255),
    save_dir  varchar(255),
    user_id   char(36),
    primary key (file_id)
) engine = InnoDB;


create table user_roles
(
    user_id char(36) not null,
    role_id char(36) not null
) engine = InnoDB;


create table users
(
    user_id       char(36) not null,
    enabled       bit,
    password      varchar(255),
    token_expired bit,
    user_name     varchar(255),
    primary key (user_id)
) engine = InnoDB;


alter table roles
    add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);


alter table users
    add constraint UK_k8d0f2n7n88w1a16yhua64onx unique (user_name);


alter table user_roles
    add constraint FKh8ciramu9cc9q3qcqiv4ue8a6
        foreign key (role_id)
            references roles (role_id);


alter table user_roles
    add constraint FKhfh9dx7w3ubf1co1vdev94g3f
        foreign key (user_id)
            references users (user_id);