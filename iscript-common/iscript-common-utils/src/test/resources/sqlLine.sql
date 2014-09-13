  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CATEGORY_NAME` varchar(100) COMMENT '目录名称',
  `LIST_URL` varchar(1000)  COMMENT '列表URL',
  `SHOP_ID` int NOT NULL COMMENT '店铺ID',
  `PRODUCT_CODE` varchar(50)  NOT NULL COMMENT '商品编号',
  `PRODUCT_NAME` varchar(1000) NOT NULL COMMENT '商品名称',
  `PRODUCT_PRICE` float(12,2) COMMENT '商品价格',
  `PRODUCT_URL` varchar(1000) COMMENT '商品链接',
  `SORT_TYPE` tinyint NOT NULL DEfault '0' COMMENT '排序类型',
  `SORT_RANK` int  NOT NULL DEfault '0' COMMENT '商品链接',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp
 `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(50) COMMENT '消息类型',
  `MESSAGE` varchar(1000)  COMMENT '消息内容',
  `SOURCE` varchar(50) COMMENT '消息来源',
  `HANDLER` varchar(100) COMMENT '处理者',
  `STATUS` tinyint NOT NULL DEfault '0' COMMENT '状态',
  `CREATE_TIME` timestamp NULL  COMMENT '创建时间',
  `UPDATE_TIME` timestamp































































































