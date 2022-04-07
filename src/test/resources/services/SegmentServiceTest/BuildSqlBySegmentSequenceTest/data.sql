TRUNCATE TABLE t_sgmt;

INSERT INTO t_sgmt
(sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, "desc", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (2101, '1', '090', '1', '202201', '1', '2021-01-01', '2021-01-02', null, null, '1', 'segment_01', 'desc_01', '0', '', '1', '{}'::json, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(2201, '1', '090', '1', '202201', '1', '2021-01-01', '2021-01-02', null, null, '1', 'segment_02', 'desc_02', '0', '', '1', '{}'::json, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(2301, '1', '090', '1', '202201', '2', null, null, 3, '1', '1', 'segment_03', 'desc_04', '0', '', '1', '{}'::json, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(2401, '1', '090', '1', '202201', '1', '2021-01-01', '2021-01-02', null, null, '1', 'segment_04', 'desc_04', '0', '', '1', '{}'::json, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(2501, '1', '090', '1', '202201', '1', '2022-01-02', '2022-03-04', 3, '1', '1', 'segment_05', 'desc_05', '0', '', '1', '{}'::json, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(2601, '1', '090', '1', '202201', '1', '2022-01-02', '2022-03-04', 3, '1', '1', 'segment_05', 'desc_05', '0', '', '1', '{}'::json, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(2701, '1', '090', '1', '202207', '2', null, null, 3, '1', '1', 'segment_07', 'desc_07', '0', '', '1', '{}'::json, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(2801, '1', '090', '1', '202208', '2', null, null, 3, '1', '1', 'segment_08', 'desc_08', '0', '', '1', '{}'::json, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
;

TRUNCATE TABLE t_sgmt_cond;

INSERT INTO t_sgmt_cond
(sgmt_cond_seq, sgmt_seq, sgmt_itm_f_scrn_seq, sgmt_itm_f_sql_seq, oprtr_type, cmprsn_val, sgmt_cond_blok_ordr, lgcl_oprtr_type, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (1100, 2101, 3100, 4100, '1', '["dummy"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01') /*This has no m_sgmt_itm_f_sql*/
,(1101, 2101, 3101, 4101, '1', '["val''01"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1102, 2101, 3102, 4102, '2', '["1234"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1105, 2101, 3105, 4105, '5', '["ho%ge fu_ga pi\\yo"]', 3, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1103, 2101, 3103, 4103, '3', '["option1", "option2"]', 2, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1104, 2101, 3104, 4104, '4', '["1234", "56.78"]', 2, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(1301, 2301, 3301, 4301, '6', '["ho%ge fu_ga pi\\yo"]', 1, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1302, 2301, 3302, 4302, '6', '["ho%ge fu_ga pi\\yo"]', 1, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(1401, 2401, 3401, 4401, '7', '["12.34"]', 2, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1402, 2401, 3402, 4402, '8', '["5678"]', 1, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(1501, 2501, 3501, 4501, '9', '["12.34"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1502, 2501, 3502, 4502, '10', '["5678"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(1601, 2601, 3601, 4601, '1', '["123"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1602, 2601, 3602, 4602, '1', '["456"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1603, 2601, 3603, 4603, '1', '["789"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(1701, 2701, 3701, 4701, '3', '["1", "2"]', 0, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(1801, 2801, 1, 4801, '3', '["1", "2"]', 0, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1802, 2801, 2, 4802, '3', '["21", "22"]', 0, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1803, 2801, 3, 4803, '5', '["エアリズ"]', 0, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1804, 2801, 4, 4804, '6', '["インナー"]', 0, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1805, 2801, 1000, 4805, '5', '["hoge"]', 1, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(1806, 2801, 1001, 4806, '2', '["hoge"]', 1, '2', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')


;


TRUNCATE TABLE m_sgmt_itm_f_scrn;

INSERT INTO m_sgmt_itm_f_scrn (sgmt_itm_f_scrn_seq, ctgry, dtl_ctgry, sgmt_itm_name, inpt_type, oprtr_list, chc_list, url_f_api_aces, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (3101, 'cat1', 'dcat1', 'sin1', '1', '[1,2]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3102, 'cat1', 'dcat1', 'sin2', '2', '[1,2]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3103, 'cat1', 'dcat1', 'sin3', '7', '[3,4]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3104, 'cat1', 'dcat1', 'sin4', '8', '[3,4]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3105, 'cat1', 'dcat1', 'sin5', '5', '[5,6]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')

,(3301, 'cat1', 'dcat1', 'sin6', '3', '[5,6]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3302, 'cat1', 'dcat1', 'sin7', '4', '[5,6]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')

,(3401, 'cat1', 'dcat1', 'sin8', '4', '[1,2,7,8,9,10]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3402, 'cat1', 'dcat1', 'sin9', '6', '[1,2,7,8,9,10]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')

,(3501, 'cat1', 'dcat1', 'sin10', '2', '[1,2,7,8,9,10]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3502, 'cat1', 'dcat1', 'sin11', '4', '[1,2,7,8,9,10]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')

,(3601, 'cat1', 'dcat1', 'sin12', '4', '[1,2,7,8,9,10]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3602, 'cat1', 'dcat1', 'sin13', '4', '[1,2,7,8,9,10]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
,(3603, 'cat1', 'dcat1', 'sin14', '4', '[1,2,7,8,9,10]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')

,(3701, 'cat1', 'dcat1', 'sin15', '4', '[1,2,7,8,9,10]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')

,(1, '画面表示用', '画面表示用', '部門', '7', '{"operatorList": [3]}', '{"choicesList": [{"name":"Women", "value": "2"},{"name": "Men", "value": "3"},{"name":"Kids", "value":"1"},{"name":"Other", "value":"8"}]}', NULL, 'f', '2022-03-14 11:49:11.352395', '0', NULL, NULL, '2022-03-14 11:49:11.352395', '0')
,(2, '画面表示用', '画面表示用', '2桁部門', '7', '{"operatorList": [3]}', '{"choicesList": [{"name":"11", "value":"11"},{"name":"12", "value":"12"},{"name":"18", "value":"18"}]}', NULL, 'f', '2022-03-14 11:49:11.352395', '0', NULL, NULL, '2022-03-14 11:49:11.352395', '0')
,(3, '画面表示用', '画面表示用', '商品名(部分一致)', '1', '{"operatorList": [5]}', NULL, NULL, 'f', '2022-03-14 11:49:11.352395', '0', NULL, NULL, '2022-03-14 11:49:11.352395', '0')
,(4, '画面表示用', '画面表示用', '商品名除外(除外部分一致)', '1', '{"operatorList": [6]}', NULL, NULL, 'f', '2022-03-14 11:49:11.352395', '0', NULL, NULL, '2022-03-14 11:49:11.352395', '0')
,(1000, 'Custom', 'Custom', 'Custom', '1', '{"operatorList": [5,6]}', NULL, NULL, 'f', '2022-03-14 11:49:11.352395', '0', NULL, NULL, '2022-03-14 11:49:11.352395', '0')
,(1001, 'Custom', 'Custom', 'Custom', '1', '{"operatorList": [1,2]}', NULL, NULL, 'f', '2022-03-14 11:49:11.352395', '0', NULL, NULL, '2022-03-14 11:49:11.352395', '0')
,(1002, 'Custom', 'Custom', 'Custom', '1', '{"operatorList": [1]}', NULL, NULL, 'f', '2022-03-14 11:49:11.352395', '0', NULL, NULL, '2022-03-14 11:49:11.352395', '0')

;


TRUNCATE TABLE m_sgmt_itm_f_sql;

INSERT INTO m_sgmt_itm_f_sql
(sgmt_itm_f_sql_seq, sgmt_itm_f_scrn_seq, brnd_id, cntry_id, evt_type, sgmt_itm_name, qry_f_sgmt, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (4101, 3101, '090', '1', '1', 'sin1', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 AND col41_text ${ operator } ''${ value }''', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4102, 3102, '090', '1', '1', 'sin2', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 AND col42_num ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4103, 3103, '090', '1', '1', 'sin3', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 AND col43_text ${ operator } (${ value })', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4104, 3104, '090', '1', '1', 'sin4', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 AND col44_num ${ operator } (${ value })', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4105, 3104, '090', '1', '1', 'sin5', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 AND col45_text ${ operator } ''%${ value }%''', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(4301, 3301, '090', '1', '1', 'sin6', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 AND col46_text ${ operator } ''%${ value }%''', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4302, 3302, '090', '1', '1', 'sin7', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 AND col47_text ${ operator } ''%${ value }%''', 't', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(4401, 3401, '090', '1', '1', 'sin6', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$ AND col48_text ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4402, 3402, '090', '1', '1', 'sin7', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$ AND col49_text ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(4501, 3501, '090', '1', '1', 'sin6', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$ AND col50_text ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4502, 3502, '090', '1', '1', 'sin7', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$ AND col51_text ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(4601, 3601, '090', '1', '1', 'sin7', 'SELECT uid FROM fr-${ ENV }-mdb.${ DATASET_GA_UQJP }.hoge WHERE 1=1 and col_1 ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4602, 3602, '090', '1', '1', 'sin7', 'SELECT uid FROM fr-${ ENV }-mdb.${ DATASET_GA_GUJP }.hoge WHERE 1=1 and col_1 ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(4603, 3603, '090', '1', '1', 'sin7', 'SELECT uid FROM fr-${ ENV }-mdb.${ DATASET_GA_UQJP_APP }.hoge WHERE 1=1 and col_1 ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(4701, 3701, '090', '1', '1', 'sin7', 'SELECT uid FROM fr-${ ENV }-mdb.${ DATASET_GA_UQJP }.hoge WHERE 1=1 and $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$ and col_1 ${ operator } ${ value }', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')

,(4801, 1, '090', '1', '5', '部門', 'SELECT DISTINCT a.uid FROM `fr-${ ENV }-safelake.${ DATASET_GA_GUJP }.ga_sessions_*` AS log, UNNEST(hits) AS h , UNNEST(h.product) AS p  INNER JOIN fr-${ ENV }-mdb.non_pii_${ ENV }_gujp.dmp_vw_m_ctlg_gujp AS c ON c.lvl_2_itm_cd = p.productSKU  INNER JOIN fr-${ ENV }-mdb.pii_${ ENV }_frjp.dmp_vw_m_acnt_jp AS a ON (SELECT MAX(IF(index=14, value, '''')) FROM UNNEST(log.customDimensions)) = a.mmbr_id_hash_sub_id WHERE 1=1 $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$ AND h.ecommerceaction.action_type = ''6'' AND c.dept_elmnt.id IN ( ${ value } )', 'f', '2022-03-14 11:49:15.344948', '0', NULL, NULL, '2022-03-14 11:49:15.344948', '0')
,(4802, 2, '090', '1', '3', '2桁部門', 'SELECT DISTINCT a.uid FROM `fr-${ ENV }-safelake.${ DATASET_GA_GUJP }.ga_sessions_*` AS log, UNNEST(hits) AS h , UNNEST(h.product) AS p  INNER JOIN fr-${ ENV }-mdb.non_pii_${ ENV }_gujp.dmp_vw_m_ctlg_gujp AS c ON c.lvl_2_itm_cd = p.productSKU  INNER JOIN fr-${ ENV }-mdb.pii_${ ENV }_frjp.dmp_vw_m_acnt_jp AS a ON (SELECT MAX(IF(index=14, value, '''')) FROM UNNEST(log.customDimensions)) = a.mmbr_id_hash_sub_id WHERE 1=1 $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$ AND h.eCommerceAction.step in (1,2,3,4) AND c.g_dept_elmnt.id IN ( ${ value } )', 'f', '2022-03-14 11:49:15.344948', '0', NULL, NULL, '2022-03-14 11:49:15.344948', '0')
,(4803, 3, '090', '1', '6', '商品名(部分一致)', 'SELECT DISTINCT p.uid FROM fr-${ ENV }-mdb.pii_${ ENV }_gujp.dmp_dmp_vw_t_prchs_hist_item_ctlg_gujp  AS p WHERE 1=1 AND p.trn_type_cd = ''SALE'' AND p.recpt_sts = ''P'' AND p.itm_type = ''ITEM'' AND p.sls_qty <![CDATA[>=]]> 1 AND p.mmbr_id IS NOT NULL $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION_FOR_PURCHASE$$ AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.itm_name LIKE ''%${ value }%''', 'f', '2022-03-14 11:49:15.344948', '0', NULL, NULL, '2022-03-14 11:49:15.344948', '0')
,(4804, 4, '090', '1', '3', '商品名除外(除外部分一致)', 'SELECT DISTINCT a.uid FROM `fr-${ ENV }-safelake.${ DATASET_GA_GUJP }.ga_sessions_*` AS log, UNNEST(hits) AS h , UNNEST(h.product) AS p  INNER JOIN fr-${ ENV }-mdb.non_pii_${ ENV }_gujp.dmp_vw_m_ctlg_gujp AS c ON c.lvl_2_itm_cd = p.productSKU  INNER JOIN fr-${ ENV }-mdb.pii_${ ENV }_frjp.dmp_vw_m_acnt_jp AS a ON (SELECT MAX(IF(index=14, value, '''')) FROM UNNEST(log.customDimensions)) = a.mmbr_id_hash_sub_id WHERE 1=1 $$EVENT_TARGET_PERIOD_DATE_TIME_CONDITION$$ AND h.eCommerceAction.step in (1,2,3,4) AND c.itm_name NOT LIKE ''%${ value }%''', 'f', '2022-03-14 11:49:15.344948', '0', NULL, NULL, '2022-03-14 11:49:15.344948', '0')
,(4805, 1000, '090', '1', '3', '商品名除外(除外部分一致)', 'SELECT DISTINCT a.uid fr-${ ENV }-mdb.pii_${ ENV }_frjp.dmp_vw_m_acnt_jp AS a WHERE 1=1 $$SEGMENT_BASIC_CONDITION_DEPT_ELMNT_GA$$ $$SEGMENT_BASIC_CONDITION_G_DEPT_ELMNT_PH$$ $$SEGMENT_BASIC_CONDITION_ITM_NAME_LIKE_GA$$ $$SEGMENT_BASIC_CONDITION_ITM_NAME_NOT_LIKE_PH$$ AND h.eCommerceAction.step in (1,2,3,4) AND c.itm_name = ''${ value }''', 'f', '2022-03-14 11:49:15.344948', '0', NULL, NULL, '2022-03-14 11:49:15.344948', '0')
,(4806, 1001, '090', '1', '3', '商品名除外(除外部分一致)', 'SELECT DISTINCT b.uid fr-${ ENV }-mdb.pii_${ ENV }_frjp.b_table AS b WHERE 1=1 AND c.itm_name != ''${ value }''', 'f', '2022-03-14 11:49:15.344948', '0', NULL, NULL, '2022-03-14 11:49:15.344948', '0')

;
