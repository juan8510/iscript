package com.lezo.iscript.service.crawler.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezo.iscript.common.BaseDao;
import com.lezo.iscript.service.crawler.dto.ListRankDto;

public interface ListRankDao extends BaseDao<ListRankDto> {

	List<ListRankDto> getListRankDtos(@Param(value = "categoryName") String categoryName,
			@Param(value = "codeList") List<String> codeList, @Param(value = "shopId") Integer shopId);

	void deleteListRanks(@Param(value = "categoryName") String categoryName,
			@Param(value = "codeList") List<String> codeList, @Param(value = "shopId") Integer shopId);
}
