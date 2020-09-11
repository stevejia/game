ALTER TABLE `zp_user_level`
	CHANGE COLUMN `describe` `describes` VARCHAR(200) NULL DEFAULT NULL COMMENT '头街 描述' AFTER `discount`;
