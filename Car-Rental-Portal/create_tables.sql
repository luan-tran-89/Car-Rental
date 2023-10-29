create table car
(
    car_id       int auto_increment
        primary key,
    cost_per_day double                                                                                        not null,
    fixed_cost   double                                                                                        not null,
    image        varchar(255)                                                                                  null,
    make         varchar(255)                                                                                  not null,
    model        varchar(255)                                                                                  not null,
    status       enum ('AVAILABLE', 'RESERVED', 'PICKED', 'UNDER_MAINTENANCE', 'DISABLED') default 'AVAILABLE' not null
);

create table maintenance
(
    id          int auto_increment
        primary key,
    description varchar(255)                                           null,
    end_date    datetime(6)                                            null,
    start_date  datetime(6)                                            null,
    status      enum ('IN_PROGRESS', 'FINISHED') default 'IN_PROGRESS' null,
    car_id      int                                                    not null,
    constraint FKgiorc601mf6t17bxerrge9vnt
        foreign key (car_id) references car (car_id)
);

create table payment_method
(
    card_type       varchar(31)  not null,
    method_id       int auto_increment
        primary key,
    approval_amount double       not null,
    balance         double       not null,
    card_number     varchar(255) not null,
    cvv             varchar(255) not null,
    expiry_date     varchar(255) not null,
    total_used      double       not null,
    used_amount     double       not null,
    user_id         int          not null
);

create table payments
(
    payment_id       int auto_increment
        primary key,
    description      varchar(255) null,
    payment_amount   double       null,
    status           varchar(255) null,
    transaction_date date         null,
    method_id        int          null,
    constraint FK2hfbjw9javech1r5vgok6lp6j
        foreign key (method_id) references payment_method (method_id)
);

create table rental
(
    rental_id  int auto_increment
        primary key,
    car_id     int         not null,
    end_date   datetime(6) not null,
    payment_id int         null,
    start_date datetime(6) not null,
    total_cost double      null,
    user_id    int         not null
);

create table user
(
    user_id              int auto_increment
        primary key,
    email                varchar(255)                                                                null,
    first_name           varchar(255)                                                                null,
    frequent_renter_type enum ('NONE', 'BRONZE', 'SILVER', 'GOLD')                default 'NONE'     null,
    last_name            varchar(255)                                                                null,
    password             varchar(255)                                                                null,
    phone                varchar(255)                                                                null,
    status               enum ('ACTIVE', 'DISABLE', 'DELETE')                     default 'ACTIVE'   null,
    user_name            varchar(255)                                                                null,
    user_role            enum ('ADMIN', 'MANAGER', 'CUSTOMER', 'FREQUENT_RENTER') default 'CUSTOMER' null,
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email)
);

create table card
(
    card_type        varchar(31)  not null,
    card_id          int auto_increment
        primary key,
    card_holder_name varchar(255) null,
    card_number      varchar(255) null,
    cvv              varchar(255) null,
    expiration_date  varchar(255) null,
    user_id          int          not null,
    constraint FKl4gbym62l738id056y12rt6q6
        foreign key (user_id) references user (user_id)
);

