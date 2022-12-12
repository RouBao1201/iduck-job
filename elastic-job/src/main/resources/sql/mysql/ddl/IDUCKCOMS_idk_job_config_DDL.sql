--定时任务配置表
create table iduckcoms.idk_job_config
(
    job_name         varchar(255) not null comment '任务名称'
        primary key,
    cron             varchar(255) not null comment 'cron表达式',
    job_mode         varchar(255) not null comment '任务类型',
    bean_name        varchar(255) not null comment 'bean名称',
    stored_procedure varchar(255) not null comment '存储过程',
    sharding_count   int          not null comment '分片数',
    sharding_param   varchar(255) not null comment '分片参数',
    source_key       varchar(255) not null comment '任务数据源key',
    state            varchar(5)   not null comment '任务状态',
    create_time      datetime     not null comment '创建时间'
);
