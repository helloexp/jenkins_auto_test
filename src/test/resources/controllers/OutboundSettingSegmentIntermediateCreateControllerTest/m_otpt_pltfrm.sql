TRUNCATE TABLE m_otpt_pltfrm;
INSERT INTO m_otpt_pltfrm
(otpt_pltfrm_seq, otpt_pltfrm_type, otpt_pltfrm_name, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 1, 'outputPlatformName1', 'f', '2021-01-01 00:00:00.000', 'user_01', '2021-01-01 00:00:00.000', 'user_01', '2021-01-01 00:00:00.000', 'user_01'),
(1002, 2, 'outputPlatformName2', 'f', '2021-02-02 00:00:00.000', 'user_02', '2021-02-02 00:00:00.000', 'user_02', '2021-02-02 00:00:00.000', 'user_02'),
(1003, 1, 'outputPlatformName3', 't', '2021-02-02 00:00:00.000', 'user_03', '2021-02-02 00:00:00.000', 'user_03', '2021-02-02 00:00:00.000', 'user_03')
;
