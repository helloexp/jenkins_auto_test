TRUNCATE TABLE public.t_outbound_stng;
INSERT INTO t_outbound_stng
(outbound_stng_seq, biz_type, brnd_id, cntry_id, sbmsn_tmg_type, rsrv_sbmsn_dtime, rglrly_sbmsn_frqncy_dtime_basis, rglrly_sbmsn_frqncy_perd_num_val, rglrly_sbmsn_frqncy_perd_unit, sbmsn_cmpl_cntct, outbound_stng_name, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, '1', '010', '1', '1', '2021-01-01 00:00:00.000', '2021-01-01 00:00:00.000', 1000, '1', '{}'::json, 'name_01', 'f', '2021-01-01 00:00:00.000', 'user_01', '2021-01-01 00:00:00.000', 'user_01', '2021-01-01 00:00:00.000', 'user_01'),
(1002, '2', '010', '1', '2', '2021-02-02 00:00:00.000', '2021-02-02 00:00:00.000', 2000, '2', '{}'::json, 'name_02', 'f', '2021-02-02 00:00:00.000', 'user_02', '2021-02-02 00:00:00.000', 'user_02', '2021-02-02 00:00:00.000', 'user_02')
;
SELECT setval('t_outbound_stng_outbound_stng_seq_seq', (SELECT MAX(outbound_stng_seq) from "t_outbound_stng"));
