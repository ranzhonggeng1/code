# 建立cfg文件管理
create table `cfgfile_version` (
  `id`           bigint(64)   not null
  comment '主键',
  `medicine_id`  bigint(64)   not null
  comment '药品id',
  `version`      varchar(14)  not null
  comment '药品版本',
  `path`         varchar(100) not null
  comment '文件路径',
  `file_name`    varchar(100) not null
  comment '文件名',
  `gmt_create`   datetime     not null
  comment '创建时间',
  `gmt_modified` datetime     not null
  comment '最后修改时间',
  `remark`       varchar(100) not null
  comment '备注',
  primary key (`id`)
)
  engine = innodb
  default charset = utf8mb4
  comment = 'cfg文件管理';

# 初始版本内置数据
INSERT INTO `cfgfile_version` (`id`,
                               `medicine_id`,
                               `version`,
                               `path`,
                               `file_name`,
                               `gmt_create`,
                               `gmt_modified`,
                               `remark`)
VALUES ('0',
        '0',
        '0',
        'cfg/',
        'medicine.properties',
        '2018-09-18 07:45:31',
        '2018-09-18 07:45:31',
        '初始版本');

INSERT INTO `cfgfile_version` (`id`,
                               `medicine_id`,
                               `version`,
                               `path`,
                               `file_name`,
                               `gmt_create`,
                               `gmt_modified`,
                               `remark`)
VALUES ('1', '1', '0', 'cfg/', '1.zip', '2018-09-25 01:14:42', '2018-09-25 01:14:42', '药品1');

# 初始系统配置
create table `sys_config` (
  `id`           bigint(64)   not null
  comment '主键',
  `code`         varchar(64)  not null
  comment '参数code',
  `name`         varchar(64)  not null
  comment '参数name',
  `value`        varchar(100) not null
  comment '参数value',
  `gmt_create`   datetime     not null
  comment '创建时间',
  `gmt_modified` datetime     not null
  comment '最后修改时间',
  `remark`       varchar(100) default null
  comment '备注',
  primary key (`id`),
  unique key `code_unique` (`code`)
)
  engine = innodb
  default charset = utf8mb4
  comment = 'cfg文件管理';


insert into sys_config (id, code, name, value, gmt_create, gmt_modified, remark)
values (1,
        'file_root_path',
        '记录文件路径',
        '/opt/server/file/',
        current_timestamp,
        current_timestamp,
        '手动');
insert into sys_config (id, code, name, value, gmt_create, gmt_modified, remark)
values (2, 'default_password', '默认密码', 'wslint', now(), now(), '');

#用户表
create table `user` (
  `id`        bigint(64) not null
  comment '用户主键',
  `user_code` varchar(100) default null
  comment '用户编码',
  `user_name` varchar(100) default null
  comment '用户名称',
  `password`  varchar(100) default null
  comment '密码',
  `sign`      varchar(100) default null
  comment '签名',
  `complete`  tinyint(1)   default null
  comment '是否完善资料',
  primary key (`id`),
  unique key `idx_user_code` (`user_code`)
)
  engine = innodb
  default charset = utf8mb4
  comment = '用户表';

#用户角色关联表
create table `user_role` (
  `user_id` bigint(64) not null
  comment '用户id',
  `role_id` bigint(64) not null
  comment '角色id'
)
  engine = innodb
  default charset = utf8mb4
  comment = '用户角色表';

#角色表
create table `role` (
  `id`        bigint(64) not null
  comment '角色主键',
  `role_name` varchar(100) default null
  comment '角色名称',
  primary key (`id`),
  unique key `idx_role_name` (`role_name`)
)
  engine = innodb
  default charset = utf8mb4
  comment = '角色表';

#角色权限表
create table `role_permission` (
  `role_id`       bigint(64) not null
  comment '角色id',
  `permission_id` bigint(64) not null
  comment '权限id'
)
  engine = innodb
  default charset = utf8mb4
  comment = '角色权限表';

#权限表
create table `permission` (
  `id`              bigint(64) not null
  comment '权限主键',
  `permission_code` varchar(100) default null
  comment '权限码',
  `permission_name` varchar(100) default null
  comment '权限名称',
  primary key (`id`),
  unique key `idx_permission_code` (`permission_code`)
)
  engine = innodb
  default charset = utf8mb4
  comment = '权限表';

# 初始化超级管理员
insert into user (id, user_code, user_name, password, complete)
values (0, 'admin', '系统管理员', '$2a$10$AKo0SVSV3NWI8839X.V50uaQdDGQpy15MVOqHbw87l35svMzGsfTK', true);
# 初始化超级管理员角色
insert into role (id, role_name)
values (0, '系统管理员角色');
# 初始化超级管理员与超级管理员角色的关联
insert into user_role (user_id, role_id)
values (0, 0);
# 初始化权限
insert into permission (id, permission_name, permission_code)
values (0, '批次创建', '0');
insert into permission (id, permission_name, permission_code)
values (1, '审批', '1');
insert into permission (id, permission_name, permission_code)
values (2, '审批进度查看', '2');
insert into permission (id, permission_name, permission_code)
values (3, '原始记录修改审核', '3');
insert into permission (id, permission_name, permission_code)
values (4, '原始记录作业', '4');
insert into permission (id, permission_name, permission_code)
values (5, '原始记录查看', '5');
insert into permission (id, permission_name, permission_code)
values (6, '报告查看', '6');
insert into permission (id, permission_name, permission_code)
values (7, '原始记录复核', '7');
insert into permission (id, permission_name, permission_code)
values (8, '发起审批', '8');
insert into permission (id, permission_name, permission_code)
values (9, '群组管理', '9');
insert into permission (id, permission_name, permission_code)
values (10, '重做实验', '10');
insert into permission (id, permission_name, permission_code)
values (11, '重置密码', '11');
insert into permission (id, permission_name, permission_code)
values (12, '数据追溯', '12');
insert into permission (id, permission_name, permission_code)
values (13, '报告作业', '13');
insert into permission (id, permission_name, permission_code)
values (14, '报告复核', '14');
insert into permission (id, permission_name, permission_code)
values (15, '报告修改审核', '15');
# 初始化超级管理员角色拥有权限组权限
insert into role_permission (role_id, permission_id)
values (0, 9);

# 创建用户权限视图
create or replace view user_permission as
  select u.id              as id,
         u.user_code       as user_code,
         u.user_name       as user_name,
         p.permission_code as permission_code,
         p.permission_name as permission_name
  from user u,
       user_role ur,
       role_permission rp,
       permission p
  where u.id = ur.user_id
    and ur.role_id = rp.role_id
    and rp.permission_id = p.id;

create table `wr_report` (
  `id`           bigint(64) not null
  comment '报表主键',
  `batch_no`     int(11)    not null
  comment '批次号',
  `medicine`     int(11)    not null
  comment '药品名称',
  `first_class`  int(11)     default null
  comment '大类',
  `second_class` int(11)     default null
  comment '小类',
  `third_class`  int(11)     default null
  comment '第三类',
  `class1`       int(11)     default null
  comment '备用类1',
  `class2`       int(11)     default null
  comment '备用类2',
  `class3`       int(11)     default null
  comment '备用类3',
  `hold1`        varchar(45) default null
  comment '备用字段1',
  `hold2`        varchar(45) default null
  comment '备用字段2',
  `hold3`        varchar(45) default null
  comment '备用字段3',
  `gmt_create`   datetime   not null
  comment '创建时间',
  `gmt_modified` datetime   not null
  comment '最后操作时间',
  primary key (`id`)
)
  engine = innodb
  default charset = utf8mb4
  comment = '智慧报表报表数据主表';

create table `wr_report_data` (
  `id`              int(11)      not null auto_increment
  comment '主键',
  `report_id`       bigint(64)   not null
  comment '主表主键id',
  `order_id`        int(11)      not null
  comment '明细数据顺序id',
  `data`            varchar(1000)         default null
  comment '具体数据',
  `inputer`         bigint(64)            default null
  comment '录入人',
  `reviewer`        bigint(64)            default null
  comment '复核人',
  `change_reason`   varchar(1000)         default null
  comment '修改原因',
  `gmt_create`      datetime     not null
  comment '创建时间',
  `gmt_modified`    datetime     not null
  comment '最后操作时间',
  `version`         int(11)               default null
  comment '版本号',
  `status`          int(11)               default null
  comment '状态',
  `hold1`           varchar(45)           default null
  comment '备用字段1',
  `hold2`           varchar(45)           default null
  comment '备用字段2',
  `hold3`           varchar(45)           default null
  comment '备用字段3',
  `hold4`           int(11)               default null
  comment '备用字段4',
  `hold5`           int(11)               default null
  comment '备用字段5',
  `modify_reviewer` bigint(64)   null     default null
  comment '修改审核人',
  `imageurl`        varchar(200) null     default null
  comment '图片链接',
  primary key (`id`)
)
  engine = innodb
  default charset = utf8mb4
  comment = '智慧报表报表数据明细表';

# 建立工作流任务表
create table `wr_wf_current_task` (
  `task_id`        bigint(64)   not null
  comment '任务id',
  `medicine_id`    bigint(64)   not null
  comment '药品编号',
  `batch_no`       bigint(64)   not null
  comment '批次号',
  `reviewer_id`    bigint(64)    default null
  comment '审批人id',
  `review_comment` varchar(2000) default null
  comment '审批意见',
  `gmt_create`     datetime     not null
  comment '创建时间',
  `gmt_modified`   datetime     not null
  comment '最后修改时间',
  `operator_id`    bigint(64)   not null
  comment '操作人id',
  `operator_name`  varchar(100) not null
  comment '操作人name',
  `status`         int(11)      not null
  comment '实验所处的状态',
  `hold1`          varchar(200)  default null
  comment '保留字段'
)
  engine = innodb
  default charset = utf8mb4
  comment = '当前任务表';

# 建立工作流任务表
create table `wr_wf_complete_task` (
  `task_id`        bigint(64)   not null
  comment '任务id',
  `medicine_id`    bigint(64)   not null
  comment '药品编号',
  `batch_no`       bigint(64)   not null
  comment '批次号',
  `reviewer_id`    bigint(64)    default null
  comment '审批人id',
  `review_comment` varchar(2000) default null
  comment '审批意见',
  `gmt_create`     datetime     not null
  comment '创建时间',
  `gmt_modified`   datetime     not null
  comment '最后修改时间',
  `operator_id`    bigint(64)   not null
  comment '操作人id',
  `operator_name`  varchar(100) not null
  comment '操作人name',
  `status`         int(11)      not null
  comment '实验所处的状态',
  `hold1`          varchar(200)  default null
  comment '保留字段'
)
  engine = innodb
  default charset = utf8mb4
  comment = '历史任务表';

# 重构数据和流程相关建表
-- auto-generated definition
create table data_batch
(
  id            bigint(64)  not null
    primary key,
  batch_no      varchar(64) not null,
  medicine_id   bigint(64)  not null,
  status        int         not null,
  next_operator bigint(64)  null,
  valid         tinyint(1)  not null,
  hold1         varchar(64) null,
  hold2         varchar(64) null,
  hold3         varchar(64) null,
  gmt_create    timestamp   null,
  gmt_modified  timestamp   null,
  create_user   bigint(64)  null,
  modify_user   bigint(64)  null
)
  comment '批次数据';

-- auto-generated definition
create table data_class
(
  id              bigint(64)  not null
    primary key,
  batch_id        bigint(64)  not null,
  first_class_id  bigint(64)  null,
  second_class_id bigint(64)  not null,
  hold1           varchar(64) null,
  hold2           varchar(64) null,
  hold3           varchar(64) null,
  gmt_create      timestamp   null,
  gmt_modified    timestamp   null,
  create_user     bigint(64)  null,
  modify_user     bigint(64)  null
)
  comment '类别数据';


CREATE OR REPLACE VIEW vw_data_batch_detail AS
  select d.*,
         (select user_name from user where id = d.next_operator) as next_operator_name,
         (select user_name from user where id = d.create_user)   as create_user_name,
         (select user_name from user where id = d.modify_user)   as modify_user_name
  from data_batch d;
CREATE OR REPLACE VIEW vw_data_class_detail AS
  select d.*,
         (select user_name from user where id = d.create_user) as create_user_name,
         (select user_name from user where id = d.modify_user) as modify_user_name
  from data_class d;

-- auto-generated definition
create table wf_batch_complete_task
(
  id            bigint(64)    not null
    primary key,
  batch_id      bigint(64)    not null,
  operation     int           not null,
  operator      bigint(64)    not null,
  next_operator bigint(64)    null,
  reason        varchar(1000) null,
  status        int           not null,
  next_status   int           not null,
  hold1         varchar(64)   null,
  hold2         varchar(64)   null,
  hold3         varchar(64)   null,
  gmt_create    timestamp     null,
  gmt_modified  timestamp     null
);

-- auto-generated definition
create table wf_batch_current_task
(
  id            bigint(64)    not null
    primary key,
  batch_id      bigint(64)    not null,
  operation     int           not null,
  operator      bigint(64)    not null,
  next_operator bigint(64)    null,
  reason        varchar(1000) null,
  status        int           not null,
  next_status   int           not null,
  hold1         varchar(64)   null,
  hold2         varchar(64)   null,
  hold3         varchar(64)   null,
  gmt_create    timestamp     null,
  gmt_modified  timestamp     null
)
  comment '批次数据工作流';

CREATE VIEW vw_wf_batch_current_task AS
  select wf.*,
         (case
            when wf.operation = 1 then '创建批次'
            when wf.operation = 2 then '提交审批'
            when wf.operation = 3 then '审批通过'
            when wf.operation = 4 then '审批驳回'
            when wf.operation = 5 then '重做实验' end)                as operation_name,
         (select user_name from user where id = wf.operator)      as operator_name,
         (select user_name from user where id = wf.next_operator) as next_operator_name,
         (case
            when wf.status = 0 then '开始'
            when wf.status = 1 then '实验中'
            when wf.status = 2 then '审批中'
            when wf.status = 3 then '未通过'
            when wf.status = 9 then '结束' end)                     as status_name,
         (case
            when wf.next_status = 0 then '开始'
            when wf.next_status = 1 then '实验中'
            when wf.next_status = 2 then '审批中'
            when wf.next_status = 3 then '未通过'
            when wf.next_status = 9 then '结束' end)                as next_status_name
  from wf_batch_current_task wf;

CREATE VIEW vw_wf_batch_complete_task AS
  select wf.*,
         (case
            when wf.operation = 1 then '创建批次'
            when wf.operation = 2 then '提交审批'
            when wf.operation = 3 then '审批通过'
            when wf.operation = 4 then '审批驳回'
            when wf.operation = 5 then '重做实验' end)                as operation_name,
         (select user_name from user where id = wf.operator)      as operator_name,
         (select user_name from user where id = wf.next_operator) as next_operator_name,
         (case
            when wf.status = 0 then '开始'
            when wf.status = 1 then '实验中'
            when wf.status = 2 then '审批中'
            when wf.status = 3 then '未通过'
            when wf.status = 9 then '结束' end)                     as status_name,
         (case
            when wf.next_status = 0 then '开始'
            when wf.next_status = 1 then '实验中'
            when wf.next_status = 2 then '审批中'
            when wf.next_status = 3 then '未通过'
            when wf.next_status = 9 then '结束' end)                as next_status_name
  from wf_batch_complete_task wf;

-- auto-generated definition
create table data_record
(
  id            bigint(64)    not null
    primary key,
  order_id      bigint(64)    not null,
  class_id      bigint(64)    not null,
  batch_id      bigint(64)    not null,
  data          varchar(1000) null,
  img_url       varchar(1000) null,
  status        int           not null,
  next_operator bigint(64)    null,
  old_id        bigint(64)    null,
  hold1         varchar(64)   null,
  hold2         varchar(64)   null,
  hold3         varchar(64)   null,
  gmt_create    timestamp     null,
  gmt_modified  timestamp     null,
  create_user   bigint(64)    null,
  modify_user   bigint(64)    null
)
  comment '记录数据';

create or replace view vw_data_record_detail as
  select re.*,
         ba.medicine_id,
         ba.batch_no,
         cl.first_class_id,
         cl.second_class_id,
         (select user_name from user where id = re.next_operator) as next_operator_name,
         (select user_name from user where id = re.create_user)   as create_user_name,
         (select user_name from user where id = re.modify_user)   as modify_user_name
  from data_record re,
       data_batch ba,
       data_class cl
  where re.batch_id = ba.id
    and re.class_id = cl.id;

-- auto-generated definition
create table wf_record_current_task
(
  id            bigint(64)    not null
    primary key,
  record_id     bigint(64)    not null,
  operation     int           not null,
  operator      bigint(64)    not null,
  next_operator bigint(64)    null,
  reason        varchar(1000) null,
  old_data      varchar(1000) null,
  status        int           null,
  next_status   int           null,
  hold1         varchar(64)   null,
  hold2         varchar(64)   null,
  hold3         varchar(64)   null,
  gmt_create    timestamp     null,
  gmt_modified  timestamp     null
)
  comment '数据工作流表';

create view vw_wf_record_current_task as
  select wf.*,
         (select user_name from user where id = wf.operator)      as operator_name,
         (select user_name from user where id = wf.next_operator) as next_operator_name
  from wf_record_current_task wf;

-- auto-generated definition
create table wf_record_complete_task
(
  id            bigint(64)    not null
    primary key,
  record_id     bigint(64)    not null,
  operation     int           not null,
  operator      bigint(64)    not null,
  next_operator bigint(64)    null,
  reason        varchar(1000) null,
  old_data      varchar(1000) null,
  status        int           null,
  next_status   int           null,
  hold1         varchar(64)   null,
  hold2         varchar(64)   null,
  hold3         varchar(64)   null,
  gmt_create    timestamp     null,
  gmt_modified  timestamp     null
)
  comment '数据工作流表';

create view vw_wf_record_complete_task as
  select wf.*,
         (select user_name from user where id = wf.operator)      as operator_name,
         (select user_name from user where id = wf.next_operator) as next_operator_name
  from wf_record_complete_task wf;

-- auto-generated definition
create table data_report
(
  id            bigint(64)    not null
    primary key,
  order_id      bigint(64)    not null,
  batch_id      bigint(64)    not null,
  data          varchar(1000) null,
  img_url       varchar(1000) null,
  status        int           not null,
  next_operator bigint(64)    null,
  hold1         varchar(64)   null,
  hold2         varchar(64)   null,
  hold3         varchar(64)   null,
  gmt_create    timestamp     null,
  gmt_modified  timestamp     null,
  create_user   bigint(64)    null,
  modify_user   bigint(64)    null
)
  comment '报告数据';

create or replace view vw_data_report_detail as
  select re.*,
         ba.medicine_id,
         ba.batch_no,
         (select user_name from user where id = re.next_operator) as next_operator_name,
         (select user_name from user where id = re.create_user)   as create_user_name,
         (select user_name from user where id = re.modify_user)   as modify_user_name
  from data_report re,
       data_batch ba
  where re.batch_id = ba.id;

-- auto-generated definition
create table wf_report_current_task
(
  id            bigint(64)    not null
    primary key,
  report_id     bigint(64)    not null,
  operation     int           not null,
  operator      bigint(64)    not null,
  next_operator bigint(64)    null,
  reason        varchar(1000) null,
  old_data      varchar(1000) null,
  status        int           null,
  next_status   int           null,
  hold1         varchar(64)   null,
  hold2         varchar(64)   null,
  hold3         varchar(64)   null,
  gmt_create    timestamp     null,
  gmt_modified  timestamp     null
)
  comment '数据工作流表';

create view vw_wf_report_current_task as
  select wf.*,
         (select user_name from user where id = wf.operator)      as operator_name,
         (select user_name from user where id = wf.next_operator) as next_operator_name
  from wf_report_current_task wf;

create table wf_report_complete_task
(
  id            bigint(64)    not null
    primary key,
  report_id     bigint(64)    not null,
  operation     int           not null,
  operator      bigint(64)    not null,
  next_operator bigint(64)    null,
  reason        varchar(1000) null,
  old_data      varchar(1000) null,
  status        int           null,
  next_status   int           null,
  hold1         varchar(64)   null,
  hold2         varchar(64)   null,
  hold3         varchar(64)   null,
  gmt_create    timestamp     null,
  gmt_modified  timestamp     null
)
  comment '数据工作流表';

create view vw_wf_report_complete_task as
  select wf.*,
         (select user_name from user where id = wf.operator)      as operator_name,
         (select user_name from user where id = wf.next_operator) as next_operator_name
  from wf_report_complete_task wf;

