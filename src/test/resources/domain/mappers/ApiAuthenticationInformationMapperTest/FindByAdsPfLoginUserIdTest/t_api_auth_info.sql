TRUNCATE TABLE t_api_auth_info;

INSERT INTO t_api_auth_info
(api_auth_info_seq, ads_pltfrm_seq, api_tkn, ads_pltfrm_login_user_id, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
 (1005, 1, '{}'::json, 'ad01', 'f', '2021-01-01 00:00:00', 'user_11', null, null, '2021-01-11 00:00:00', 'user_21')
,(1001, 1, '{}'::json, 'ad08', 't', '2021-01-02 00:00:00', 'user_12', null, null, '2021-01-12 00:00:00', 'user_22')
,(1002, 2, '{}'::json, 'ad01', 'f', '2021-01-03 00:00:00', 'user_13', null, null, '2021-01-13 00:00:00', 'user_23')
,(1003, 1, '{}'::json, 'ad02', 'f', '2021-01-04 00:00:00', 'user_14', null, null, '2021-01-14 00:00:00', 'user_24')
,(1004, 2, '{}'::json, 'ad04', 'f', '2021-01-05 00:00:00', 'user_15', null, null, '2021-01-15 00:00:00', 'user_25')
,(1006, 1, '{}'::json, 'ad04', 'f', '2021-01-06 00:00:00', 'user_16', null, null, '2021-01-16 00:00:00', 'user_26')
;
