create table if not exists `password`
(
    `id`          bigint auto_increment comment '密码ID',
    `version`     int              not null default 0 comment '乐观锁',
    `del_flag`    tinyint unsigned not null default 0 comment '逻辑删除',
    `create_time` datetime         not null comment '创建时间',
    `update_time` datetime         not null comment '更新时间',
    `user`        bigint           not null comment '用户ID',
    `url`         varchar(255)     not null comment '链接',
    `description` varchar(255)     not null comment '描述',
    `password`    varchar(255)     not null comment '密码',
    constraint pwd_pk primary key (id)
);