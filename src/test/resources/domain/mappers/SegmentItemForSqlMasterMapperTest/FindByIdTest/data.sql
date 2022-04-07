TRUNCATE TABLE m_sgmt_itm_f_sql;

INSERT INTO m_sgmt_itm_f_sql
(sgmt_itm_f_sql_seq, sgmt_itm_f_scrn_seq, brnd_id, cntry_id, evt_type, sgmt_itm_name, qry_f_sgmt, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 2001, '1', '1', '1', 'name_01', 'query_01', 'f', '2021-01-01 00:00:01.000', 'user_01', NULL, NULL, '2021-01-01 00:00:02.000', 'user_01'),
(1003, 2003, '3', '3', '3', 'name_03', 'query_03', 'f', '2021-03-03 00:00:01.000', 'user_03', NULL, NULL, '2021-03-03 00:00:02.000', 'user_03'),
(1002, 2002, '2', '2', '2', 'name_02', 'query_02', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02')
;

SELECT setval('m_sgmt_itm_f_sql_sgmt_itm_f_sql_seq_seq', (SELECT MAX(sgmt_itm_f_sql_seq) from "m_sgmt_itm_f_sql"));
