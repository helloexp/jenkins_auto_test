package com.fastretailing.marketingpf.services.builders;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster.BASIC_SEGMENT_ITEM;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TemplateQueryBuilderTest {

    @Nested
    public class BuildTemplateQueryTest {

        @Test
        public void testBuildTemplateQuery() {
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM ${ PROJECT_MDB }.${ DATASET_MDB }.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND (chnl_clsfctn_key in ('uqapp-jp', 'uqline-jp') OR mmbr_id LIKE '70%')\n"
                        + "$$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("PROJECT_MDB", "hoge");
                parameters.put("DATASET_MDB", "fuga");
                parameters.put("eventTargetPeriodType", EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
                parameters.put("eventTargetPeriodStartDate", "2020-01-02");
                parameters.put("eventTargetPeriodEndDate", "2020-11-12");
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND (chnl_clsfctn_key in ('uqapp-jp', 'uqline-jp') OR mmbr_id LIKE '70%')\n"
                        + " \n"
                        + "    AND _TABLE_SUFFIX BETWEEN '2020-01-02'\n"
                        + "    AND '2020-11-12'\n"
                        + " \n"
                        + " \n"
                        + "AND gndr_cd = 22");

                parameters.put("eventTargetPeriodType", EVENT_TARGET_PERIOD_TYPE.RELATIVE_DATE.getValueAsString());
                parameters.put("eventTargetPeriodRelativePeriodUnit", "DAY");
                parameters.put("eventTargetPeriodRelativeNumberValue", "10");
                TemplateQueryBuilder builder2 = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder2.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND (chnl_clsfctn_key in ('uqapp-jp', 'uqline-jp') OR mmbr_id LIKE '70%')\n"
                        + " \n"
                        + " \n"
                        + "    AND _TABLE_SUFFIX BETWEEN CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -10 DAY) AS STRING FORMAT 'YYYYMMDD' ) \n"
                        + "    AND CAST(CURRENT_DATE('Asia/Tokyo') AS STRING FORMAT 'YYYYMMDD' )\n"
                        + " \n"
                        + "AND gndr_cd = 22");
            }
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM ${ PROJECT_MDB }.${ DATASET_MDB }.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND (chnl_clsfctn_key in ('uqapp-jp', 'uqline-jp') OR mmbr_id LIKE '70%')\n"
                        + "$$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("PROJECT_MDB", "hoge");
                parameters.put("DATASET_MDB", "fuga");
                parameters.put("eventTargetPeriodType", EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
                parameters.put("eventTargetPeriodStartDate", "20200102");
                parameters.put("eventTargetPeriodEndDate", "20201112");
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND (chnl_clsfctn_key in ('uqapp-jp', 'uqline-jp') OR mmbr_id LIKE '70%')\n"
                        + " \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20201112') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + "AND gndr_cd = 22");

                parameters.put("eventTargetPeriodType", EVENT_TARGET_PERIOD_TYPE.RELATIVE_DATE.getValueAsString());
                parameters.put("eventTargetPeriodRelativePeriodUnit", "MONTH");
                parameters.put("eventTargetPeriodRelativeNumberValue", "10");
                TemplateQueryBuilder builder2 = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder2.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND (chnl_clsfctn_key in ('uqapp-jp', 'uqline-jp') OR mmbr_id LIKE '70%')\n"
                        + " \n"
                        + " \n"
                        + "    AND p.trn_dtime  >=  CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -10 MONTH) AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(CURRENT_DATE('Asia/Tokyo') AS TIMESTAMP)\n"
                        + " \n"
                        + "AND gndr_cd = 22");
            }
        }

        @Test
        public void testBuildTemplateQuery_BasicSegmentItem() {
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "$$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_GA$$\n"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "\n"
                        + "AND gndr_cd = 22");

                parameters.put(BASIC_SEGMENT_ITEM.DEPT_ELMNT.getValueReplaceKey(), "'1', '2'");
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND c.dept_elmnt.id IN ( '1', '2' )\n"
                        + "AND gndr_cd = 22");
            }
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "$$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_PH$$\n"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "\n"
                        + "AND gndr_cd = 22");

                parameters.put(BASIC_SEGMENT_ITEM.DEPT_ELMNT.getValueReplaceKey(), "'1', '2'");
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND p.dept_elmnt.id IN ( '1', '2' )\n"
                        + "AND gndr_cd = 22");
            }
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "$$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_GA$$\n"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "\n"
                        + "AND gndr_cd = 22");

                parameters.put(BASIC_SEGMENT_ITEM.G_DEPT_ELMNT.getValueReplaceKey(), "'21', '22'");
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND c.g_dept_elmnt.id IN ( '21', '22' )\n"
                        + "AND gndr_cd = 22");
            }
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "$$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_PH$$\n"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "\n"
                        + "AND gndr_cd = 22");

                parameters.put(BASIC_SEGMENT_ITEM.G_DEPT_ELMNT.getValueReplaceKey(), "'21', '22'");
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND p.g_dept_elmnt.id IN ( '21', '22' )\n"
                        + "AND gndr_cd = 22");
            }
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "$$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_GA$$\n"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "\n"
                        + "AND gndr_cd = 22");

                parameters.put(BASIC_SEGMENT_ITEM.ITM_NAME_INCLUDE.getValueReplaceKey(), "エアリズム");
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND c.itm_name LIKE '%エアリズム%'\n"
                        + "AND gndr_cd = 22");
            }
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "$$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_PH$$\n"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "\n"
                        + "AND gndr_cd = 22");

                parameters.put(BASIC_SEGMENT_ITEM.ITM_NAME_INCLUDE.getValueReplaceKey(), "エアリズム");
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND p.itm_name LIKE '%エアリズム%'\n"
                        + "AND gndr_cd = 22");
            }
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "$$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_GA$$\n"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "\n"
                        + "AND gndr_cd = 22");

                parameters.put(BASIC_SEGMENT_ITEM.ITM_NAME_EXCLUDE.getValueReplaceKey(), "インナー");
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND c.itm_name NOT LIKE '%インナー%'\n"
                        + "AND gndr_cd = 22");
            }
            {
                String conditionQuery = "SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "$$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_PH$$\n"
                        + "AND gndr_cd ${ operator } ${ value }";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "22");

                TemplateQueryBuilder builder = new TemplateQueryBuilder(conditionQuery, parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "\n"
                        + "AND gndr_cd = 22");

                parameters.put(BASIC_SEGMENT_ITEM.ITM_NAME_EXCLUDE.getValueReplaceKey(), "インナー");
                assertThat(builder.build()).isEqualTo("SELECT uid\n"
                        + "FROM hoge.fuga.vw_m_acnt_jp\n"
                        + "WHERE 1=1\n"
                        + "AND p.itm_name NOT LIKE '%インナー%'\n"
                        + "AND gndr_cd = 22");
            }
        }

        @Test
        public void testOperators() {
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.EQUAL.getName());
                parameters.put("value", "fuga");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } '${ value }'", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column = 'fuga'");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.NOT_EQUAL.getName());
                parameters.put("value", "fuga");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } '${ value }'", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column != 'fuga'");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.IN.getName());
                parameters.put("value", "1,2,3");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } (${ value })", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column IN (1,2,3)");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.NOT_IN.getName());
                parameters.put("value", "1,2,3");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } (${ value })", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column NOT IN (1,2,3)");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.LIKE.getName());
                parameters.put("value", "fuga");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } '%${ value }%'", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column LIKE '%fuga%'");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.NOT_LIKE.getName());
                parameters.put("value", "fuga");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } '%${ value }%'", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column NOT LIKE '%fuga%'");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.GREATER_OR_EQUAL.getName());
                parameters.put("value", "12.34");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } ${ value }", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column >= 12.34");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.GREATER.getName());
                parameters.put("value", "1234");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } ${ value }", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column > 1234");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.LESS_OR_EQUAL.getName());
                parameters.put("value", "12.34");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } ${ value }", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column <= 12.34");
            }
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("operator", OPERATOR_TYPE.LESS.getName());
                parameters.put("value", "1234");
                TemplateQueryBuilder builder = new TemplateQueryBuilder("SELECT uid FROM table WHERE text_column ${ operator } ${ value }", parameters);
                assertThat(builder.build()).isEqualTo("SELECT uid FROM table WHERE text_column < 1234");
            }
        }
    }
}
