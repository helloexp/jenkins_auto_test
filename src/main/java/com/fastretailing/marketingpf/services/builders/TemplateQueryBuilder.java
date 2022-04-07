package com.fastretailing.marketingpf.services.builders;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster.BASIC_SEGMENT_ITEM;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateQueryBuilder {

    private static final Logger logger = LoggerFactory.getLogger(TemplateQueryBuilder.class);

    private String originalQuery;
    private Map<String, String> paramMap;

    public TemplateQueryBuilder(String originalQuery, Map<String, String> paramMap) {
        this.originalQuery = originalQuery;
        this.paramMap = paramMap;
    }

    /**
     * Process template query<br>
     * - Replace variable string found in variableMap<br>
     * - Fill appropriate parameters with paramMap<br>
     *
     * @return Plain query string
     */
    public String build() {
        String query = originalQuery;
        for (Entry<String, String> entry : getReplaceMap().entrySet()) {
            query = query.replace(entry.getKey(), entry.getValue());
        }
        String queryTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">" +
                "<mapper namespace=\"segment_query\">" +
                "    <select id=\"sub_condition_query\">" + query + "</select>" +
                "</mapper>";
        logger.debug("queryTemplate = {}", queryTemplate);
        logger.debug("paramMap = {}", paramMap);
        try (InputStream is = Resources.getResourceAsStream("mybatis-segment-query-builder-config.xml");
                InputStream xmlIs = new ByteArrayInputStream(queryTemplate.getBytes())) {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            Configuration configuration = factory.getConfiguration();
            XMLMapperBuilder builder = new XMLMapperBuilder(xmlIs, configuration, "segment_query", configuration.getSqlFragments());
            builder.parse();
            MappedStatement ms = factory.getConfiguration().getMappedStatement("segment_query.sub_condition_query");
            BoundSql boundSql = ms.getBoundSql(paramMap);
            return boundSql.getSql();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * These following value will be replace directly
     *
     * @return Map<String, String>
     */
    private Map<String, String> getReplaceMap() {
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("$$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$", ""
                + "<if test=\"eventTargetPeriodType == 1\">\n"
                + "    AND _TABLE_SUFFIX BETWEEN '${ eventTargetPeriodStartDate }'\n"
                + "    AND '${ eventTargetPeriodEndDate }'\n"
                + "</if>\n"
                + "<if test=\"eventTargetPeriodType == 2\">\n"
                + "    AND _TABLE_SUFFIX BETWEEN CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -${ eventTargetPeriodRelativeNumberValue } ${ eventTargetPeriodRelativePeriodUnit }) AS STRING FORMAT 'YYYYMMDD' ) \n"
                + "    AND CAST(CURRENT_DATE('Asia/Tokyo') AS STRING FORMAT 'YYYYMMDD' )\n"
                + "</if>\n");

        replaceMap.put("$$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$", ""
                + "<if test=\"eventTargetPeriodType == 1\">\n"
                + "    AND p.trn_dtime <![CDATA[>=]]> CAST(PARSE_DATE('%Y%m%d', '${ eventTargetPeriodStartDate }') AS TIMESTAMP)\n"
                + "    AND p.trn_dtime <![CDATA[<]]> CAST(PARSE_DATE('%Y%m%d', '${ eventTargetPeriodEndDate }') AS TIMESTAMP)\n"
                + "</if>\n"
                + "<if test=\"eventTargetPeriodType == 2\">\n"
                + "    AND p.trn_dtime <![CDATA[>=]]> CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -${ eventTargetPeriodRelativeNumberValue } ${ eventTargetPeriodRelativePeriodUnit }) AS TIMESTAMP)\n"
                + "    AND p.trn_dtime <![CDATA[<]]> CAST(CURRENT_DATE('Asia/Tokyo') AS TIMESTAMP)\n"
                + "</if>\n");

        if(this.paramMap.containsKey(BASIC_SEGMENT_ITEM.DEPT_ELMNT.getValueReplaceKey())) {
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_GA$$", "AND c.dept_elmnt.id IN ( ${ dept_elmnt_value } )");
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_PH$$", "AND p.dept_elmnt.id IN ( ${ dept_elmnt_value } )");
        } else {
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_GA$$", "");
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_PH$$", "");
        }

        if(this.paramMap.containsKey(BASIC_SEGMENT_ITEM.G_DEPT_ELMNT.getValueReplaceKey())) {
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_GA$$", "AND c.g_dept_elmnt.id IN ( ${ g_dept_elmnt_value } )");
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_PH$$", "AND p.g_dept_elmnt.id IN ( ${ g_dept_elmnt_value } )");
        } else {
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_GA$$", "");
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_PH$$", "");
        }

        if(this.paramMap.containsKey(BASIC_SEGMENT_ITEM.ITM_NAME_INCLUDE.getValueReplaceKey())) {
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_GA$$", "AND c.itm_name LIKE '%${ itm_name_like_value }%'");
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_PH$$", "AND p.itm_name LIKE '%${ itm_name_like_value }%'");
        } else {
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_GA$$", "");
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_PH$$", "");
        }

        if(this.paramMap.containsKey(BASIC_SEGMENT_ITEM.ITM_NAME_EXCLUDE.getValueReplaceKey())) {
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_GA$$", "AND c.itm_name NOT LIKE '%${ itm_name_not_like_value }%'");
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_PH$$", "AND p.itm_name NOT LIKE '%${ itm_name_not_like_value }%'");
        } else {
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_GA$$", "");
            replaceMap.put("$$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_PH$$", "");
        }

        return replaceMap;
    }
}
