TRUNCATE TABLE t_sgmt;
INSERT INTO public.t_sgmt
(sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, "desc", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type,del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, '2', '010', '1', '202101', '1', '1970-01-01', '2021-11-16', 2000, '3', 'item_category_1', 'segment_name_1', 'desc_1', '0', 'sql_1', '1', '[1, 2]'::json, '1', 'f', '1970-01-01 00:00:01.000', 'user_id_1', '1970-01-01 00:00:01.000', 'user_id_1', '2021-10-01 00:00:01.000', 'user_id_1'),
(1002, '1', '090', '1', '202101', '2', '2021-11-16', '1970-02-02', 1000, '1', 'item_category_2', 'segment_name_2', 'desc_2', '1', 'sql_2', '2', '[1, 2]'::json, '1', 'f', '1970-01-01 00:00:01.000', 'user_id_2', '1970-01-01 00:00:01.000', 'user_id_2', '2021-11-10 00:00:01.000', 'user_id_2'),
(1003, '2', '010', '1', '202103', '1', '1970-01-01', '2021-11-16', 2000, '3', 'item_category_3', 'segment_name_3', 'desc_3', '0', 'sql_3', '1', '[1, 2]'::json, '1', 'f', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3', '2021-12-10 00:00:01.000', 'user_id_3');
SELECT setval('t_sgmt_sgmt_seq_seq', (SELECT MAX(sgmt_seq) from "t_sgmt"));
