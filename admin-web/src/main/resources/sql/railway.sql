

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login_user_token
-- ----------------------------
DROP TABLE IF EXISTS `login_user_token`;
CREATE TABLE `login_user_token`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'token',
  `time_out` int(10) NOT NULL COMMENT '失效时间 秒',
  `time` bigint(30) NULL DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`user_id`, `token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '拍照人登录token' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for oa_notify
-- ----------------------------
DROP TABLE IF EXISTS `oa_notify`;
CREATE TABLE `oa_notify`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '内容',
  `files` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '附件',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `oa_notify_del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '通知通告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oa_notify_record
-- ----------------------------
DROP TABLE IF EXISTS `oa_notify_record`;
CREATE TABLE `oa_notify_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `notify_id` bigint(20) NULL DEFAULT NULL COMMENT '通知通告ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '接受人',
  `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '阅读标记',
  `read_date` date NULL DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `oa_notify_record_notify_id`(`notify_id`) USING BTREE,
  INDEX `oa_notify_record_user_id`(`user_id`) USING BTREE,
  INDEX `oa_notify_record_read_flag`(`is_read`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '通知通告发送记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '中国铁路中心', 1, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '标签名',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据值',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `sort` decimal(10, 0) NULL DEFAULT NULL COMMENT '排序（升序）',
  `parent_id` bigint(64) NULL DEFAULT 0 COMMENT '父级编号',
  `create_by` int(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_dict_value`(`value`) USING BTREE,
  INDEX `sys_dict_label`(`name`) USING BTREE,
  INDEX `sys_dict_del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) NULL DEFAULT NULL COMMENT '文件类型',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件上传' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `time` int(11) NULL DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 176 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '基础管理', '', '', 0, 'fa fa-bars', 0, '2017-08-09 22:49:47', NULL);
INSERT INTO `sys_menu` VALUES (2, 3, '系统菜单', 'sys/menu/', 'sys:menu:menu', 1, 'fa fa-th-list', 2, '2017-08-09 22:55:15', NULL);
INSERT INTO `sys_menu` VALUES (3, 0, '系统管理', NULL, NULL, 0, 'fa fa-desktop', 5, '2017-08-09 23:06:55', '2017-08-14 14:13:43');
INSERT INTO `sys_menu` VALUES (6, 3, '用户管理', 'sys/user/', 'sys:user:user', 1, 'fa fa-user', 0, '2017-08-10 14:12:11', NULL);
INSERT INTO `sys_menu` VALUES (7, 3, '角色管理', 'sys/role', 'sys:role:role', 1, 'fa fa-paw', 1, '2017-08-10 14:13:19', NULL);
INSERT INTO `sys_menu` VALUES (12, 6, '新增', '', 'sys:user:add', 2, '', 0, '2017-08-14 10:51:35', NULL);
INSERT INTO `sys_menu` VALUES (13, 6, '编辑', '', 'sys:user:edit', 2, '', 0, '2017-08-14 10:52:06', NULL);
INSERT INTO `sys_menu` VALUES (14, 6, '删除', NULL, 'sys:user:remove', 2, NULL, 0, '2017-08-14 10:52:24', NULL);
INSERT INTO `sys_menu` VALUES (15, 7, '新增', '', 'sys:role:add', 2, '', 0, '2017-08-14 10:56:37', NULL);
INSERT INTO `sys_menu` VALUES (20, 2, '新增', '', 'sys:menu:add', 2, '', 0, '2017-08-14 10:59:32', NULL);
INSERT INTO `sys_menu` VALUES (21, 2, '编辑', '', 'sys:menu:edit', 2, '', 0, '2017-08-14 10:59:56', NULL);
INSERT INTO `sys_menu` VALUES (22, 2, '删除', '', 'sys:menu:remove', 2, '', 0, '2017-08-14 11:00:26', NULL);
INSERT INTO `sys_menu` VALUES (24, 6, '批量删除', '', 'sys:user:batchRemove', 2, '', 0, '2017-08-14 17:27:18', NULL);
INSERT INTO `sys_menu` VALUES (25, 6, '停用', NULL, 'sys:user:disable', 2, NULL, 0, '2017-08-14 17:27:43', NULL);
INSERT INTO `sys_menu` VALUES (26, 6, '重置密码', '', 'sys:user:resetPwd', 2, '', 0, '2017-08-14 17:28:34', NULL);
INSERT INTO `sys_menu` VALUES (27, 91, '系统日志', 'common/log', 'common:log', 1, 'fa fa-warning', 0, '2017-08-14 22:11:53', NULL);
INSERT INTO `sys_menu` VALUES (28, 27, '刷新', NULL, 'sys:log:list', 2, NULL, 0, '2017-08-14 22:30:22', NULL);
INSERT INTO `sys_menu` VALUES (29, 27, '删除', NULL, 'sys:log:remove', 2, NULL, 0, '2017-08-14 22:30:43', NULL);
INSERT INTO `sys_menu` VALUES (30, 27, '清空', NULL, 'sys:log:clear', 2, NULL, 0, '2017-08-14 22:31:02', NULL);
INSERT INTO `sys_menu` VALUES (55, 7, '编辑', '', 'sys:role:edit', 2, '', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (56, 7, '删除', '', 'sys:role:remove', 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (57, 91, '运行监控', '/druid/index.html', '', 1, 'fa fa-caret-square-o-right', 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (61, 2, '批量删除', '', 'sys:menu:batchRemove', 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (62, 7, '批量删除', '', 'sys:role:batchRemove', 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (71, 1, '文件管理', '/common/sysFile', 'common:sysFile:sysFile', 1, 'fa fa-folder-open', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (73, 3, '部门管理', '/system/sysDept', 'system:sysDept:sysDept', 1, 'fa fa-users', 3, NULL, NULL);
INSERT INTO `sys_menu` VALUES (74, 73, '增加', '/system/sysDept/add', 'system:sysDept:add', 2, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (75, 73, '刪除', 'system/sysDept/remove', 'system:sysDept:remove', 2, NULL, 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (76, 73, '编辑', '/system/sysDept/edit', 'system:sysDept:edit', 2, NULL, 3, NULL, NULL);
INSERT INTO `sys_menu` VALUES (78, 1, '数据字典', '/common/dict', 'common:dict:dict', 1, 'fa fa-book', 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (79, 78, '增加', '/common/dict/add', 'common:dict:add', 2, NULL, 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (80, 78, '编辑', '/common/dict/edit', 'common:dict:edit', 2, NULL, 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (81, 78, '删除', '/common/dict/remove', 'common:dict:remove', 2, '', 3, NULL, NULL);
INSERT INTO `sys_menu` VALUES (83, 78, '批量删除', '/common/dict/batchRemove', 'common:dict:batchRemove', 2, '', 4, NULL, NULL);
INSERT INTO `sys_menu` VALUES (91, 0, '系统监控', '', '', 0, 'fa fa-video-camera', 5, NULL, NULL);
INSERT INTO `sys_menu` VALUES (92, 91, '在线用户', 'sys/online', '', 1, 'fa fa-user', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (105, 0, '照片查询', '/railway/station/leaderLook', 'photom', 1, 'fa fa-file-pdf-o', 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (106, 0, '任务调度', '/railway/order/list', 'railway:order:list', 1, 'fa fa-desktop', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (107, 0, '拍照人员', '/railway/person/list', 'railway:person:list', 1, 'fa fa-user', 3, NULL, NULL);
INSERT INTO `sys_menu` VALUES (108, 0, '照片管理', '/railway/photomanage', 'railway:photomanage:list', 1, 'fa fa-bars', 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (109, 3, '站点管理', '/railway/station', 'railway:station:station', 1, 'fa fa-bars', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (110, 109, '添加', '/railway/station/add', 'railway:station:add', 2, 'fa fa-plus', 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (111, 109, '删除', '/railway/station/remove', 'railway:station:remove', 2, 'fa fa-close', 3, NULL, NULL);
INSERT INTO `sys_menu` VALUES (112, 109, '编辑', '/railway/station/edit', 'railway:station:edit', 2, 'fa fa-pencil-square-o', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (114, 106, '添加', '/railway/order/add', 'railway:order:add', 2, 'fa fa-address-book-o', 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (115, 106, '修改页面', '/railway/order/edit', 'railway:order:edit', 2, '', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (116, 106, '修改', '/railway/order/update', 'railway:order:edit', 2, '', 3, NULL, NULL);
INSERT INTO `sys_menu` VALUES (117, 106, '复制', '/railway/order/copy', 'railway:order:copy', 2, '', 4, NULL, NULL);
INSERT INTO `sys_menu` VALUES (118, 107, '添加', '/railway/person/add', 'railway:person:add', 2, '', 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (119, 107, '修改页面', '/railway/person/edit', 'railway:person:edit', 2, '', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (120, 107, '获取', '/railway/person/get', 'railway:person:edit', 2, '', 3, NULL, NULL);
INSERT INTO `sys_menu` VALUES (121, 107, '修改', '/railway/person/update', 'railway:person:edit', 2, '', 4, NULL, NULL);
INSERT INTO `sys_menu` VALUES (122, 107, '删除', '/railway/person/remove', 'railway:person:remove', 2, '', 5, NULL, NULL);
INSERT INTO `sys_menu` VALUES (123, 108, '查看照片', '/railway/photomanage/viewPhoto', 'railway:photomanage:viewPhoto', 2, '', 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (124, 108, '查询', '/railway/photomanage/list', 'railway:photomanage:select', 2, '', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (125, 108, '重置', '/railway/photomanage/list', 'railway:photomanage:reset', 2, '', 3, NULL, NULL);
INSERT INTO `sys_menu` VALUES (126, 108, '编辑', '/railway/photomanage/edit', 'railway:photomanage:edit', 2, '', 4, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级用户角色', 'admin', '拥有最高权限', 2, '2017-08-12 00:43:52', '2017-08-12 19:14:59');
INSERT INTO `sys_role` VALUES (60, '管理员', NULL, '进行管理', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (61, '领导', NULL, '主要由领导进行查询', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4617 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (3359, 61, 105);
INSERT INTO `sys_role_menu` VALUES (3360, 61, -1);
INSERT INTO `sys_role_menu` VALUES (4522, 1, -1);
INSERT INTO `sys_role_menu` VALUES (4523, 1, 125);
INSERT INTO `sys_role_menu` VALUES (4524, 1, 124);
INSERT INTO `sys_role_menu` VALUES (4525, 1, 123);
INSERT INTO `sys_role_menu` VALUES (4526, 1, 122);
INSERT INTO `sys_role_menu` VALUES (4527, 1, 121);
INSERT INTO `sys_role_menu` VALUES (4528, 1, 120);
INSERT INTO `sys_role_menu` VALUES (4529, 1, 119);
INSERT INTO `sys_role_menu` VALUES (4530, 1, 118);
INSERT INTO `sys_role_menu` VALUES (4531, 1, 117);
INSERT INTO `sys_role_menu` VALUES (4532, 1, 116);
INSERT INTO `sys_role_menu` VALUES (4533, 1, 115);
INSERT INTO `sys_role_menu` VALUES (4534, 1, 114);
INSERT INTO `sys_role_menu` VALUES (4535, 1, 30);
INSERT INTO `sys_role_menu` VALUES (4536, 1, 29);
INSERT INTO `sys_role_menu` VALUES (4537, 1, 28);
INSERT INTO `sys_role_menu` VALUES (4538, 1, 92);
INSERT INTO `sys_role_menu` VALUES (4539, 1, 57);
INSERT INTO `sys_role_menu` VALUES (4540, 1, 27);
INSERT INTO `sys_role_menu` VALUES (4541, 1, 112);
INSERT INTO `sys_role_menu` VALUES (4542, 1, 111);
INSERT INTO `sys_role_menu` VALUES (4543, 1, 110);
INSERT INTO `sys_role_menu` VALUES (4544, 1, 76);
INSERT INTO `sys_role_menu` VALUES (4545, 1, 75);
INSERT INTO `sys_role_menu` VALUES (4546, 1, 74);
INSERT INTO `sys_role_menu` VALUES (4547, 1, 62);
INSERT INTO `sys_role_menu` VALUES (4548, 1, 56);
INSERT INTO `sys_role_menu` VALUES (4549, 1, 55);
INSERT INTO `sys_role_menu` VALUES (4550, 1, 15);
INSERT INTO `sys_role_menu` VALUES (4551, 1, 26);
INSERT INTO `sys_role_menu` VALUES (4552, 1, 25);
INSERT INTO `sys_role_menu` VALUES (4553, 1, 24);
INSERT INTO `sys_role_menu` VALUES (4554, 1, 14);
INSERT INTO `sys_role_menu` VALUES (4555, 1, 13);
INSERT INTO `sys_role_menu` VALUES (4556, 1, 12);
INSERT INTO `sys_role_menu` VALUES (4557, 1, 61);
INSERT INTO `sys_role_menu` VALUES (4558, 1, 22);
INSERT INTO `sys_role_menu` VALUES (4559, 1, 21);
INSERT INTO `sys_role_menu` VALUES (4560, 1, 20);
INSERT INTO `sys_role_menu` VALUES (4561, 1, 109);
INSERT INTO `sys_role_menu` VALUES (4562, 1, 73);
INSERT INTO `sys_role_menu` VALUES (4563, 1, 7);
INSERT INTO `sys_role_menu` VALUES (4564, 1, 6);
INSERT INTO `sys_role_menu` VALUES (4565, 1, 2);
INSERT INTO `sys_role_menu` VALUES (4566, 1, 83);
INSERT INTO `sys_role_menu` VALUES (4567, 1, 81);
INSERT INTO `sys_role_menu` VALUES (4568, 1, 80);
INSERT INTO `sys_role_menu` VALUES (4569, 1, 79);
INSERT INTO `sys_role_menu` VALUES (4570, 1, 78);
INSERT INTO `sys_role_menu` VALUES (4571, 1, 71);
INSERT INTO `sys_role_menu` VALUES (4572, 1, 108);
INSERT INTO `sys_role_menu` VALUES (4573, 1, 107);
INSERT INTO `sys_role_menu` VALUES (4574, 1, 106);
INSERT INTO `sys_role_menu` VALUES (4575, 1, 105);
INSERT INTO `sys_role_menu` VALUES (4576, 1, 91);
INSERT INTO `sys_role_menu` VALUES (4577, 1, 3);
INSERT INTO `sys_role_menu` VALUES (4578, 1, 1);
INSERT INTO `sys_role_menu` VALUES (4617, 60, 125);
INSERT INTO `sys_role_menu` VALUES (4618, 60, 124);
INSERT INTO `sys_role_menu` VALUES (4619, 60, 123);
INSERT INTO `sys_role_menu` VALUES (4620, 60, 122);
INSERT INTO `sys_role_menu` VALUES (4621, 60, 121);
INSERT INTO `sys_role_menu` VALUES (4622, 60, 120);
INSERT INTO `sys_role_menu` VALUES (4623, 60, 119);
INSERT INTO `sys_role_menu` VALUES (4624, 60, 118);
INSERT INTO `sys_role_menu` VALUES (4625, 60, 117);
INSERT INTO `sys_role_menu` VALUES (4626, 60, 116);
INSERT INTO `sys_role_menu` VALUES (4627, 60, 115);
INSERT INTO `sys_role_menu` VALUES (4628, 60, 114);
INSERT INTO `sys_role_menu` VALUES (4629, 60, 105);
INSERT INTO `sys_role_menu` VALUES (4630, 60, 112);
INSERT INTO `sys_role_menu` VALUES (4631, 60, 111);
INSERT INTO `sys_role_menu` VALUES (4632, 60, 110);
INSERT INTO `sys_role_menu` VALUES (4633, 60, 76);
INSERT INTO `sys_role_menu` VALUES (4634, 60, 75);
INSERT INTO `sys_role_menu` VALUES (4635, 60, 74);
INSERT INTO `sys_role_menu` VALUES (4636, 60, 62);
INSERT INTO `sys_role_menu` VALUES (4637, 60, 56);
INSERT INTO `sys_role_menu` VALUES (4638, 60, 55);
INSERT INTO `sys_role_menu` VALUES (4639, 60, 15);
INSERT INTO `sys_role_menu` VALUES (4640, 60, 26);
INSERT INTO `sys_role_menu` VALUES (4641, 60, 25);
INSERT INTO `sys_role_menu` VALUES (4642, 60, 24);
INSERT INTO `sys_role_menu` VALUES (4643, 60, 14);
INSERT INTO `sys_role_menu` VALUES (4644, 60, 13);
INSERT INTO `sys_role_menu` VALUES (4645, 60, 12);
INSERT INTO `sys_role_menu` VALUES (4646, 60, 107);
INSERT INTO `sys_role_menu` VALUES (4647, 60, 106);
INSERT INTO `sys_role_menu` VALUES (4648, 60, 109);
INSERT INTO `sys_role_menu` VALUES (4649, 60, 73);
INSERT INTO `sys_role_menu` VALUES (4650, 60, 7);
INSERT INTO `sys_role_menu` VALUES (4651, 60, 6);
INSERT INTO `sys_role_menu` VALUES (4652, 60, 126);
INSERT INTO `sys_role_menu` VALUES (4653, 60, 108);
INSERT INTO `sys_role_menu` VALUES (4654, 60, -1);
INSERT INTO `sys_role_menu` VALUES (4655, 60, 3);

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务调用的方法名',
  `is_concurrent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务是否有状态',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `bean_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `job_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态',
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务分组',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `spring_bean` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Spring bean',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `dept_id` int(20) NULL DEFAULT NULL COMMENT '部门',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别:0男 1女',
  `birth` datetime(0) NULL DEFAULT NULL COMMENT '出身日期',
  `pic_id` int(11) NULL DEFAULT NULL COMMENT '头像',
  `live_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现居住地',
  `hobby` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '爱好',
  `province` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在城市',
  `district` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在地区',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 142 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '超级管理员', 'c1cee993ece039aa8b0efbe6f2754082', 1, 'lg932740579@163.com', '18108258001', 1, 1, '2017-08-15 21:40:39', '2017-08-15 21:41:00', 96, '2017-12-14 00:00:00', 138, 'ccc', '122;121;', '北京市', '北京市市辖区', '东城区');
INSERT INTO `sys_user` VALUES (137, 'system', '管理员', '4299804831a9b29cc327e3b5290b0bb3', 1, 'fengchaseyou@163.com', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (140, 'lingdao', '领导', 'fdd4a0f3955e79e4cfa3744a158e991d', 1, 'fengchaseyou@163.com', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (141, 'Reviewer', '照片查看者', 'e6dab84e1edd411151d97cd34ca07f82', 1, 'abc@hotmail.com', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (73, 30, 48);
INSERT INTO `sys_user_role` VALUES (74, 30, 49);
INSERT INTO `sys_user_role` VALUES (75, 30, 50);
INSERT INTO `sys_user_role` VALUES (76, 31, 48);
INSERT INTO `sys_user_role` VALUES (77, 31, 49);
INSERT INTO `sys_user_role` VALUES (78, 31, 52);
INSERT INTO `sys_user_role` VALUES (79, 32, 48);
INSERT INTO `sys_user_role` VALUES (80, 32, 49);
INSERT INTO `sys_user_role` VALUES (81, 32, 50);
INSERT INTO `sys_user_role` VALUES (82, 32, 51);
INSERT INTO `sys_user_role` VALUES (83, 32, 52);
INSERT INTO `sys_user_role` VALUES (84, 33, 38);
INSERT INTO `sys_user_role` VALUES (85, 33, 49);
INSERT INTO `sys_user_role` VALUES (86, 33, 52);
INSERT INTO `sys_user_role` VALUES (87, 34, 50);
INSERT INTO `sys_user_role` VALUES (88, 34, 51);
INSERT INTO `sys_user_role` VALUES (89, 34, 52);
INSERT INTO `sys_user_role` VALUES (106, 124, 1);
INSERT INTO `sys_user_role` VALUES (111, 2, 1);
INSERT INTO `sys_user_role` VALUES (113, 131, 48);
INSERT INTO `sys_user_role` VALUES (117, 135, 1);
INSERT INTO `sys_user_role` VALUES (120, 134, 1);
INSERT INTO `sys_user_role` VALUES (121, 134, 48);
INSERT INTO `sys_user_role` VALUES (123, 130, 1);
INSERT INTO `sys_user_role` VALUES (124, NULL, 48);
INSERT INTO `sys_user_role` VALUES (125, 132, 52);
INSERT INTO `sys_user_role` VALUES (126, 132, 49);
INSERT INTO `sys_user_role` VALUES (127, 123, 48);
INSERT INTO `sys_user_role` VALUES (132, 36, 48);
INSERT INTO `sys_user_role` VALUES (139, 137, 60);
INSERT INTO `sys_user_role` VALUES (143, 140, 61);
INSERT INTO `sys_user_role` VALUES (144, 141, 61);
INSERT INTO `sys_user_role` VALUES (145, 1, 1);

-- ----------------------------
-- Table structure for tbl_order
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order`;
CREATE TABLE `tbl_order`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `train_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '车厢号',
  `start_station_id` bigint(32) NULL DEFAULT NULL COMMENT '发站id',
  `start_station_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发站名称',
  `end_station_id` bigint(32) NULL DEFAULT NULL COMMENT '到站id',
  `end_station_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '到站名称',
  `consignor` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '托货人',
  `consignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收货人',
  `product_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '品名',
  `train_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '车型',
  `project_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '方案编号',
  `loading_line` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '装车路线',
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '任务描述',
  `person_ids` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '拍摄人员id集合,逗号分隔',
  `person_names` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '拍摄人员name集合,逗号分隔',
  `continue_shot` bigint(32) NULL DEFAULT 0 COMMENT '为0时不是续拍，不为0时为续拍，存放上次任务ID',
  `order_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `order_end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `order_state` int(4) NULL DEFAULT 1 COMMENT '任务状态:0已拍照，1未拍照，',
  `del_state` int(2) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建用户',
  `modify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改用户',
  `upload_way` int(2) NULL DEFAULT 0 COMMENT '0:新建任务上传，1:拍照人员主动上传',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '任务表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for tbl_person
-- ----------------------------
DROP TABLE IF EXISTS `tbl_person`;
CREATE TABLE `tbl_person`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `login_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录密码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电话',
  `station_id` bigint(32) NULL DEFAULT NULL COMMENT '站点id',
  `station_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '站点名称',
  `del_state` int(2) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建用户',
  `modify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `登录名不能重复`(`login_name`) USING BTREE COMMENT '登录名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '拍照人员表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for tbl_picture
-- ----------------------------
DROP TABLE IF EXISTS `tbl_picture`;
CREATE TABLE `tbl_picture`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `order_id` bigint(32) NULL DEFAULT NULL COMMENT '任务id',
  `train_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '车厢号',
  `person_id` bigint(32) NULL DEFAULT NULL COMMENT '拍照人Id',
  `thum_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '略缩图路径地址',
  `url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '原图地址',
  `del_state` int(2) NULL DEFAULT 0 COMMENT '是否删除 0 未删除，1已删除',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建用户',
  `modify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '照片表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for tbl_station
-- ----------------------------
DROP TABLE IF EXISTS `tbl_station`;
CREATE TABLE `tbl_station`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '站点名称',
  `parent_id` bigint(32) NULL DEFAULT NULL COMMENT '父级id, 顶级parent_id:0',
  `del_state` int(2) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建用户',
  `modify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '站点表' ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;
