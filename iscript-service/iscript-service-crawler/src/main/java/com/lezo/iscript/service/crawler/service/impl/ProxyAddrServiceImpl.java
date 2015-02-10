package com.lezo.iscript.service.crawler.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezo.iscript.service.crawler.dao.ProxyAddrDao;
import com.lezo.iscript.service.crawler.dto.ProxyAddrDto;
import com.lezo.iscript.service.crawler.service.ProxyAddrService;
import com.lezo.iscript.utils.BatchIterator;
import com.lezo.iscript.utils.ProxyUtils;

@Service
public class ProxyAddrServiceImpl implements ProxyAddrService {
	@Autowired
	private ProxyAddrDao proxyAddrDao;

	@Override
	public synchronized void batchInsertProxyAddrs(List<ProxyAddrDto> dtoList) {
		ensureAddrCodeFilled(dtoList);
		dtoList = doFilterByPort(dtoList);
		BatchIterator<ProxyAddrDto> it = new BatchIterator<ProxyAddrDto>(dtoList);
		while (it.hasNext()) {
			proxyAddrDao.batchInsert(it.next());
		}

	}

	private List<ProxyAddrDto> doFilterByPort(List<ProxyAddrDto> dataList) {
		List<ProxyAddrDto> destList = new ArrayList<ProxyAddrDto>(dataList);
		for (ProxyAddrDto addrDto : dataList) {
			if (!ProxyUtils.isPort(addrDto.getPort())) {
				continue;
			}
			destList.add(addrDto);
		}
		return destList;
	}

	@Override
	public synchronized void batchUpdateProxyAddrs(List<ProxyAddrDto> dtoList) {
		ensureAddrCodeFilled(dtoList);
		BatchIterator<ProxyAddrDto> it = new BatchIterator<ProxyAddrDto>(dtoList);
		while (it.hasNext()) {
			proxyAddrDao.batchUpdate(it.next());
		}
	}

	public void setProxyAddrDao(ProxyAddrDao proxyAddrDao) {
		this.proxyAddrDao = proxyAddrDao;
	}

	@Override
	public synchronized void batchSaveProxyAddrs(List<ProxyAddrDto> dtoList) {
		if (CollectionUtils.isEmpty(dtoList)) {
			return;
		}
		ensureAddrCodeFilled(dtoList);
		List<ProxyAddrDto> insertList = new ArrayList<ProxyAddrDto>();
		List<ProxyAddrDto> updateList = new ArrayList<ProxyAddrDto>();
		doAssortment(dtoList, insertList, updateList);
		batchInsertProxyAddrs(insertList);
		batchUpdateProxyAddrs(updateList);
	}

	private void ensureAddrCodeFilled(List<ProxyAddrDto> dtoList) {
		if (CollectionUtils.isEmpty(dtoList)) {
			return;
		}
		if (StringUtils.isNotEmpty(dtoList.get(0).getAddrCode())) {
			return;
		}
		for (ProxyAddrDto dto : dtoList) {
			String addrCode = getDtoKey(dto);
			dto.setAddrCode(addrCode);
		}
	}

	private String getDtoKey(ProxyAddrDto dto) {
		return dto.getIp() + "" + dto.getPort();
	}

	private void doAssortment(List<ProxyAddrDto> dtoList, List<ProxyAddrDto> insertList, List<ProxyAddrDto> updateList) {
		Map<String, ProxyAddrDto> newDtoMap = new HashMap<String, ProxyAddrDto>();
		for (ProxyAddrDto dto : dtoList) {
			newDtoMap.put(dto.getAddrCode(), dto);
		}
		List<String> addrCodeList = new ArrayList<String>(newDtoMap.keySet());
		List<ProxyAddrDto> hasDtoList = getProxyAddrDtosByAddrCodes(addrCodeList);
		Set<String> hasKeySet = new HashSet<String>();
		for (ProxyAddrDto oldDto : hasDtoList) {
			hasKeySet.add(oldDto.getAddrCode());
			ProxyAddrDto newDto = newDtoMap.get(oldDto.getAddrCode());
			if (newDto != null) {
				keepValidValue(oldDto, newDto);
				updateList.add(newDto);
			}
		}
		for (Entry<String, ProxyAddrDto> entry : newDtoMap.entrySet()) {
			if (hasKeySet.contains(entry.getKey())) {
				continue;
			}
			insertList.add(entry.getValue());
		}
	}

	private void keepValidValue(ProxyAddrDto oldDto, ProxyAddrDto newDto) {
		newDto.setId(oldDto.getId());
		if (StringUtils.isEmpty(newDto.getRegionName())) {
			newDto.setRegionName(oldDto.getRegionName());
		}
		if (StringUtils.isEmpty(newDto.getIspName())) {
			newDto.setIspName(oldDto.getIspName());
		}
		if (StringUtils.isEmpty(newDto.getRemark())) {
			newDto.setRemark(oldDto.getRemark());
		}
		if (newDto.getType() == null || newDto.getType() == ProxyAddrDto.TYPE_UNKNOWN) {
			newDto.setType(oldDto.getType());
		}
		if (newDto.getSeedId() == null || newDto.getSeedId().equals(0L)) {
			newDto.setSeedId(oldDto.getSeedId());
		}
	}

	@Override
	public List<ProxyAddrDto> getProxyAddrDtosByAddrCodes(List<String> addrCodeList) {
		if (CollectionUtils.isEmpty(addrCodeList)) {
			return Collections.emptyList();
		}
		List<ProxyAddrDto> resultList = new ArrayList<ProxyAddrDto>();
		BatchIterator<String> it = new BatchIterator<String>(addrCodeList, 500);
		while (it.hasNext()) {
			List<ProxyAddrDto> hasList = proxyAddrDao.getProxyAddrDtosByAddrCodes(it.next());
			resultList.addAll(hasList);
		}
		return resultList;
	}

	@Override
	public List<ProxyAddrDto> getNullRegionProxyAddrDtos(Integer type) {
		return proxyAddrDao.getNullRegionProxyAddrDtos(type);
	}

	@Override
	public List<ProxyAddrDto> getProxyAddrDtosByCreateTime(Date afterTime) {
		return proxyAddrDao.getProxyAddrDtosByCreateTime(afterTime);
	}

}
