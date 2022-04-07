TRUNCATE TABLE t_sgmt;

INSERT INTO t_sgmt
(sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, "desc", sql_edit_flg, editd_sql, sts, evt_type_list, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, '2', '010', '1', 'm_1', '1', '1970-01-01', '2021-11-16', 2000, '3', 'item_category_1', 'segment_name_1', 'desc_1', '1', 'sql_1', '1', '{}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_1', '1970-01-01 00:00:01.000', 'user_id_1', '1970-01-01 00:00:01.000', 'user_id_1'),
(1002, '1', '090', '1', 'm_2', '2', '2021-11-16', '1970-02-02', 1000, '1', 'item_category_2', 'segment_name_2', 'desc_2', '2', 'sql_2', '2', '{}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_2', '1970-01-01 00:00:01.000', 'user_id_2', '1970-01-01 00:00:01.000', 'user_id_2'),
(1003, '2', '010', '1', 'm_1', '1', '1970-01-01', '2021-11-16', 2000, '3', 'item_category_3', 'segment_name_3', 'desc_3', '3', 'sql_3', '1', '{}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3'),
(1004, '1', '090', '1', 'm_2', '2', '2021-11-16', '1970-02-02', 1000, '2', 'item_category_4', 'segment_name_4', 'desc_4', '4', 'sql_4', '4', '{}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_4', '1970-01-01 00:00:01.000', 'user_id_4', '1970-01-01 00:00:01.000', 'user_id_4'),
(1005, '2', '010', '1', 'm_1', '1', '1970-01-01', '2021-11-16', 2000, '3', 'item_category_5', 'segment_name_5', 'desc_5', '5', 'sql_5', '1', '{}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_5', '1970-01-01 00:00:01.000', 'user_id_5', '1970-01-01 00:00:01.000', 'user_id_5');

SELECT setval('t_sgmt_sgmt_seq_seq', (SELECT MAX(sgmt_seq) from "t_sgmt"));

