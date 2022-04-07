TRUNCATE TABLE m_user;

INSERT INTO m_user
(user_id, user_full_nm, role_seq_list, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt)
VALUES
(1001, 'User_1', '{"authority": 1}'::json, 'f', '2022-01-02 03:04:11', 'user_id_11', NULL, NULL, '2022-01-02 03:04:13', 'user_id_13'),
(1004, 'User_4', '{"authority": 4}'::json, 'f', '2022-01-02 03:04:41', 'user_id_41', NULL, NULL, '2022-01-02 03:04:43', 'user_id_43'),
(1002, 'User_2', '{"authority": 2}'::json, 'f', '2022-01-02 03:04:21', 'user_id_21', NULL, NULL, '2022-01-02 03:04:23', 'user_id_23'),
(1003, 'User_3', '{"authority": 3}'::json, 't', '2022-01-02 03:04:31', 'user_id_31', '2022-01-02 03:04:32', 'user_id_32', '2022-01-02 03:04:33', 'user_id_33'),
(1005, 'User_5', '{"authority": 5}'::json, 'f', '2022-01-02 03:04:51', 'user_id_51', NULL, NULL, '2022-01-02 03:04:53', 'user_id_53');
