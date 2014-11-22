  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MESSAGE` varchar(1000) COMMENT '概况信息',
  `STATUS` tinyint NOT NULL default '0' COMMENT '0-创建中，1-创建结束，-1-创建失败',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
