TRUNCATE TABLE m_sgmt_itm_f_sql;

INSERT INTO m_sgmt_itm_f_sql
(sgmt_itm_f_sql_seq, sgmt_itm_f_scrn_seq, brnd_id, cntry_id, evt_type, sgmt_itm_name, qry_f_sgmt, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 2001, '11', '12', '13', 'name_01', 'query_01', 'f', '2021-01-01 00:00:01.000', 'user_01', NULL, NULL, '2021-01-01 00:00:02.000', 'user_01'),
(1002, 2001, '21', '22', '23', 'name_02', 'query_02', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02'),
(1003, 2001, '31', '32', '33', 'name_03', 'query_03', 'f', '2021-03-03 00:00:01.000', 'user_03', NULL, NULL, '2021-03-03 00:00:02.000', 'user_03'),
(1004, 2002, '41', '42', '43', 'name_04', 'query_04', 'f', '2021-04-04 00:00:01.000', 'user_04', NULL, NULL, '2021-04-04 00:00:02.000', 'user_04'),
(1005, 2002, '51', '52', '53', 'name_05', 'query_05', 'f', '2021-05-05 00:00:01.000', 'user_05', NULL, NULL, '2021-05-05 00:00:02.000', 'user_05'),
(1006, 2002, '61', '62', '63', 'name_06', 'query_06', 'f', '2021-06-06 00:00:01.000', 'user_06', NULL, NULL, '2021-06-06 00:00:02.000', 'user_06')
;

SELECT setval('m_sgmt_itm_f_sql_sgmt_itm_f_sql_seq_seq', (SELECT MAX(sgmt_itm_f_sql_seq) from "m_sgmt_itm_f_sql"));
