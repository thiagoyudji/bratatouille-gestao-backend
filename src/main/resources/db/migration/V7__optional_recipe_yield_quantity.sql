-- Null signals no reference yield; do not fabricate a quantity.
ALTER TABLE recipes ALTER COLUMN yield_quantity DROP NOT NULL;
