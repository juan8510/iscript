package com.lezo.iscript.service.crawler.dto;

import java.util.Date;

import com.lezo.iscript.utils.PriceUtils;

import lombok.Data;

@Data
public class MatchDto {
	private Long id;
	private Integer wareType;
	private Long wareCode;
	private Long similarCode;
	private Integer siteId;
	private Integer shopId;
	private String productCode;
	private String productName;
	private String productUrl;
	private Long productPrice;
	private String barCode;
	private String imgUrl;
	private String tokenBrand;
	private String tokenGategory;
	private String tokenVary;
	private String deciderKvs;
	private Integer arbiterId;
	private Integer similarScore;
	private String caption;
	private Integer confirmModel;
	private Integer isStandard;
	private Integer isDelete;
	private Date createTime;
	private Date updateTime;

}