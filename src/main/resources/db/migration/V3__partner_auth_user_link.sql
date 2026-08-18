ALTER TABLE partners
    ADD COLUMN auth_user_id BIGINT;

ALTER TABLE partners
    ADD CONSTRAINT uk_partners_auth_user UNIQUE (auth_user_id);

ALTER TABLE partners
    ADD CONSTRAINT fk_partners_auth_user
        FOREIGN KEY (auth_user_id) REFERENCES auth_users (id);
