TRUNCATE TABLE t_sgmt_cond;

INSERT INTO t_sgmt_cond
(sgmt_cond_seq, sgmt_seq, sgmt_itm_f_scrn_seq, sgmt_itm_f_sql_seq, oprtr_type, cmprsn_val, sgmt_cond_blok_ordr, lgcl_oprtr_type, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 2001, 3001, 4001, '1', '["val_01"]', 1, '1', 'f', '2021-01-01 00:00:01.000', 'user_01', NULL, NULL, '2021-01-01 00:00:02.000', 'user_01'),
(1002, 2002, 3002, 4002, '2', '["val_02"]', 1, '2', 'f', '2021-02-02 00:00:01.000', 'user_02', NULL, NULL, '2021-02-02 00:00:02.000', 'user_02')
;

SELECT setval('t_sgmt_cond_sgmt_cond_seq_seq', (SELECT MAX(sgmt_cond_seq) from "t_sgmt_cond"));
