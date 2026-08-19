ALTER TABLE purchases
    ADD COLUMN payer_type VARCHAR(20);

UPDATE purchases
SET payer_type = 'PARTNER'
WHERE payer_type IS NULL;

ALTER TABLE purchases
    ALTER COLUMN payer_type SET NOT NULL;

ALTER TABLE purchases
    ADD CONSTRAINT ck_purchases_payer_type CHECK (payer_type IN ('BRATATOUILLE', 'PARTNER'));

ALTER TABLE purchases
    ALTER COLUMN paid_by_partner_id DROP NOT NULL;
