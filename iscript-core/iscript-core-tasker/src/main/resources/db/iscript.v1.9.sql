ALTER TABLE T_MESSAGE
ADD COLUMN `DATA_BUCKET` varchar(10) NOT NULL DEFAULT '' COMMENT '数据桶',
ADD COLUMN `DATA_DOMAIN` varchar(20) NOT NULL DEFAULT '' COMMENT '数据域名',
ADD COLUMN `DATA_COUNT` int NOT NULL DEFAULT '0' COMMENT '数据量';