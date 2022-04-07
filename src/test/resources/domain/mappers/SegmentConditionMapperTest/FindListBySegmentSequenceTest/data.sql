TRUNCATE TABLE t_sgmt_cond;

INSERT INTO t_sgmt_cond
(sgmt_cond_seq, sgmt_seq, sgmt_itm_f_scrn_seq, sgmt_itm_f_sql_seq, oprtr_type, cmprsn_val, sgmt_cond_blok_ordr, lgcl_oprtr_type, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1002, 2001, 3002, 4002, '2', 'val_02', 1, '2', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02'),
(1001, 2001, 3001, 4001, '1', 'val_01', 1, '1', 'f', '2021-01-01 00:00:01.000', 'user_01', NULL, NULL, '2021-01-01 00:00:02.000', 'user_01'),
(1004, 2002, 3004, 4004, '4', 'val_04', 2, '2', 'f', '2021-04-04 00:00:01.000', 'user_04', NULL, NULL, '2021-04-04 00:00:04.000', 'user_04'),
(1005, 2002, 3005, 4005, '5', 'val_05', 2, '1', 'f', '2021-05-05 00:00:01.000', 'user_05', NULL, NULL, '2021-05-05 00:00:05.000', 'user_05'),
(1003, 2001, 3003, 4003, '3', 'val_03', 1, '1', 'f', '2021-03-03 00:00:01.000', 'user_03', NULL, NULL, '2021-03-03 00:00:03.000', 'user_03'),
(1006, 2006, 3006, 4006, '6', 'val_06', 0, '1', 'f', '2021-03-03 00:00:01.000', 'user_03', NULL, NULL, '2021-03-03 00:00:03.000', 'user_03')
;

SELECT setval('t_sgmt_cond_sgmt_cond_seq_seq', (SELECT MAX(sgmt_cond_seq) from "t_sgmt_cond"));

TRUNCATE TABLE m_sgmt_itm_f_sql;

INSERT INTO m_sgmt_itm_f_sql
(sgmt_itm_f_sql_seq, sgmt_itm_f_scrn_seq, brnd_id, cntry_id, evt_type, sgmt_itm_name, qry_f_sgmt, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(4001, 3001, '090', '1', '12', 'name_01', 'query_01', 'f', '2021-01-01 00:00:01.000', 'user_01', NULL, NULL, '2021-01-01 00:00:02.000', 'user_01'),
(4003, 3001, '3', '3', '3', 'name_03', 'query_03', 'f', '2021-03-03 00:00:01.000', 'user_03', NULL, NULL, '2021-03-03 00:00:02.000', 'user_03'),
(4002, 3001, '2', '2', '2', 'name_02', 'query_02', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02'),
(4005, 3002, '5', '5', '5', 'name_05', 'query_05', 'f', '2021-05-05 00:00:01.000', 'user_05', NULL, NULL, '2021-05-05 00:00:02.000', 'user_05'),
(4004, 3002, '4', '4', '4', 'name_04', 'query_04', 'f', '2021-04-04 00:00:01.000', 'user_04', NULL, NULL, '2021-04-04 00:00:02.000', 'user_04')
;

SELECT setval('m_sgmt_itm_f_sql_sgmt_itm_f_sql_seq_seq', (SELECT MAX(sgmt_itm_f_sql_seq) from "m_sgmt_itm_f_sql"));


TRUNCATE TABLE m_sgmt_itm_f_scrn;

INSERT INTO m_sgmt_itm_f_scrn (sgmt_itm_f_scrn_seq, ctgry, dtl_ctgry, sgmt_itm_name, inpt_type, oprtr_list, chc_list, url_f_api_aces, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(3001, 'cat1', 'dcat1', 'segment_item_name_1', '1', '[1]', '[]', 'http://hoge.com', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13'),
(3002, 'cat1', 'dcat1', 'segment_item_name_2', '2', '[1]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13'),
(3003, 'cat1', 'dcat1', 'segment_item_name_3', '3', '[1]', '[]', '', 'f', '2021-01-01 00:00:11', 'u11', '2021-01-01 00:00:12', 'u12', '2021-01-01 00:00:13', 'u13')
;

SELECT setval('m_sgmt_itm_f_sql_sgmt_itm_f_sql_seq_seq', (SELECT MAX(sgmt_itm_f_sql_seq) from "m_sgmt_itm_f_sql"));
