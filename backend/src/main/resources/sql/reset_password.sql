-- 重置admin用户的密码为admin123
UPDATE tb_user SET password = '$2a$10$GgOl4P5VGxqabq5roCIvweNmO9DVZ5EPfTR.Mpfw/ijRYobm9IZyG' WHERE username = 'admin';

-- 重置tester用户的密码为test123
UPDATE tb_user SET password = '$2a$10$OiHUUzBo3i5EWSCzF6Qreu9OpVc36A5l9GFsYHWzTwyUDtE.m1MBu' WHERE username = 'tester';

-- 需要生成新的加密密码
-- UPDATE tb_user SET password = '新的加密密码' WHERE username = 'tester'; 