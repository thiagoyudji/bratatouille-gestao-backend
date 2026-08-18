ALTER TABLE recipes ADD COLUMN yield_quantity NUMERIC(19, 3);
UPDATE recipes SET yield_quantity = 1 WHERE yield_quantity IS NULL;
ALTER TABLE recipes ALTER COLUMN yield_quantity SET NOT NULL;
