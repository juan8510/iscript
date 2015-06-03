package com.lezo.iscript.service.crawler.dto;

import java.util.Date;

public class ProductDto {
	private Long id;
	private Integer shopId;
	private String productCode;
	private String productName;
	private Float marketPrice;
	private String productUrl;
	private String productBrand;
	private String productModel;
	private String productAttr;
	private String barCode;
	private String imgUrl;
	private String unionUrl;
	private Date onsailTime;
	private Date createTime;
	private Date updateTime;

	private Integer siteId;
	private String categoryNav;
	private String tokenBrand;
	private String tokenCategory;
	
	private String spuCodes;
	private String spuVary;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProductAttr() {
		return productAttr;
	}

	public void setProductAttr(String productAttr) {
		this.productAttr = productAttr;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getOnsailTime() {
		return onsailTime;
	}

	public void setOnsailTime(Date onsailTime) {
		if (onsailTime != null && onsailTime.getTime() > 0) {
			this.onsailTime = onsailTime;
		}
	}

	public Float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getUnionUrl() {
		return unionUrl;
	}

	public void setUnionUrl(String unionUrl) {
		this.unionUrl = unionUrl;
	}

	public String getCategoryNav() {
		return categoryNav;
	}

	public void setCategoryNav(String categoryNav) {
		this.categoryNav = categoryNav;
	}

	public String getTokenBrand() {
		return tokenBrand;
	}

	public void setTokenBrand(String tokenBrand) {
		this.tokenBrand = tokenBrand;
	}

	public String getTokenCategory() {
		return tokenCategory;
	}

	public void setTokenCategory(String tokenCategory) {
		this.tokenCategory = tokenCategory;
	}

	public String getSpuCodes() {
		return spuCodes;
	}

	public void setSpuCodes(String spuCodes) {
		this.spuCodes = spuCodes;
	}

	public String getSpuVary() {
		return spuVary;
	}

	public void setSpuVary(String spuVary) {
		this.spuVary = spuVary;
	}
}
