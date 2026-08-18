ALTER TABLE sellable_stocks RENAME COLUMN enabled TO active;
ALTER TABLE sellable_stocks DROP CONSTRAINT IF EXISTS ck_sellable_stocks_quantity;
ALTER TABLE sellable_stocks DROP COLUMN IF EXISTS available_quantity;
