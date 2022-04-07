TRUNCATE TABLE t_sgmt;

INSERT INTO t_sgmt
(sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, "desc", sql_edit_flg, editd_sql, sts, evt_type_list, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 'biz_1', '1', '1', 'm_1', '1', '1970-01-01', '1970-02-02', 2001, '1', 'item_category_1', 'segment_name_1', 'desc_1', '1', 'sql_1', '1', '{}'::json, 'f', '1970-01-01 00:00:00', 'user_id_1', '1970-01-01 00:00:00', 'user_id_1', '1970-01-01 00:00:00', 'user_id_1'),
(1002, 'biz_2', '2', '2', 'm_2', '2', '1970-01-01', '1970-02-02', 2002, '2', 'item_category_2', 'segment_name_2', 'desc_2', '2', 'sql_2', '2', '{}'::json, 'f', '1970-01-01 00:00:00', 'user_id_2', '1970-01-01 00:00:00', 'user_id_2', '1970-01-01 00:00:00', 'user_id_2'),
(1003, 'biz_3', '3', '3', 'm_3', '3', '1970-01-01', '1970-02-02', 2003, '3', 'item_category_3', 'segment_name_3', 'desc_3', '3', 'sql_3', '3', '{}'::json, 'f', '1970-01-01 00:00:00', 'user_id_3', '1970-01-01 00:00:00', 'user_id_3', '1970-01-01 00:00:00', 'user_id_3')
;

SELECT setval('t_sgmt_sgmt_seq_seq', (SELECT MAX(sgmt_seq) from "t_sgmt"));
