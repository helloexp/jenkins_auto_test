TRUNCATE TABLE t_otpt_pltfrm_outbound_stng_intrm;

INSERT INTO t_otpt_pltfrm_outbound_stng_intrm
(otpt_pltfrm_outbound_stng_intrm_seq, outbound_stng_seq, otpt_pltfrm_seq, otpt_pltfrm_type, ad_acnt_seq, ads_pltfrm_seq, crm_pltfrm_seq, adnc_id, user_list_name, ext_trgt_id, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 2001, 3001, '1', 4001, 5001, 6001, '123', 'name_123', '2', 'f', '2021-01-01 00:00:00.000', 'user_01', NULL, NULL, '2021-01-01 00:00:00.000', 'user_01'),
(1005, 2001, 3001, '1', 4001, 5001, 6001, '456', 'name_456', '1', 'f', '2021-01-01 00:00:00.000', 'user_01', NULL, NULL, '2021-01-01 00:00:00.000', 'user_01'),
(1004, 2001, 3001, '1', 4001, 5001, 6001, '789', 'name_789', '3', 'f', '2021-01-01 00:00:00.000', 'user_01', NULL, NULL, '2021-01-01 00:00:00.000', 'user_01'),
(1003, 2003, 3003, '1', 4003, 5003, 6003, '654', 'name_654', '1', 'f', '2021-03-03 00:00:00.000', 'user_03', NULL, NULL, '2021-03-03 00:00:00.000', 'user_03'),
(1002, 2002, 3002, '1', 4002, 5002, 6002, '258', 'name_258', '1', 'f', '2021-02-02 00:00:00.000', 'user_02', NULL, NULL, '2021-02-02 00:00:00.000', 'user_02')
;

SELECT SETVAL((select pg_get_serial_sequence('t_otpt_pltfrm_outbound_stng_intrm', 'otpt_pltfrm_outbound_stng_intrm_seq')),
(SELECT MAX(otpt_pltfrm_outbound_stng_intrm_seq) from "t_otpt_pltfrm_outbound_stng_intrm"));
