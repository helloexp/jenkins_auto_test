TRUNCATE TABLE public.t_api_auth_info;

INSERT INTO t_api_auth_info
(api_auth_info_seq, ads_pltfrm_seq, api_tkn, ads_pltfrm_login_user_id, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 2001, '{}'::json, 'user_01', 'f', '2021-01-01 00:00:00.000', 'user_01', '2021-01-01 00:00:00.000', 'user_01', '2021-01-01 00:00:00.000', 'user_01'),
(1003, 2003, '{}'::json, 'user_03', 'f', '2021-03-03 00:00:00.000', 'user_03', '2021-03-03 00:00:00.000', 'user_03', '2021-03-03 00:00:00.000', 'user_03'),
(1002, 2002, '{}'::json, 'user_02', 'f', '2021-02-02 00:00:00.000', 'user_02', '2021-02-02 00:00:00.000', 'user_02', '2021-02-02 00:00:00.000', 'user_02')
;

SELECT setval('t_api_auth_info_api_auth_info_seq_seq', (SELECT MAX(api_auth_info_seq) from "t_api_auth_info"));
