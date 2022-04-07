TRUNCATE TABLE m_user;

INSERT INTO m_user
(user_id, user_full_nm, role_seq_list, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 'User_1', '{"authority": 1}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_1', '1970-01-01 00:00:01.000', 'user_id_1', '1970-01-01 00:00:01.000', 'user_id_1'),
(1004, 'User_4', '{"authority": 4}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3'),
(1002, 'User_2', '{"authority": 2}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_2', '1970-01-01 00:00:01.000', 'user_id_2', '1970-01-01 00:00:01.000', 'user_id_2'),
(1003, 'User_3', '{"authority": 3}'::json, 't', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3'),
(1005, 'User_5', '{"authority": 5}'::json, 'f', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3', '1970-01-01 00:00:01.000', 'user_id_3');