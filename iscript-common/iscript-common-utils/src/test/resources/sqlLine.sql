  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(50) default NULL COMMENT '消息名称',
  `MESSAGE` varchar(2000) default NULL COMMENT '消息内容',
  `SOURCE` varchar(50) default NULL COMMENT '消息来源',
  `REMARK` varchar(100) default NULL COMMENT '备注信息',
  `STATUS` tinyint(4) NOT NULL default '0' COMMENT '状态',
  `SORT_CODE` tinyint(4) NOT NULL default '0' COMMENT '类型编码',
  `CREATE_TIME` timestamp NULL default NULL COMMENT '创建时间',
  `UPDATE_TIME` timestamp NOT NULL default '0000-00-00 00:00:00' on update CURRENT_TIMESTAMP COMMENT '更新时间',
  `DATA_BUCKET` varchar(10) NOT NULL default '' COMMENT '数据桶',
  `DATA_DOMAIN` varchar(20) NOT NULL default '' COMMENT '数据域名',
  `DATA_COUNT` int(11) NOT NULL default '0' COMMENT '数据量',