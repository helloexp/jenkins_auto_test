TRUNCATE TABLE public.m_ads_pltfrm;

INSERT INTO m_ads_pltfrm
(ads_pltfrm_seq, otpt_pltfrm_seq, ads_pltfrm_name, api_tkn, ext_trgt_id_list, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 2001, 'Google Ads', '{"client_id": "googleusercontent.com"}'::json, '{}'::json, 'f', '2021-02-02 00:00:01.000', 'user1', '2021-02-02 00:00:02.000', 'user1', '2021-02-02 00:00:03.000', 'user1'),
(1002, 2001, 'Facebook Ads', '{"app_id": "AppId"}'::json, '{}'::json, 'f', '2021-02-03 00:00:01.000', 'user1', '2021-02-03 00:00:02.000', 'user1', '2021-02-03 00:00:03.000', 'user1')
;
