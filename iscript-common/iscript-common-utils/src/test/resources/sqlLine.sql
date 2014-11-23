  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QUERY_WORD` varchar(50) NOT NULL default '' COMMENT '关键词',
  `QUERY_SOLR` varchar(200) NOT NULL default '{}' COMMENT 'SOLR语句',
  `QUERY_RESULT` varchar(2000)  COMMENT '查询结果',
  `QUERY_COST` int  COMMENT '查询耗时',
  `QUERY_HIT` int NOT NULL default '0' COMMENT '命中次数',
  `STATUS` tinyint NOT NULL default '0' COMMENT '0-新建，1-查询中，2-查询完成，-1-失败,-2-失效',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
