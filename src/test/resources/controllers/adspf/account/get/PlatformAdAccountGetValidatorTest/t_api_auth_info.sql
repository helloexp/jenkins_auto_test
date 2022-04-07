TRUNCATE TABLE t_api_auth_info;

INSERT INTO t_api_auth_info
(api_auth_info_seq, ads_pltfrm_seq, api_tkn, ads_pltfrm_login_user_id, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 1, '{"refresh_token": "refresh_token"}'::json, 'user_01', 'f', '2021-01-01 00:00:01.000', 'user1', '2021-01-01 00:00:02.000', 'user1', '2021-01-01 00:00:03.000', 'user1'),
(1002, 2, '{"access_token": "access_token"}'::json, 'user_02', 'f', '2021-02-02 00:00:01.000', 'user2', '2021-02-02 00:00:02.000', 'user2', '2021-02-02 00:00:03.000', 'user2'),
(1003, 1, '{"access_token": "access_token"}'::json, 'user_02', 't', '2021-02-02 00:00:01.000', 'user2', '2021-02-02 00:00:02.000', 'user2', '2021-02-02 00:00:03.000', 'user2'),
(1004, 4, '{"access_token": "access_token"}'::json, 'user_02', 'f', '2021-02-02 00:00:01.000', 'user2', '2021-02-02 00:00:02.000', 'user2', '2021-02-02 00:00:03.000', 'user2')
;

SELECT setval('t_api_auth_info_api_auth_info_seq_seq', (SELECT MAX(api_auth_info_seq) from "t_api_auth_info"));
