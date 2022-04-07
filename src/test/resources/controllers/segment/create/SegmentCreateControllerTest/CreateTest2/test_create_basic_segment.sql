TRUNCATE TABLE t_sgmt;
SELECT setval('t_sgmt_sgmt_seq_seq', 1000);

TRUNCATE TABLE t_sgmt_cond;
SELECT setval('t_sgmt_cond_sgmt_cond_seq_seq', 2000);

TRUNCATE TABLE m_sgmt_itm_f_scrn;
INSERT INTO m_sgmt_itm_f_scrn (sgmt_itm_f_scrn_seq, ctgry, dtl_ctgry, sgmt_itm_name, inpt_type, oprtr_list, chc_list, url_f_api_aces, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt) VALUES
(1, '画面表示用', '画面表示用', '部門', '7', E'{\"operatorList\": [3]}', E'{\"choicesList\": [{\"name\":\"Women\", \"value\": \"2\"},{\"name\": \"Men\", \"value\": \"3\"},{\"name\":\"Kids\", \"value\":\"1\"},{\"name\":\"Other\", \"value\":\"8\"}]}', null, 'f', NOW(), '0', null, null, NOW(), '0')
,(2, '画面表示用', '画面表示用', '2桁部門', '7', E'{\"operatorList\": [3]}', E'{\"choicesList\": [{\"name\":\"11\", \"value\":\"11\"},{\"name\":\"12\", \"value\":\"12\"},{\"name\":\"18\", \"value\":\"18\"},{\"name\":\"21\", \"value\":\"21\"},{\"name\":\"22\", \"value\":\"22\"},{\"name\":\"23\", \"value\":\"23\"},{\"name\":\"24\", \"value\":\"24\"},{\"name\":\"25\", \"value\":\"25\"},{\"name\":\"26\", \"value\":\"26\"},{\"name\":\"27\", \"value\":\"27\"},{\"name\":\"28\", \"value\":\"28\"},{\"name\":\"29\", \"value\":\"29\"},{\"name\":\"31\", \"value\":\"31\"},{\"name\":\"32\", \"value\":\"32\"},{\"name\":\"33\", \"value\":\"33\"},{\"name\":\"34\", \"value\":\"34\"},{\"name\":\"35\", \"value\":\"35\"},{\"name\":\"36\", \"value\":\"36\"},{\"name\":\"37\", \"value\":\"37\"},{\"name\":\"38\", \"value\":\"38\"}]}', null, 'f', NOW(), '0', null, null, NOW(), '0')
,(3, '画面表示用', '画面表示用', '商品名(部分一致)', '1', E'{\"operatorList\": [5]}', null, null, 'f', NOW(), '0', null, null, NOW(), '0')
,(4, '画面表示用', '画面表示用', '商品名除外(除外部分一致)', '1', E'{\"operatorList\": [6]}', null, null, 'f', NOW(), '0', null, null, NOW(), '0')
,(5, 'ユーザ属性', 'ユーザ属性', '年代', '7', E'{\"operatorList\": [3, 4]}', E'{\"choicesList\": [{\"name\":\"10代\", \"value\": \"10\"},{\"name\":\"20代\", \"value\": \"20\"},{\"name\":\"30代\", \"value\": \"30\"},{\"name\":\"40代\", \"value\": \"40\"},{\"name\":\"50代\", \"value\": \"50\"},{\"name\":\"60代\", \"value\": \"60\"}]}', null, 'f', NOW(), '0', null, null, NOW(), '0')
,(6, 'ユーザ属性', 'ユーザ属性', '登録性別', '7', E'{\"operatorList\": [3, 4]}', E'{\"choicesList\":[{\"name\":\"男性\",\"value\":\"01\"}, {\"name\":\"女性\",\"value\":\"02\"}, {\"name\":\"その他\",\"value\":\"NOT_SPECIFIED\"}]}', null, 'f', NOW(), '0', null, null, NOW(), '0')
,(35, 'ユーザ属性', 'ユーザ属性', '都道府県', '7', E'{\"operatorList\": [3, 4]}', E'{\"choicesList\":[{\"name\":\"北海道\",\"value\": \"北海道\"},{\"name\":\"青森県\",\"value\": \"青森県\"},{\"name\":\"岩手県\",\"value\": \"岩手県\"},{\"name\":\"宮城県\",\"value\": \"宮城県\"},{\"name\":\"秋田県\",\"value\": \"秋田県\"},{\"name\":\"山形県\",\"value\": \"山形県\"},{\"name\":\"福島県\",\"value\": \"福島県\"},{\"name\":\"茨城県\",\"value\": \"茨城県\"},{\"name\":\"栃木県\",\"value\": \"栃木県\"},{\"name\":\"群馬県\",\"value\": \"群馬県\"},{\"name\":\"埼玉県\",\"value\": \"埼玉県\"},{\"name\":\"千葉県\",\"value\": \"千葉県\"},{\"name\":\"東京都\",\"value\": \"東京都\"},{\"name\":\"神奈川県\",\"value\": \"神奈川県\"},{\"name\":\"新潟県\",\"value\": \"新潟県\"},{\"name\":\"富山県\",\"value\": \"富山県\"},{\"name\":\"石川県\",\"value\": \"石川県\"},{\"name\":\"福井県\",\"value\": \"福井県\"},{\"name\":\"山梨県\",\"value\": \"山梨県\"},{\"name\":\"長野県\",\"value\": \"長野県\"},{\"name\":\"岐阜県\",\"value\": \"岐阜県\"},{\"name\":\"静岡県\",\"value\": \"静岡県\"},{\"name\":\"愛知県\",\"value\": \"愛知県\"},{\"name\":\"三重県\",\"value\": \"三重県\"},{\"name\":\"滋賀県\",\"value\": \"滋賀県\"},{\"name\":\"京都府\",\"value\": \"京都府\"},{\"name\":\"大阪府\",\"value\": \"大阪府\"},{\"name\":\"兵庫県\",\"value\": \"兵庫県\"},{\"name\":\"奈良県\",\"value\": \"奈良県\"},{\"name\":\"和歌山県\",\"value\": \"和歌山県\"},{\"name\":\"鳥取県\",\"value\": \"鳥取県\"},{\"name\":\"島根県\",\"value\": \"島根県\"},{\"name\":\"岡山県\",\"value\": \"岡山県\"},{\"name\":\"広島県\",\"value\": \"広島県\"},{\"name\":\"山口県\",\"value\": \"山口県\"},{\"name\":\"徳島県\",\"value\": \"徳島県\"},{\"name\":\"香川県\",\"value\": \"香川県\"},{\"name\":\"愛媛県\",\"value\": \"愛媛県\"},{\"name\":\"高知県\",\"value\": \"高知県\"},{\"name\":\"福岡県\",\"value\": \"福岡県\"},{\"name\":\"佐賀県\",\"value\": \"佐賀県\"},{\"name\":\"長崎県\",\"value\": \"長崎県\"},{\"name\":\"熊本県\",\"value\": \"熊本県\"},{\"name\":\"大分県\",\"value\": \"大分県\"},{\"name\":\"宮崎県\",\"value\": \"宮崎県\"},{\"name\":\"鹿児島県\",\"value\": \"鹿児島県\"},{\"name\":\"沖縄県\",\"value\": \"沖縄県\"}]}', null, 'f', NOW(), '0', null, null, NOW(), '0')
,(37, 'ユーザ属性', 'ユーザ属性', '郵便番号(7桁ハイフンなし)', '1', E'{\"operatorList\": [1,2]}', null, null, 'f', NOW(), '0', null, null, NOW(), '0')
;


TRUNCATE TABLE m_sgmt_itm_f_sql;
INSERT INTO m_sgmt_itm_f_sql (sgmt_itm_f_sql_seq, sgmt_itm_f_scrn_seq, brnd_id, cntry_id, evt_type, sgmt_itm_name, qry_f_sgmt, del_flg_f_adt, create_date_time_f_adt, create_user_id_f_adt, del_date_time_f_adt, del_user_id_f_adt, update_date_time_f_adt, update_user_id_f_adt) VALUES
 (1001, 1, '010', '1', '1', 'EC会員性別', E'select uid from hoge', 'f', NOW(), '0', null, null, NOW(), '0')
,(1002, 1, '010', '1', '3', 'EC会員性別', E'select uid from hoge', 'f', NOW(), '0', null, null, NOW(), '0')
,(1003, 3, '010', '1', '1', 'EC会員性別', E'select uid from hoge', 'f', NOW(), '0', null, null, NOW(), '0')
,(1004, 4, '010', '1', '3', 'EC会員性別', E'select uid from hoge', 'f', NOW(), '0', null, null, NOW(), '0')
,(1005, 4, '010', '1', '4', 'EC会員性別', E'select uid from hoge', 'f', NOW(), '0', null, null, NOW(), '0')
,(1006, 35, '010', '1', '5', 'EC会員性別', E'select uid from hoge', 'f', NOW(), '0', null, null, NOW(), '0')
,(1007, 37, '010', '1', '5', 'EC会員性別', E'select uid from hoge', 'f', NOW(), '0', null, null, NOW(), '0')
;

