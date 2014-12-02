  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `data_date` date NOT NULL DEFAULT '0000-00-00' COMMENT '数据日期',
  `category_name` varchar(100) NOT NULL DEFAULT '' COMMENT '档期分类',
  `brand_name` varchar(100) NOT NULL COMMENT '档期名称',
  `pt_brand_name` varchar(100) NOT NULL COMMENT '品牌',
  `sell_date_start` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上档时间',
  `sell_date_end` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '下档时间',
  `business_owner` varchar(100)  COMMENT '商务负责人',
  `pricing_owner` varchar(100)  COMMENT '比价负责人',
  `site_id` int(10) NOT NULL DEFAULT '1111' COMMENT '对手站点编号',
  `vcode_count` int(10) NOT NULL DEFAULT '0' COMMENT '档期下所有商品数',
  `vmatch_count` int(10) NOT NULL DEFAULT '0' COMMENT 'VIP匹配数',
  `vwarn_count` int(10) NOT NULL DEFAULT '0' COMMENT 'VIP价格异常商品数',
  `ocode_count` int(10) NOT NULL DEFAULT '0' COMMENT '对手商品数',
  `oupdate_count` int(10) NOT NULL DEFAULT '0' COMMENT '已更新商品数',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '0-done,1-abort',
  `is_handle` tinyint NOT NULL DEFAULT '0' COMMENT '0-未处理,1-已处理',
  `oupdate_time` datetime  COMMENT '对手价格更新时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',