package com.lezo.iscript.service.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezo.iscript.service.crawler.dao.ProductDao;
import com.lezo.iscript.service.crawler.dto.ProductDto;
import com.lezo.iscript.service.crawler.service.ProductService;
import com.lezo.iscript.utils.BatchIterator;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;

	@Override
	public void batchInsertProductDtos(List<ProductDto> dtoList) {
		BatchIterator<ProductDto> it = new BatchIterator<ProductDto>(dtoList);
		while (it.hasNext()) {
			productDao.batchInsert(it.next());
		}
	}

	@Override
	public List<ProductDto> getProductDtos(List<String> codeList, Integer shopId, String siteCode) {
		List<ProductDto> dtoList = new ArrayList<ProductDto>();
		BatchIterator<String> it = new BatchIterator<String>(codeList);
		while (it.hasNext()) {
			List<ProductDto> subList = productDao.getProductDtos(it.next(), shopId, siteCode);
			if (CollectionUtils.isNotEmpty(subList)) {
				dtoList.addAll(subList);
			}
		}
		return dtoList;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

}