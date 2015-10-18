package com.lezo.iscript.service.crawler.service.impl;

import java.util.Collections;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezo.iscript.service.crawler.dao.SimilarDao;
import com.lezo.iscript.service.crawler.dto.SimilarDto;
import com.lezo.iscript.service.crawler.service.SimilarService;
import com.lezo.iscript.utils.BatchIterator;

@Log4j
@Service
public class SimilarServiceImpl implements SimilarService {
    @Autowired
    private SimilarDao similarDao;

    @Override
    public void batchInsertSimilarDtos(List<SimilarDto> dtoList) {
        BatchIterator<SimilarDto> it = new BatchIterator<SimilarDto>(dtoList);
        while (it.hasNext()) {
            similarDao.batchInsert(it.next());
        }
    }

    @Override
    public void batchUpdateSimilarDtos(List<SimilarDto> dtoList) {
        BatchIterator<SimilarDto> it = new BatchIterator<SimilarDto>(dtoList);
        while (it.hasNext()) {
            similarDao.batchUpdate(it.next());
        }
    }

    public void setSimilarDao(SimilarDao similarDao) {
        this.similarDao = similarDao;
    }

    @Override
    public List<SimilarDto> getSimilarDtoByJobIds(List<Long> jobIds) {
        if (CollectionUtils.isEmpty(jobIds)) {
            return Collections.emptyList();
        }
        return similarDao.getSimilarDtoByJobIds(jobIds);
    }

    @Override
    public List<String> getBrands() {
        return similarDao.getBrands();
    }

    @Override
    public List<SimilarDto> getSimilarDtoByBrandAndId(String brand, Long fromId, int limit) {
        if (brand == null || limit < 1) {
            return Collections.emptyList();
        }
        return similarDao.getSimilarDtoByBrandAndId(brand, fromId, limit);
    }

    @Override
    public List<SimilarDto> getSimilarDtoByJobIdSiteId(String jobId, int siteId, Long fromId, int limit) {
        return similarDao.getSimilarDtoByJobIdSiteId(jobId, siteId, fromId, limit);
    }

}
