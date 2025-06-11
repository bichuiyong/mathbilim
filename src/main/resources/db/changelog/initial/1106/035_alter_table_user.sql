ALTER TABLE users ADD COLUMN password_reset_token varchar(255);
ALTER TABLE users ADD COLUMN email_verification_token varchar(255);