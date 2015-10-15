package com.lezo.iscript.match.algorithm.analyse;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lezo.iscript.match.algorithm.IAnalyser;
import com.lezo.iscript.match.map.BrandMapper;
import com.lezo.iscript.match.map.SameChars;
import com.lezo.iscript.match.pojo.CellAssort;
import com.lezo.iscript.match.pojo.CellStat;
import com.lezo.iscript.match.pojo.CellToken;
import com.lezo.iscript.match.utils.CellAssortUtils;

@Log4j
public class BrandAnalyser implements IAnalyser {

    @Override
    public CellAssort analyse(List<CellToken> tokens) {
        CellAssort assort = new CellAssort();
        assort.setName(NAME_BRAND);
        if (CollectionUtils.isEmpty(tokens)) {
            return assort;
        }
        BrandMapper mapper = BrandMapper.getInstance();
        Map<SameChars, CellStat> cellStatMap = Maps.newHashMap();
        for (CellToken cell : tokens) {
            SameChars sameChars = mapper.getSameSet(cell.getValue());
            if (sameChars == null) {
                continue;
            }
            CellStat cellStat = cellStatMap.get(sameChars);
            if (cellStat == null) {
                cellStat = new CellStat();
                List<CellToken> sameCells = Lists.newArrayList();
                cellStat.setTokens(sameCells);
                cellStatMap.put(sameChars, cellStat);
            }
            cellStat.getTokens().add(cell);
        }
        for (CellStat cellStat : cellStatMap.values()) {
            doStatistic(cellStat);
        }
        if (!cellStatMap.isEmpty()) {
            List<CellStat> stats = Lists.newArrayList(cellStatMap.values());
            assort.setStats(stats);
            CellAssortUtils.doAnalyse(assort);
        }
        return assort;
    }

    private void doStatistic(CellStat cellStat) {
        if (cellStat == null || cellStat.getTokens() == null) {
            return;
        }
        CellToken headToken = null;
        int len = 0;
        Set<String> tokenSet = Sets.newHashSet();
        for (CellToken token : cellStat.getTokens()) {
            if (headToken == null || headToken.getIndex() > token.getIndex()) {
                headToken = token;
            }
            tokenSet.add(token.getValue());
            len += token.getValue().length();
        }
        cellStat.setValue(headToken);
        cellStat.setLength(len);
        cellStat.setCount(tokenSet.size());
    }

}
