package com.fastretailing.marketingpf.services.builders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster.BASIC_SEGMENT_ITEM;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForSqlMaster;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.INPUT_TYPE;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SegmentQueryBuilderTest {

    @Nested
    public class BuildTest {

        @Test
        public void testBuild() {
            Segment segment = new Segment();
            segment.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            segment.setEventTargetPeriodStartDate(LocalDate.of(2020, 1, 2));
            segment.setEventTargetPeriodEndDate(LocalDate.of(2022, 3, 4));
            segment.setAdditionalConditionLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());

            SegmentItemForSqlMaster s1 = new SegmentItemForSqlMaster();
            s1.setQueryForSegment("SELECT uid from ${ PROJECT_MD1 } where col1 ${ operator } (${ value })");
            SegmentItemForScreenMaster scr1 = new SegmentItemForScreenMaster();
            scr1.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c1 = new SegmentCondition();
            c1.setSegmentItemForSqlMaster(s1);
            c1.setSegmentItemForScreenMaster(scr1);
            c1.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c1.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c1.setSegmentConditionBlockOrder(1);
            c1.setComparisonValue("[\"1\",\"2\",\"3\"]");

            SegmentItemForSqlMaster s2 = new SegmentItemForSqlMaster();
            s2.setQueryForSegment("SELECT uid from ${ PROJECT_MD2 } where col2 ${ operator } ${ value }");
            SegmentItemForScreenMaster scr2 = new SegmentItemForScreenMaster();
            scr2.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c2 = new SegmentCondition();
            c2.setSegmentItemForSqlMaster(s2);
            c2.setSegmentItemForScreenMaster(scr2);
            c2.setOperatorType(OPERATOR_TYPE.EQUAL.getValueAsString());
            c2.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c2.setSegmentConditionBlockOrder(1);
            c2.setComparisonValue("[\"123\"]");

            SegmentItemForSqlMaster s3 = new SegmentItemForSqlMaster();
            s3.setQueryForSegment("SELECT uid from ${ PROJECT_MD3 } where col3 ${ operator } '%${ value }%'");
            SegmentItemForScreenMaster scr3 = new SegmentItemForScreenMaster();
            scr3.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c3 = new SegmentCondition();
            c3.setSegmentItemForSqlMaster(s3);
            c3.setSegmentItemForScreenMaster(scr3);
            c3.setOperatorType(OPERATOR_TYPE.LIKE.getValueAsString());
            c3.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            c3.setSegmentConditionBlockOrder(2);
            c3.setComparisonValue("[\"hoge\"]");

            SegmentItemForSqlMaster s4 = new SegmentItemForSqlMaster();
            s4.setQueryForSegment("SELECT uid from ${ PROJECT_MD4 } where col4 >= '${ eventTargetPeriodStartDate }' AND ${ operator }  ${ value }");
            SegmentItemForScreenMaster scr4 = new SegmentItemForScreenMaster();
            scr4.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c4 = new SegmentCondition();
            c4.setSegmentItemForSqlMaster(s4);
            c4.setSegmentItemForScreenMaster(scr4);
            c4.setOperatorType(OPERATOR_TYPE.GREATER_OR_EQUAL.getValueAsString());
            c4.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            c4.setSegmentConditionBlockOrder(2);
            c4.setComparisonValue("[\"1.6\"]");

            List<SegmentCondition> segmentConditionList = Arrays.asList(c1, c2, c3, c4);
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("PROJECT_MD1", "t1");
            paramMap.put("PROJECT_MD2", "t2");
            paramMap.put("PROJECT_MD3", "t3");
            paramMap.put("PROJECT_MD4", "t4");
            SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, segmentConditionList, paramMap);
            assertThat(builder.build()).isEqualTo("((SELECT uid from t1 where col1 IN ('1', '2', '3')) INTERSECT DISTINCT (SELECT uid from t2 where col2 = 123)) "
                    + "INTERSECT DISTINCT "
                    + "((SELECT uid from t3 where col3 LIKE '%hoge%') UNION DISTINCT (SELECT uid from t4 where col4 >= '20200102' AND >=  1.6))");

            SegmentItemForSqlMaster s0 = new SegmentItemForSqlMaster();
            s0.setQueryForSegment("SELECT uid from t0 where col0 ${ operator } (${ value })");
            SegmentItemForScreenMaster scr0 = new SegmentItemForScreenMaster();
            scr0.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c0 = new SegmentCondition();
            c0.setSegmentItemForSqlMaster(s0);
            c0.setSegmentItemForScreenMaster(scr0);
            c0.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c0.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c0.setSegmentConditionBlockOrder(0);
            c0.setComparisonValue("[\"0\"]");

            SegmentItemForSqlMaster s5 = new SegmentItemForSqlMaster();
            s5.setQueryForSegment("SELECT uid from t5 where col5 ${ operator } (${ value })");
            SegmentItemForScreenMaster scr5 = new SegmentItemForScreenMaster();
            scr5.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c5 = new SegmentCondition();
            c5.setSegmentItemForSqlMaster(s5);
            c5.setSegmentItemForScreenMaster(scr5);
            c5.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            c5.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c5.setSegmentConditionBlockOrder(1);
            c5.setComparisonValue("[\"5\"]");

            SegmentItemForSqlMaster s6 = new SegmentItemForSqlMaster();
            s6.setQueryForSegment("SELECT uid from t6 where col6 ${ operator } ${ value }");
            SegmentItemForScreenMaster scr6 = new SegmentItemForScreenMaster();
            scr6.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c6 = new SegmentCondition();
            c6.setSegmentItemForSqlMaster(s6);
            c6.setSegmentItemForScreenMaster(scr6);
            c6.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            c6.setOperatorType(OPERATOR_TYPE.EQUAL.getValueAsString());
            c6.setSegmentConditionBlockOrder(1);
            c6.setComparisonValue("[\"6\"]");

            SegmentItemForSqlMaster s7 = new SegmentItemForSqlMaster();
            s7.setQueryForSegment("SELECT uid from t7 where col7 ${ operator } ${ value }");
            SegmentItemForScreenMaster scr7 = new SegmentItemForScreenMaster();
            scr7.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c7 = new SegmentCondition();
            c7.setSegmentItemForSqlMaster(s7);
            c7.setSegmentItemForScreenMaster(scr7);
            c7.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c7.setOperatorType(OPERATOR_TYPE.NOT_EQUAL.getValueAsString());
            c7.setSegmentConditionBlockOrder(2);
            c7.setComparisonValue("[\"7\"]");

            assertThat(new SegmentQueryBuilder(segment, Arrays.asList(c0), paramMap).build()).isEqualTo("SELECT uid from t0 where col0 IN ('0')");
            assertThat(new SegmentQueryBuilder(segment, Arrays.asList(c5), paramMap).build()).isEqualTo("SELECT uid from t5 where col5 IN ('5')");
            assertThat(new SegmentQueryBuilder(segment, Arrays.asList(c5, c0, c6), paramMap).build()).isEqualTo(
                    "(SELECT uid from t0 where col0 IN ('0')) INTERSECT DISTINCT ((SELECT uid from t5 where col5 IN ('5')) UNION DISTINCT (SELECT uid from t6 where col6 = 6))");
            assertThat(new SegmentQueryBuilder(segment, Arrays.asList(c5, c0, c6, c7), paramMap).build()).isEqualTo(
                    "(SELECT uid from t0 where col0 IN ('0')) INTERSECT DISTINCT (((SELECT uid from t5 where col5 IN ('5')) UNION DISTINCT (SELECT uid from t6 where col6 = 6)) INTERSECT DISTINCT (SELECT uid from t7 where col7 != 7))");
        }

        @Test
        public void testBuild_WithBasicItem() {
            Segment segment = new Segment();
            segment.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            segment.setEventTargetPeriodStartDate(LocalDate.of(2020, 1, 2));
            segment.setEventTargetPeriodEndDate(LocalDate.of(2022, 3, 4));
            segment.setAdditionalConditionLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());

            SegmentItemForSqlMaster s1 = new SegmentItemForSqlMaster();
            s1.setQueryForSegment(
                    "SELECT DISTINCT p.uid FROM fr-${ ENV }-mdb.pii_${ ENV }_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty <![CDATA[>=]]> 1 AND p.mmbr_id IS NOT NULL $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$ AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.dept_elmnt.id IN ( ${ value } )");
            SegmentItemForScreenMaster scr1 = new SegmentItemForScreenMaster();
            scr1.setSegmentItemForScreenSequence(BASIC_SEGMENT_ITEM.DEPT_ELMNT.getSegmentItemForScreenSequence());
            scr1.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_API_TEXT.getValueAsString());
            SegmentCondition c1 = new SegmentCondition();
            c1.setSegmentItemForScreenSequence(BASIC_SEGMENT_ITEM.DEPT_ELMNT.getSegmentItemForScreenSequence());
            c1.setSegmentItemForSqlMaster(s1);
            c1.setSegmentItemForScreenMaster(scr1);
            c1.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c1.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c1.setSegmentConditionBlockOrder(0);
            c1.setComparisonValue("[\"1\",\"2\"]");

            SegmentItemForSqlMaster s2 = new SegmentItemForSqlMaster();
            s2.setQueryForSegment(
                    "SELECT DISTINCT p.uid FROM fr-${ ENV }-mdb.pii_${ ENV }_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty <![CDATA[>=]]> 1 AND p.mmbr_id IS NOT NULL $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$ AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.g_dept_elmnt.id IN ( ${ value } )");
            SegmentItemForScreenMaster scr2 = new SegmentItemForScreenMaster();
            scr2.setSegmentItemForScreenSequence(BASIC_SEGMENT_ITEM.G_DEPT_ELMNT.getSegmentItemForScreenSequence());
            scr2.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_API_TEXT.getValueAsString());
            SegmentCondition c2 = new SegmentCondition();
            c2.setSegmentItemForScreenSequence(BASIC_SEGMENT_ITEM.G_DEPT_ELMNT.getSegmentItemForScreenSequence());
            c2.setSegmentItemForSqlMaster(s2);
            c2.setSegmentItemForScreenMaster(scr2);
            c2.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c2.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c2.setSegmentConditionBlockOrder(0);
            c2.setComparisonValue("[\"21\",\"22\"]");

            SegmentItemForSqlMaster s3 = new SegmentItemForSqlMaster();
            s3.setQueryForSegment(
                    "SELECT DISTINCT p.uid FROM fr-${ ENV }-mdb.pii_${ ENV }_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty <![CDATA[>=]]> 1 AND p.mmbr_id IS NOT NULL $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$ AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.itm_name LIKE '%${ value }%'");
            SegmentItemForScreenMaster scr3 = new SegmentItemForScreenMaster();
            scr3.setSegmentItemForScreenSequence(BASIC_SEGMENT_ITEM.ITM_NAME_INCLUDE.getSegmentItemForScreenSequence());
            scr3.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c3 = new SegmentCondition();
            c3.setSegmentItemForScreenSequence(BASIC_SEGMENT_ITEM.ITM_NAME_INCLUDE.getSegmentItemForScreenSequence());
            c3.setSegmentItemForSqlMaster(s3);
            c3.setSegmentItemForScreenMaster(scr3);
            c3.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c3.setOperatorType(OPERATOR_TYPE.LIKE.getValueAsString());
            c3.setSegmentConditionBlockOrder(0);
            c3.setComparisonValue("[\"エアリズム\"]");

            SegmentItemForSqlMaster s4 = new SegmentItemForSqlMaster();
            s4.setQueryForSegment(
                    "SELECT DISTINCT p.uid FROM fr-${ ENV }-mdb.pii_${ ENV }_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty <![CDATA[>=]]> 1 AND p.mmbr_id IS NOT NULL $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$ AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.itm_name NOT LIKE '%${ value }%'");
            SegmentItemForScreenMaster scr4 = new SegmentItemForScreenMaster();
            scr4.setSegmentItemForScreenSequence(BASIC_SEGMENT_ITEM.ITM_NAME_EXCLUDE.getSegmentItemForScreenSequence());
            scr4.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition c4 = new SegmentCondition();
            c4.setSegmentItemForScreenSequence(BASIC_SEGMENT_ITEM.ITM_NAME_EXCLUDE.getSegmentItemForScreenSequence());
            c4.setSegmentItemForSqlMaster(s4);
            c4.setSegmentItemForScreenMaster(scr4);
            c4.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c4.setOperatorType(OPERATOR_TYPE.NOT_LIKE.getValueAsString());
            c4.setSegmentConditionBlockOrder(0);
            c4.setComparisonValue("[\"インナー\"]");

            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("ENV", "env");
            paramMap.put("DATASET_GA_UQJP", "dataset_ga_uqjp");
            paramMap.put("DATASET_GA_GUJP", "dataset_ga_gujp");
            paramMap.put("DATASET_GA_UQJP_APP", "dataset_ga_uqjp_app");
            {
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, Arrays.asList(c1, c2, c3, c4), paramMap);
                assertThat(builder.build()).isEqualTo(""
                        + "(SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.dept_elmnt.id IN ( '1', '2' )) INTERSECT DISTINCT (SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.g_dept_elmnt.id IN ( '21', '22' )) INTERSECT DISTINCT (SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.itm_name LIKE '%エアリズム%') INTERSECT DISTINCT (SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.itm_name NOT LIKE '%インナー%')");
            }
            SegmentItemForSqlMaster s1000 = new SegmentItemForSqlMaster();
            s1000.setQueryForSegment("SELECT DISTINCT uid FROM fr-${ ENV }-mdb.${DATASET_GA_UQJP}.hoge WHERE 1=1 $$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_GA$$ $$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_PH$$ AND mmbr_id  ${ operator } (${ value })");
            SegmentItemForScreenMaster scr1000 = new SegmentItemForScreenMaster();
            scr1000.setSegmentItemForScreenSequence(1000L);
            scr1000.setInputType(INPUT_TYPE.SINGLE_SELECTION_API_TEXT.getValueAsString());
            SegmentCondition c1000 = new SegmentCondition();
            c1000.setSegmentItemForScreenSequence(1000L);
            c1000.setSegmentItemForSqlMaster(s1000);
            c1000.setSegmentItemForScreenMaster(scr1000);
            c1000.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c1000.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c1000.setSegmentConditionBlockOrder(1);
            c1000.setComparisonValue("[\"12345\",\"67890\"]");
            {
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, Arrays.asList(c1000), paramMap);
                assertThat(builder.build()).isEqualTo("SELECT DISTINCT uid FROM fr-env-mdb.dataset_ga_uqjp.hoge WHERE 1=1   AND mmbr_id  IN ('12345', '67890')");
            }
            {
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, Arrays.asList(c1, c1000), paramMap);
                assertThat(builder.build()).isEqualTo("(SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.dept_elmnt.id IN ( '1', '2' )) "
                        + "INTERSECT DISTINCT (SELECT DISTINCT uid FROM fr-env-mdb.dataset_ga_uqjp.hoge "
                        + "WHERE 1=1 AND c.dept_elmnt.id IN ( '1', '2' ) AND p.dept_elmnt.id IN ( '1', '2' ) AND mmbr_id  IN ('12345', '67890'))");
            }
            {
                s1000.setQueryForSegment("SELECT DISTINCT uid FROM fr-${ ENV }-mdb.${DATASET_GA_UQJP}.hoge WHERE 1=1 $$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_GA$$ $$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_PH$$ AND mmbr_id  ${ operator } (${ value })");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, Arrays.asList(c2, c1000), paramMap);
                assertThat(builder.build()).isEqualTo("(SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.g_dept_elmnt.id IN ( '21', '22' )) "
                        + "INTERSECT DISTINCT (SELECT DISTINCT uid FROM fr-env-mdb.dataset_ga_uqjp.hoge "
                        + "WHERE 1=1 AND c.g_dept_elmnt.id IN ( '21', '22' ) AND p.g_dept_elmnt.id IN ( '21', '22' ) AND mmbr_id  IN ('12345', '67890'))");
            }
            {
                s1000.setQueryForSegment("SELECT DISTINCT uid FROM fr-${ ENV }-mdb.${DATASET_GA_UQJP}.hoge WHERE 1=1 $$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_GA$$ $$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_PH$$ AND mmbr_id  ${ operator } (${ value })");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, Arrays.asList(c3, c1000), paramMap);
                assertThat(builder.build()).isEqualTo("(SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.itm_name LIKE '%エアリズム%') "
                        + "INTERSECT DISTINCT (SELECT DISTINCT uid FROM fr-env-mdb.dataset_ga_uqjp.hoge "
                        + "WHERE 1=1 AND c.itm_name LIKE '%エアリズム%' AND p.itm_name LIKE '%エアリズム%' AND mmbr_id  IN ('12345', '67890'))");
            }
            {
                s1000.setQueryForSegment("SELECT DISTINCT uid FROM fr-${ ENV }-mdb.${DATASET_GA_UQJP}.hoge WHERE 1=1 $$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_GA$$ $$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_PH$$ AND mmbr_id  ${ operator } (${ value })");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, Arrays.asList(c4, c1000), paramMap);
                assertThat(builder.build()).isEqualTo("(SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.itm_name NOT LIKE '%インナー%') "
                        + "INTERSECT DISTINCT (SELECT DISTINCT uid FROM fr-env-mdb.dataset_ga_uqjp.hoge "
                        + "WHERE 1=1 AND c.itm_name NOT LIKE '%インナー%' AND p.itm_name NOT LIKE '%インナー%' AND mmbr_id  IN ('12345', '67890'))");
            }

            Segment segment2 = new Segment();
            segment2.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            segment2.setEventTargetPeriodStartDate(LocalDate.of(2020, 1, 2));
            segment2.setEventTargetPeriodEndDate(LocalDate.of(2022, 3, 4));
            segment2.setAdditionalConditionLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());

            SegmentItemForSqlMaster s1001 = new SegmentItemForSqlMaster();
            s1001.setQueryForSegment("SELECT DISTINCT uid FROM hoge WHERE mmbr_id  ${ operator } (${ value })");
            SegmentItemForScreenMaster scr1001 = new SegmentItemForScreenMaster();
            scr1001.setSegmentItemForScreenSequence(1001L);
            scr1001.setInputType(INPUT_TYPE.SINGLE_SELECTION_API_TEXT.getValueAsString());
            SegmentCondition c1001 = new SegmentCondition();
            c1001.setSegmentItemForScreenSequence(1001L);
            c1001.setSegmentItemForSqlMaster(s1001);
            c1001.setSegmentItemForScreenMaster(scr1001);
            c1001.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            c1001.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c1001.setSegmentConditionBlockOrder(1);
            c1001.setComparisonValue("[\"bob\",\"mary\"]");

            SegmentItemForSqlMaster s1002 = new SegmentItemForSqlMaster();
            s1002.setQueryForSegment("SELECT DISTINCT uid FROM fuga WHERE mmbr_id  ${ operator } (${ value })");
            SegmentItemForScreenMaster scr1002 = new SegmentItemForScreenMaster();
            scr1002.setSegmentItemForScreenSequence(1002L);
            scr1002.setInputType(INPUT_TYPE.SINGLE_SELECTION_API_TEXT.getValueAsString());
            SegmentCondition c1002 = new SegmentCondition();
            c1002.setSegmentItemForScreenSequence(1002L);
            c1002.setSegmentItemForSqlMaster(s1002);
            c1002.setSegmentItemForScreenMaster(scr1002);
            c1002.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            c1002.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c1002.setSegmentConditionBlockOrder(1);
            c1002.setComparisonValue("[\"john\",\"hary\"]");

            {
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment2, Arrays.asList(c1, c2, c1001, c1002), paramMap);
                assertThat(builder.build()).isEqualTo("((SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.dept_elmnt.id IN ( '1', '2' )) INTERSECT DISTINCT (SELECT DISTINCT p.uid FROM fr-env-mdb.pii_env_uqjp.dmp_vw_t_prchs_hist_item_ctlg_uqjp AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                        + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20200102') AS TIMESTAMP)\n"
                        + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                        + " \n"
                        + " \n"
                        + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.g_dept_elmnt.id IN ( '21', '22' ))) INTERSECT DISTINCT ((SELECT DISTINCT uid FROM hoge WHERE mmbr_id  IN ('bob', 'mary')) UNION DISTINCT (SELECT DISTINCT uid FROM fuga WHERE mmbr_id  IN ('john', 'hary')))");
            }
        }
    }

    @Nested
    public class BuildBlockQueryTest {

        @Test
        public void testBuildBlockQuery() {
            Segment segment = new Segment();
            segment.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
            SegmentItemForSqlMaster s1 = new SegmentItemForSqlMaster();
            s1.setQueryForSegment("(SELECT 'u2' AS uid) INTERSECT DISTINCT (SELECT 'u2' AS uid)");
            SegmentItemForScreenMaster scr1 = new SegmentItemForScreenMaster();
            scr1.setInputType(INPUT_TYPE.TEXT_BOX_NUMERICAL_VALUE.getValueAsString());
            SegmentCondition c1 = new SegmentCondition();
            c1.setSegmentItemForSqlMaster(s1);
            c1.setSegmentItemForScreenMaster(scr1);
            c1.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c1.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c1.setComparisonValue("[1]");

            SegmentItemForSqlMaster s2 = new SegmentItemForSqlMaster();
            s2.setQueryForSegment("(SELECT 'u1' AS uid) UNION DISTINCT (SELECT 'u2' AS uid) UNION DISTINCT (SELECT 'u3' AS uid)");
            SegmentItemForScreenMaster scr2 = new SegmentItemForScreenMaster();
            scr2.setInputType(INPUT_TYPE.TEXT_BOX_NUMERICAL_VALUE.getValueAsString());
            SegmentCondition c2 = new SegmentCondition();
            c2.setSegmentItemForSqlMaster(s2);
            c2.setSegmentItemForScreenMaster(scr2);
            c2.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c2.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c2.setComparisonValue("[2]");

            SegmentItemForSqlMaster s3 = new SegmentItemForSqlMaster();
            s3.setQueryForSegment("(SELECT 'u3' AS uid) INTERSECT DISTINCT (SELECT 'u3' AS uid)");
            SegmentItemForScreenMaster scr3 = new SegmentItemForScreenMaster();
            scr3.setInputType(INPUT_TYPE.TEXT_BOX_NUMERICAL_VALUE.getValueAsString());
            SegmentCondition c3 = new SegmentCondition();
            c3.setSegmentItemForSqlMaster(s3);
            c3.setSegmentItemForScreenMaster(scr3);
            c3.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c3.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            c3.setComparisonValue("[3]");

            List<SegmentCondition> segmentConditionList = Arrays.asList(c1, c2, c3);
            assertThat(builder.buildBlockQuery(segmentConditionList)).isEqualTo(
                    "((SELECT 'u2' AS uid) INTERSECT DISTINCT (SELECT 'u2' AS uid)) INTERSECT DISTINCT ((SELECT 'u1' AS uid) UNION DISTINCT (SELECT 'u2' AS uid) UNION DISTINCT (SELECT 'u3' AS uid)) INTERSECT DISTINCT ((SELECT 'u3' AS uid) INTERSECT DISTINCT (SELECT 'u3' AS uid))");

            SegmentItemForSqlMaster s4 = new SegmentItemForSqlMaster();
            s4.setQueryForSegment("SELECT 'u4' AS uid");
            SegmentItemForScreenMaster scr4 = new SegmentItemForScreenMaster();
            scr4.setInputType(INPUT_TYPE.TEXT_BOX_NUMERICAL_VALUE.getValueAsString());
            SegmentCondition c4 = new SegmentCondition();
            c4.setSegmentItemForSqlMaster(s4);
            c4.setSegmentItemForScreenMaster(scr4);
            c4.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            c4.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c4.setComparisonValue("[4]");

            try {
                List<SegmentCondition> notGoodList = Arrays.asList(c1, c4);
                builder.buildBlockQuery(notGoodList);
                fail();
            } catch (RuntimeException e) {

            } catch (Exception e) {
                fail(e);
            }

            SegmentItemForSqlMaster s5 = new SegmentItemForSqlMaster();
            s5.setQueryForSegment("SELECT 'u5' AS uid");
            SegmentItemForScreenMaster scr5 = new SegmentItemForScreenMaster();
            scr5.setInputType(INPUT_TYPE.TEXT_BOX_NUMERICAL_VALUE.getValueAsString());
            SegmentCondition c5 = new SegmentCondition();
            c5.setSegmentItemForSqlMaster(s5);
            c5.setSegmentItemForScreenMaster(scr5);
            c5.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            c5.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            c5.setComparisonValue("[4]");

            assertThat(builder.buildBlockQuery(Arrays.asList(c4, c5))).isEqualTo("(SELECT 'u4' AS uid) UNION DISTINCT (SELECT 'u5' AS uid)");
        }
    }

    @Nested
    public class BuildIntersectDistinctQueryTest {

        @Test
        public void testBuildIntersectDistinctQuery() {
            SegmentQueryBuilder builder = new SegmentQueryBuilder(null, new ArrayList<>(), new HashMap<String, String>());
            List<String> queryList = Arrays.asList("SELECT 'u1' AS uid", "SELECT 'u2' AS uid", "SELECT 'u3' AS uid");
            assertThat(builder.buildIntersectDistinctQuery(queryList))
                    .isEqualTo("(SELECT 'u1' AS uid) INTERSECT DISTINCT (SELECT 'u2' AS uid) INTERSECT DISTINCT (SELECT 'u3' AS uid)");
        }
    }

    @Nested
    public class BuildUnionDistinctQueryTest {

        @Test
        public void testBuildUnionDistinctQuery() {
            SegmentQueryBuilder builder = new SegmentQueryBuilder(null, new ArrayList<>(), new HashMap<String, String>());
            List<String> queryList = Arrays.asList("SELECT 'u1' AS uid", "SELECT 'u2' AS uid", "SELECT 'u3' AS uid");
            assertThat(builder.buildUnionDistinctQuery(queryList))
                    .isEqualTo("(SELECT 'u1' AS uid) UNION DISTINCT (SELECT 'u2' AS uid) UNION DISTINCT (SELECT 'u3' AS uid)");
        }
    }

    @Nested
    public class BuildConditionQueryTest {

        @Test
        public void testBuildConditionQuery() {
            Segment segment = new Segment();
            segment.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            segment.setEventTargetPeriodStartDate(LocalDate.of(2020, 1, 2));
            segment.setEventTargetPeriodEndDate(LocalDate.of(2022, 3, 4));

            SegmentItemForSqlMaster segmentItemForSql = new SegmentItemForSqlMaster();
            segmentItemForSql.setQueryForSegment("SELECT uid\n"
                    + "FROM ${ PROJECT_MDB }.${ DATASET_MDB }.vw_m_acnt_jp\n"
                    + "WHERE 1=1\n"
                    + "AND (chnl_clsfctn_key in ('uqapp-jp', 'uqline-jp') OR mmbr_id LIKE '70%')\n"
                    + "$$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$"
                    + "AND gndr_cd ${ operator } (${ value })\n");
            SegmentItemForScreenMaster segmentItemForScreen = new SegmentItemForScreenMaster();
            segmentItemForScreen.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_API_TEXT.getValueAsString());

            SegmentCondition segmentCondition = new SegmentCondition();
            segmentCondition.setSegmentItemForSqlMaster(segmentItemForSql);
            segmentCondition.setSegmentItemForScreenMaster(segmentItemForScreen);
            segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.OR.getValueAsString());
            segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            segmentCondition.setComparisonValue("[\"hoge\",\"fuga\"]");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("PROJECT_MDB", "hoge");
            paramMap.put("DATASET_MDB", "fuga");
            SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), paramMap);
            assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid\n"
                    + "FROM hoge.fuga.vw_m_acnt_jp\n"
                    + "WHERE 1=1\n"
                    + "AND (chnl_clsfctn_key in ('uqapp-jp', 'uqline-jp') OR mmbr_id LIKE '70%')\n"
                    + " \n"
                    + "    AND _TABLE_SUFFIX BETWEEN '20200102'\n"
                    + "    AND '20220304'\n"
                    + " \n"
                    + " \n"
                    + "AND gndr_cd IN ('hoge', 'fuga')");
        }

        @Test
        public void testSingleTextValue() {
            Segment segment = new Segment();
            segment.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            SegmentItemForSqlMaster segmentItemForSql = new SegmentItemForSqlMaster();
            segmentItemForSql.setQueryForSegment("SELECT uid FROM table WHERE text_column ${ operator } '${ value }'");
            SegmentItemForScreenMaster segmentItemForScreen = new SegmentItemForScreenMaster();
            segmentItemForScreen.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            SegmentCondition segmentCondition = new SegmentCondition();
            segmentCondition.setSegmentItemForSqlMaster(segmentItemForSql);
            segmentCondition.setSegmentItemForScreenMaster(segmentItemForScreen);
            segmentCondition.setOperatorType(OPERATOR_TYPE.EQUAL.getValueAsString());
            {
                segmentCondition.setComparisonValue("[\"hoge\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE text_column = 'hoge'");
            }
            {
                segmentCondition.setComparisonValue("[\"ho'ge\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE text_column = 'ho\\'ge'");
            }
            {
                segmentCondition.setComparisonValue("[\"ho\\\\g\'e\"]"); // ["ho\\g'e"]
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE text_column = 'ho\\\\g\\'e'");
            }
            {
                segmentCondition.setComparisonValue("[\"~!@#$%^&*()_+;\',.\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE text_column = '~!@#$%^&*()_+;\\',.'");
            }
        }

        @Test
        public void testSingleNumericValue() {
            Segment segment = new Segment();
            segment.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            SegmentItemForSqlMaster segmentItemForSql = new SegmentItemForSqlMaster();
            segmentItemForSql.setQueryForSegment("SELECT uid FROM table WHERE numeric_column ${ operator } ${ value }");
            SegmentItemForScreenMaster segmentItemForScreen = new SegmentItemForScreenMaster();
            segmentItemForScreen.setInputType(INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE.getValueAsString());
            SegmentCondition segmentCondition = new SegmentCondition();
            segmentCondition.setSegmentItemForSqlMaster(segmentItemForSql);
            segmentCondition.setSegmentItemForScreenMaster(segmentItemForScreen);
            segmentCondition.setOperatorType(OPERATOR_TYPE.EQUAL.getValueAsString());
            {
                segmentCondition.setComparisonValue("[\"12.34\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE numeric_column = 12.34");
            }
            {
                segmentCondition.setComparisonValue("[\"12.34\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE numeric_column = 12.34");
            }
        }

        @Test
        public void testMultipleNumericValue() {
            Segment segment = new Segment();
            segment.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            SegmentItemForSqlMaster segmentItemForSql = new SegmentItemForSqlMaster();
            segmentItemForSql.setQueryForSegment("SELECT uid FROM table WHERE numeric_column ${ operator } (${ value })");
            SegmentItemForScreenMaster segmentItemForScreen = new SegmentItemForScreenMaster();
            segmentItemForScreen.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_API_NUMERICAL_VALUE.getValueAsString());
            SegmentCondition segmentCondition = new SegmentCondition();
            segmentCondition.setSegmentItemForSqlMaster(segmentItemForSql);
            segmentCondition.setSegmentItemForScreenMaster(segmentItemForScreen);
            segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            {
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue("[\"12\", \"34\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE numeric_column IN (12, 34)");
            }
            {
                segmentCondition.setOperatorType(OPERATOR_TYPE.NOT_IN.getValueAsString());
                segmentCondition.setComparisonValue("[\"12.34\", \"56\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE numeric_column NOT IN (12.34, 56)");
            }
            {
                segmentCondition.setOperatorType(OPERATOR_TYPE.EQUAL.getValueAsString());
                segmentCondition.setComparisonValue("[\"12.34\", \"56\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                try {
                    builder.buildConditionQuery(segmentCondition);
                    fail();
                } catch (RuntimeException e) {
                } catch (Exception e) {
                    fail();
                }
            }
        }

        @Test
        public void testMultipleTextValue() {
            Segment segment = new Segment();
            segment.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            SegmentItemForSqlMaster segmentItemForSql = new SegmentItemForSqlMaster();
            segmentItemForSql.setQueryForSegment("SELECT uid FROM table WHERE text_column ${ operator } (${ value })");
            SegmentItemForScreenMaster segmentItemForScreen = new SegmentItemForScreenMaster();
            segmentItemForScreen.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_API_TEXT.getValueAsString());
            SegmentCondition segmentCondition = new SegmentCondition();
            segmentCondition.setSegmentItemForSqlMaster(segmentItemForSql);
            segmentCondition.setSegmentItemForScreenMaster(segmentItemForScreen);
            segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            {
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue("[\"fuga\", \"34\", \"h'o\\\\g\\\"e\"]");
                SegmentQueryBuilder builder = new SegmentQueryBuilder(segment, new ArrayList<>(), new HashMap<String, String>());
                assertThat(builder.buildConditionQuery(segmentCondition)).isEqualTo("SELECT uid FROM table WHERE text_column IN ('fuga', '34', 'h\\'o\\\\g\"e')");
            }
        }
    }


    @Nested
    public class FormatValueTest {

        @Test
        public void testFormatValue() {
            SegmentQueryBuilder builder = new SegmentQueryBuilder(new Segment(), new ArrayList<>(), new HashMap<String, String>());
            try {
                builder.formatValue(INPUT_TYPE.MULTIPLE_SELECTION_TEXT, OPERATOR_TYPE.IN, Arrays.asList());
                fail();
            } catch (RuntimeException e) {
            } catch (Exception e) {
                fail();
            }
            assertThat(builder.formatValue(INPUT_TYPE.MULTIPLE_SELECTION_TEXT, OPERATOR_TYPE.IN, Arrays.asList("'hoge'", "\\tfuga"))).isEqualTo("'\\'hoge\\'', '\\\\tfuga'");
            assertThat(builder.formatValue(INPUT_TYPE.MULTIPLE_SELECTION_NUMERICAL_VALUE, OPERATOR_TYPE.NOT_IN, Arrays.asList("1234", "56.78"))).isEqualTo("1234, 56.78");
            try {
                builder.formatValue(INPUT_TYPE.TEXT_BOX_TEXT, OPERATOR_TYPE.EQUAL, Arrays.asList("hoge", "fuga"));
                fail();
            } catch (RuntimeException e) {
            } catch (Exception e) {
                fail();
            }
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_API_TEXT, OPERATOR_TYPE.LIKE, Arrays.asList("ho'ge fu%ga pi_yo"))).isEqualTo("ho\\'ge fu\\\\%ga pi\\\\_yo");
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_API_TEXT, OPERATOR_TYPE.NOT_LIKE, Arrays.asList("%_'\\"))).isEqualTo("\\\\%\\\\_\\'\\\\\\\\");

            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_API_TEXT, OPERATOR_TYPE.EQUAL, Arrays.asList("年'月\\日"))).isEqualTo("年\\\'月\\\\日");
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_TEXT, OPERATOR_TYPE.NOT_EQUAL, Arrays.asList("NULL"))).isEqualTo("NULL");

            // This is valid but the final query may not work
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_TEXT, OPERATOR_TYPE.LESS, Arrays.asList("hoge"))).isEqualTo("hoge");

            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_API_NUMERICAL_VALUE, OPERATOR_TYPE.EQUAL, Arrays.asList("12.34"))).isEqualTo("12.34");
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE, OPERATOR_TYPE.NOT_EQUAL, Arrays.asList("5678"))).isEqualTo("5678");
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_API_NUMERICAL_VALUE, OPERATOR_TYPE.LESS, Arrays.asList("12.34"))).isEqualTo("12.34");
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_API_NUMERICAL_VALUE, OPERATOR_TYPE.LESS_OR_EQUAL, Arrays.asList("12.34"))).isEqualTo("12.34");
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE, OPERATOR_TYPE.GREATER, Arrays.asList("5678"))).isEqualTo("5678");
            assertThat(builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE, OPERATOR_TYPE.GREATER_OR_EQUAL, Arrays.asList("5678"))).isEqualTo("5678");
            try {
                builder.formatValue(INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE, OPERATOR_TYPE.GREATER_OR_EQUAL, Arrays.asList("not a num"));
                fail();
            } catch (RuntimeException e) {
            } catch (Exception e) {
                fail();
            }
        }
    }

    @Nested
    public class FormatDateValueYyyyMmDdTest {

        @Test
        public void testFormatDateValue() {
            SegmentQueryBuilder builder = new SegmentQueryBuilder(new Segment(), new ArrayList<>(), new HashMap<String, String>());
            assertThat(builder.formatDateValueYyyyMmDd(LocalDate.of(2021, 1, 2))).isEqualTo("20210102");
            assertThat(builder.formatDateValueYyyyMmDd(null)).isEqualTo("");
        }
    }

    @Nested
    public class FormatNumericValueTest {

        @Test
        public void testFormatNumericValue() {
            SegmentQueryBuilder builder = new SegmentQueryBuilder(new Segment(), new ArrayList<>(), new HashMap<String, String>());
            assertThat(builder.formatNumericValue("1")).isEqualTo("1");
            assertThat(builder.formatNumericValue("1   ")).isEqualTo("1");
            assertThat(builder.formatNumericValue("12.34")).isEqualTo("12.34");
            assertThat(builder.formatNumericValue("4E-45")).isEqualTo("4E-45");
            assertThat(builder.formatNumericValue("-11")).isEqualTo("-11");
            try {
                assertThat(builder.formatNumericValue(" 1 OR 1 = 1")).isEqualTo("-11");
                fail();
            } catch (RuntimeException e) {
            } catch (Exception e) {
                fail();
            }
        }
    }

    @Nested
    public class EscapeStringValueTest {

        @Test
        public void testEscapeStringValue() {
            SegmentQueryBuilder builder = new SegmentQueryBuilder(new Segment(), new ArrayList<>(), new HashMap<String, String>());
            assertThat(builder.escapeStringValue("")).isEqualTo("");
            assertThat(builder.escapeStringValue("hoge")).isEqualTo("hoge");
            assertThat(builder.escapeStringValue("ho\\ge")).isEqualTo("ho\\\\ge");
            assertThat(builder.escapeStringValue("ho'ge")).isEqualTo("ho\\'ge");
            assertThat(builder.escapeStringValue("ho\\'ge")).isEqualTo("ho\\\\\\'ge");// ho\\\'ge
            assertThat(builder.escapeStringValue("' OR 1 = 1")).isEqualTo("\\' OR 1 = 1");
            assertThat(builder.escapeStringValue("ho%ge fu'ga pi\\yo pi_po")).isEqualTo("ho%ge fu\\'ga pi\\\\yo pi_po");
        }
    }

    @Nested
    public class EscapeLikeValueTest {

        @Test
        public void testEscapeLikeValue() {
            SegmentQueryBuilder builder = new SegmentQueryBuilder(new Segment(), new ArrayList<>(), new HashMap<String, String>());
            assertThat(builder.escapeLikeValue("")).isEqualTo("");
            assertThat(builder.escapeLikeValue("hoge")).isEqualTo("hoge");
            assertThat(builder.escapeLikeValue("ho%ge")).isEqualTo("ho\\\\%ge");
            assertThat(builder.escapeLikeValue("ho_ge")).isEqualTo("ho\\\\_ge");
            assertThat(builder.escapeLikeValue("ho\\ge")).isEqualTo("ho\\\\\\\\ge");
            assertThat(builder.escapeLikeValue("hog'e")).isEqualTo("hog\\'e");
            assertThat(builder.escapeLikeValue("h'o\\%ge")).isEqualTo("h\\'o\\\\\\\\\\\\%ge");
            assertThat(builder.escapeLikeValue("ho%ge fu'ga pi\\yo pi_po")).isEqualTo("ho\\\\%ge fu\\'ga pi\\\\\\\\yo pi\\\\_po");
        }
    }
}
