TRUNCATE TABLE t_sgmt;

INSERT INTO t_sgmt
(sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, "desc", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, '1', '090', '1', '202101', '1', '2021-11-30T17:00:00.000Z', '2021-12-08T17:00:00.000Z', 2001, '1', 'item_category_1', 'segment_name_1', 'desc_1', '', '0', '1', '{}'::json, '1' , 'f', '1970-01-01 00:00:00', 'user_id_1', '1970-01-01 00:00:00', 'user_id_1', '1970-01-01 00:00:00', 'user_id_1'),
(1002, '2', '010', '1', '202101', '1', '2021-11-30T17:00:00.000Z', '2021-12-08T17:00:00.000Z', 2002, '2', 'item_category_2', 'segment_name_2', 'desc_2', '', '0', '2', '{}'::json, '1' , 'f', '1970-01-01 00:00:00', 'user_id_2', '1970-01-01 00:00:00', 'user_id_2', '1970-01-01 00:00:00', 'user_id_2'),
(1003, '1', '090', '1', '202101', '1', '2021-11-30T17:00:00.000Z', '2021-12-08T17:00:00.000Z', 2003, '3', 'item_category_3', 'segment_name_3', 'desc_3', '', '0', '1', '{}'::json, '1' , 'f', '1970-01-01 00:00:00', 'user_id_3', '1970-01-01 00:00:00', 'user_id_3', '1970-01-01 00:00:00', 'user_id_3')
;

SELECT setval('t_sgmt_sgmt_seq_seq', (SELECT MAX(sgmt_seq) from "t_sgmt"));

TRUNCATE TABLE t_sgmt_cond;

INSERT INTO t_sgmt_cond
(sgmt_cond_seq, sgmt_seq, sgmt_itm_f_scrn_seq, sgmt_itm_f_sql_seq, oprtr_type, cmprsn_val, sgmt_cond_blok_ordr, lgcl_oprtr_type, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 1001, 7, 4001, '3', '[3, 4]', 1, '1', 'f', '2021-01-01 00:00:01.000', 'user_01', NULL, NULL, '2021-01-01 00:00:02.000', 'user_01'),
(1002, 1001, 8, 4002, '3', '[3, 4]', 1, '2', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02'),
(1004, 1001, 9, 4002, '3', '[3, 4]', 1, '2', 't', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02'),
(1003, 1002, 10, 4002, '3', '[3, 4]', 1, '2', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02'),
(1006, 1002, 11, 4002, '3', '[3, 4]', 1, '2', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02'),
(1005, 1002, 12, 4002, '3', '[3, 4]', 1, '2', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02')
;

SELECT setval('t_sgmt_cond_sgmt_cond_seq_seq', (SELECT MAX(sgmt_cond_seq) from "t_sgmt_cond"));
