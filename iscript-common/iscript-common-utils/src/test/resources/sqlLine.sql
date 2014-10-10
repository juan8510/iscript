  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SITE_ID` varchar(50) COMMENT '站点ID',
  `PRODUCT_CODE` varchar(50)  COMMENT '商品编码',
  `PROMOTE_CODE` varchar(50)  COMMENT '促销编码',
  `PROMOTE_NAME` varchar(50) COMMENT '促销名称',
  `PROMOTE_DETAIL` varchar(1500) COMMENT '促销内容',
  `PROMOTE_NUMS` varchar(1000) COMMENT '促销数值',
  `PROMOTE_URL` varchar(1000) COMMENT '促销链接',
  `PROMOTE_TYPE` tinyint NOT NULL default '0' COMMENT '促销类型，0-满减,1-满赠，2-满折',
  `PROMOTE_STATUS` tinyint NOT NULL default '0' COMMENT '状态，-1-促销未开始,0-促销中，1-促销结束',
  `IS_DELETE` tinyint NOT NULL default '0' COMMENT '0-未删除,1-删除',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp