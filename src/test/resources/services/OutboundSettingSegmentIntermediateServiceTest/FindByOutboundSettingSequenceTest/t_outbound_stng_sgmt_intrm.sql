TRUNCATE TABLE t_outbound_stng_sgmt_intrm;
INSERT INTO t_outbound_stng_sgmt_intrm
(outbound_stng_sgmt_intrm_seq, outbound_stng_seq, sgmt_seq, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 2001, 3002, 'f', '2021-01-01 00:00:00.000', 'user_01', '2021-01-01 00:00:00.000', 'user_01', '2021-01-01 00:00:00.000', 'user_01'),
(1005, 2001, 3001, 'f', '2021-02-02 00:00:00.000', 'user_05', '2021-02-02 00:00:00.000', 'user_05', '2021-02-02 00:00:00.000', 'user_05'),
(1003, 2003, 3003, 'f', '2021-03-03 00:00:00.000', 'user_03', '2021-03-03 00:00:00.000', 'user_03', '2021-03-03 00:00:00.000', 'user_03'),
(1002, 2002, 3002, 'f', '2021-02-02 00:00:00.000', 'user_02', '2021-02-02 00:00:00.000', 'user_02', '2021-02-02 00:00:00.000', 'user_02'),
(1004, 2002, 3002, 't', '2021-02-02 00:00:00.000', 'user_04', '2021-02-02 00:00:00.000', 'user_04', '2021-02-02 00:00:00.000', 'user_04')
;
SELECT setval('t_outbound_stng_sgmt_intrm_outbound_stng_sgmt_intrm_seq_seq', (SELECT MAX(outbound_stng_sgmt_intrm_seq) from "t_outbound_stng_sgmt_intrm"));
