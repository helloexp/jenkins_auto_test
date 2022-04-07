TRUNCATE TABLE m_otpt_pltfrm;

INSERT INTO m_otpt_pltfrm
(otpt_pltfrm_seq, otpt_pltfrm_type, otpt_pltfrm_name, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1002, '2', 'name_02', 'f', '2021-02-02 00:00:00.000', 'user_02', NULL, NULL, '2021-02-02 00:00:00.000', 'user_02'),
(1001, '1', 'name_01', 'f', '2021-01-01 00:00:00.000', 'user_01', NULL, NULL, '2021-01-01 00:00:00.000', 'user_01'),
(1003, '1', 'name_03', 'f', '2021-03-03 00:00:00.000', 'user_03', NULL, NULL, '2021-03-03 00:00:00.000', 'user_03')
;

SELECT setval('m_otpt_pltfrm_otpt_pltfrm_seq_seq', (SELECT MAX(otpt_pltfrm_seq) from "m_otpt_pltfrm"));
