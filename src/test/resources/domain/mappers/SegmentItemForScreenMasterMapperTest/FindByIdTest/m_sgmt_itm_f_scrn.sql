TRUNCATE TABLE m_sgmt_itm_f_scrn;

INSERT INTO m_sgmt_itm_f_scrn
(sgmt_itm_f_scrn_seq, ctgry, dtl_ctgry, sgmt_itm_name, inpt_type, oprtr_list, chc_list, url_f_api_aces, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1003, 'cat_1003', 'detail_cat_1003', 'name_1003', '3', '{"operatorList": [3]}', '{"choicesList": [{"name":"Women", "value": 2},{"name": "Men", "value": 3},{"name":"Kids", "value":1},{"name":"Other", "value":8}]}', null, 'f', '2022-01-03 00:00:00', 'user_1003', null, null, '2022-01-03 00:00:01', 'user_1003'),
(1001, 'cat_1001', 'detail_cat_1001', 'name_1001', '1', '{"operatorList": [1]}', '{"choicesList": [{"name":"Women", "value": 2},{"name": "Men", "value": 3},{"name":"Kids", "value":1},{"name":"Other", "value":8}]}', null, 'f', '2022-01-01 00:00:00', 'user_1001', null, null, '2022-01-01 00:00:01', 'user_1001'),
(1002, 'cat_1002', 'detail_cat_1002', 'name_1002', '2', '{"operatorList": [2]}', '{"choicesList": [{"name":"Women", "value": 2},{"name": "Men", "value": 3},{"name":"Kids", "value":1},{"name":"Other", "value":8}]}', null, 'f', '2022-01-02 00:00:00', 'user_1002', null, null, '2022-01-02 00:00:01', 'user_1002')
;
