DROP TABLES IF EXISTS T_PRODUCT_SUMMARY;
CREATE TABLE `T_PRODUCT_SUMMARY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SITE_ID` varchar(50) COMMENT '站点ID',
  `SHOP_ID` varchar(50) COMMENT '店铺ID',
  `PRODUCT_CODE` varchar(50)  COMMENT '商品编码',
  `PRICE_RISE` float(12,2) COMMENT '价格增长率 ',
  `SALE_NUM_RISE` float(12,2) COMMENT '销量增长率',
  `COMMENT_NUM_RISE` float(12,2) COMMENT '评论增长率',
  `GOOD_COMMENT_RISE` float(12,2) COMMENT '好评增长率',
  `POOR_COMMENT_RISE` float(12,2) COMMENT '差评增长率',
  `DATA_CONTENT` varchar(2000) COMMENT '计算数据',
  `IS_DELETE`  tinyint NOT NULL default '0' COMMENT '删除，0-FALSE,1-TRUE',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_T_PRODUCT_SUMMARY_CODE_SITE` (`SITE_ID`,`PRODUCT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '产品因素汇总表';

DROP TABLES IF EXISTS T_PRODUCT_TRUST;
CREATE TABLE `T_PRODUCT_TRUST` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SITE_ID` varchar(50) COMMENT '站点ID',
  `PRODUCT_CODE` varchar(50)  COMMENT '商品编码',
  `IS_PROMOTE_FAKE` tinyint NOT NULL default '0' COMMENT '假促销，0-FALSE,1-TRUE',
  `IS_DELETE` tinyint NOT NULL default '0' COMMENT '0-未删除,1-删除',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_T_PRODUCT_SUMMARY_CODE_SITE` (`SITE_ID`,`PRODUCT_CODE`),
  KEY `IDX_T_PRODUCT_SUMMARY_MCODE_SITE` (`SITE_ID`,`PROMOTE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '促销产品映射表';


DROP TABLES IF EXISTS T_LUCENE_INDEX;
CREATE TABLE `T_LUCENE_INDEX` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MESSAGE` varchar(1000) COMMENT '索引信息',
  `DATA_COUNT` int  COMMENT '数据量',
  `DATA_DAY` date NULL  COMMENT '数据日期',
  `RETRY` tinyint NOT NULL default '0' COMMENT '重试次数',
  `STATUS` tinyint NOT NULL default '0' COMMENT '0-创建中，1-创建结束，-1-创建失败',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_T_LUCENE_INDEX_CREATE_TIME` (`CREATE_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'LUCENE索引表';

DROP TABLES IF EXISTS T_SEARCH_HIS;
CREATE TABLE `T_SEARCH_HIS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QUERY_WORD` varchar(50) NOT NULL default '' COMMENT '关键词',
  `QUERY_SOLR` varchar(200) NOT NULL default '{}' COMMENT 'SOLR语句',
  `QUERY_RESULT` text  COMMENT '查询结果',
  `QUERY_COST` int  COMMENT '查询耗时',
  `QUERY_HIT` int NOT NULL default '0' COMMENT '命中次数',
  `STATUS` tinyint NOT NULL default '0' COMMENT '0-新建，1-查询中，2-查询完成，-1-失败,-2-失效',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `T_SEARCH_HIS_QUERY_SOLR` (`QUERY_SOLR`(50))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '搜索历史';



