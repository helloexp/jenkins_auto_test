TRUNCATE TABLE t_sgmt;

INSERT INTO t_sgmt
(sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, "desc", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (2101, '1', '090', '1', '202201', '1', '2021-01-01', '2021-01-02', null, null, '1', 'segment_01', 'desc_01', '0', '', '1', '{}'::json, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
,(2201, '1', '090', '1', '202201', '1', '2021-01-01', '2021-01-02', null, null, '1', 'segment_02', 'desc_02', '1', 'select * from hoge', '1', '{}'::json, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
;

TRUNCATE TABLE t_sgmt_cond;

INSERT INTO t_sgmt_cond
(sgmt_cond_seq, sgmt_seq, sgmt_itm_f_scrn_seq, sgmt_itm_f_sql_seq, oprtr_type, cmprsn_val, sgmt_cond_blok_ordr, lgcl_oprtr_type, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (1101, 2101, 3101, 4101, '1', '["val''01"]', 1, '1', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
;


TRUNCATE TABLE m_sgmt_itm_f_scrn;

INSERT INTO m_sgmt_itm_f_scrn (sgmt_itm_f_scrn_seq, ctgry, dtl_ctgry, sgmt_itm_name, inpt_type, oprtr_list, chc_list, url_f_api_aces, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (3101, 'cat1', 'dcat1', 'sin1', '1', '[1,2]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
;


TRUNCATE TABLE m_sgmt_itm_f_sql;

INSERT INTO m_sgmt_itm_f_sql
(sgmt_itm_f_sql_seq, sgmt_itm_f_scrn_seq, brnd_id, cntry_id, evt_type, sgmt_itm_name, qry_f_sgmt, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (4101, 3101, '090', '1', '1', 'sin1', 'SELECT uid FROM fr-${ ENV }-mdb.pii_${ ENV }_frjp WHERE 1=1 AND col41_text ${ operator } ''${ value }''', 'f', '2021-01-01 00:00:01', 'user_01', NULL, NULL, '2021-01-01 00:00:02', 'user_01')
;
