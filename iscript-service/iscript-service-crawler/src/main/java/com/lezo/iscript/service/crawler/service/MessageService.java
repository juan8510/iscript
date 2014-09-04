package com.lezo.iscript.service.crawler.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezo.iscript.common.BaseService;
import com.lezo.iscript.service.crawler.dto.MessageDto;

public interface MessageService extends BaseService<MessageDto> {
	List<MessageDto> getMessageDtos(@Param(value = "type") String type, @Param(value = "status") Integer status,
			@Param(value = "limit") Integer limit);

	void batchUpdateStatus(@Param(value = "idList") List<Long> idList, @Param(value = "status") Integer status,
			@Param(value = "handler") String handler);
}
