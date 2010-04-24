CREATE TABLE IF NOT EXISTS facebook.user (
  id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  facebook_user_id VARCHAR(255) NOT NULL,
  session_key VARCHAR(255),
  CONSTRAINT idx_usr_fuid UNIQUE INDEX (facebook_user_id),
  PRIMARY KEY (id)
) ENGINE = InnoDB;
