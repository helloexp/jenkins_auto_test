TRUNCATE TABLE m_sgmt_itm_f_scrn;

INSERT INTO m_sgmt_itm_f_scrn
(sgmt_itm_f_scrn_seq, ctgry, dtl_ctgry, sgmt_itm_name, inpt_type, oprtr_list, chc_list, url_f_api_aces, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1003, 'large3', 'small3', 'item3', '3', '{}', '{}'::json, 'hoge.com', 'f', '1970-01-01 00:00:00', 'user_id_3', '1970-01-01 00:00:00', 'user_id_3', '1970-01-01 00:00:00', 'user_id_3'),
(1002, 'large2', 'small2', 'item2', '2', '{}', '{}'::json, 'fuga.com', 'f', '1970-01-01 00:00:00', 'user_id_2', '1970-01-01 00:00:00', 'user_id_2', '1970-01-01 00:00:00', 'user_id_2'),
(1001, 'large1', 'small1', 'item1', '1', '{}', '{}'::json, 'hoge.com', 't', '1970-01-01 00:00:00', 'user_id_1', '1970-01-01 00:00:00', 'user_id_1', '1970-01-01 00:00:00', 'user_id_1')
;
