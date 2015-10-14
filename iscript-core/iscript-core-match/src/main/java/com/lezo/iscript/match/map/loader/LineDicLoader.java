package com.lezo.iscript.match.map.loader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import lombok.extern.log4j.Log4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lezo.iscript.match.map.SameChars;

@Log4j
public class LineDicLoader implements DicLoader {
    private static final Pattern CN_REG = Pattern.compile("[\u4e00-\u9fa5]+");
    private static final String encoding = "UTF-8";
    private static final Comparator<String> CMP_CN = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            boolean hasCn1 = CN_REG.matcher(o1).find();
            boolean hasCn2 = CN_REG.matcher(o2).find();
            if (hasCn1 && !hasCn2) {
                return -1;
            } else if (!hasCn1 && hasCn2) {
                return 1;
            }
            return o2.length() - o1.length();
        }
    };
    private int minLen;

    public LineDicLoader() {
        this(1);
    }
    public LineDicLoader(int minLen) {
        super();
        this.minLen = minLen;
    }

    @Override
    public Map<String, SameChars> loadDic(String dicPath) throws Exception {
        return loadDic(new FileInputStream(dicPath));
    }

    @Override
    public Map<String, SameChars> loadDic(InputStream in) throws Exception {
        InputStreamReader isr = null;
        BufferedReader bReader = null;
        try {
            isr = new InputStreamReader(in, encoding);
            bReader = new BufferedReader(isr);
            Map<String, SameChars> temMap = Maps.newHashMap();
            while (bReader.ready()) {
                String line = bReader.readLine();
                if (line == null) {
                    break;
                }
                Set<String> sameSet = toSameSet(line);
                SameChars sameChars = toSameChars(sameSet);
                for (String value : sameSet) {
                    SameChars hasChars = temMap.get(value);
                    if (hasChars != null) {
                        log.warn("duplicat unit:" + value);
                    } else {
                        temMap.put(value, sameChars);
                    }
                }
            }
            return temMap;
        } catch (IOException e) {
            log.error("load dic,cause:", e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(bReader);
        }
        return null;
    }

    private SameChars toSameChars(Set<String> sameSet) {
        List<String> sameList = Lists.newArrayList(sameSet);
        Collections.sort(sameList, CMP_CN);
        SameChars sameChars = new SameChars();
        sameChars.setValue(sameList.get(0));
        sameChars.setSameSet(sameSet);
        return sameChars;
    }

    private Set<String> toSameSet(String line) {
        if (StringUtils.isBlank(line)) {
            return Collections.emptySet();
        }
        StringTokenizer tokenizer = new StringTokenizer(line, "=");
        Set<String> newSet = Sets.newLinkedHashSet();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (StringUtils.isNotBlank(token) && token.length() >= minLen) {
                token = toUnify(token);
                newSet.add(token);
            }
        }
        return newSet;
    }

    private String toUnify(String token) {
        if (token == null) {
            return null;
        }
        return token.toLowerCase();
    }

}