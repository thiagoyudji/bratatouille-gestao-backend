CREATE TABLE auth_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(80) NOT NULL,
    password_hash VARCHAR(120) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP(6),
    CONSTRAINT uk_auth_users_username UNIQUE (username),
    CONSTRAINT ck_auth_users_role CHECK (role IN ('ADMIN', 'EMPLOYEE', 'CUSTOMER'))
);

CREATE TABLE customer_profiles (
    id BIGSERIAL PRIMARY KEY,
    auth_user_id BIGINT NOT NULL,
    customer_type VARCHAR(2) NOT NULL,
    full_name VARCHAR(160) NOT NULL,
    email VARCHAR(160) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    CONSTRAINT uk_customer_profiles_auth_user UNIQUE (auth_user_id),
    CONSTRAINT fk_customer_profiles_auth_user
        FOREIGN KEY (auth_user_id) REFERENCES auth_users (id),
    CONSTRAINT ck_customer_profiles_type CHECK (customer_type IN ('PF', 'PJ'))
);

CREATE TABLE customer_profile_addresses (
    customer_profile_id BIGINT NOT NULL,
    address_order INTEGER NOT NULL,
    label VARCHAR(80),
    zip_code VARCHAR(20),
    street VARCHAR(120),
    number VARCHAR(40),
    neighborhood VARCHAR(120),
    state VARCHAR(2),
    city VARCHAR(120),
    complement VARCHAR(120),
    default_address BOOLEAN NOT NULL,
    CONSTRAINT pk_customer_profile_addresses
        PRIMARY KEY (customer_profile_id, address_order),
    CONSTRAINT fk_customer_profile_addresses_profile
        FOREIGN KEY (customer_profile_id) REFERENCES customer_profiles (id) ON DELETE CASCADE
);

CREATE TABLE partners (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    active BOOLEAN,
    default_split_percentage NUMERIC(5, 2) NOT NULL,
    created_at TIMESTAMP(6),
    CONSTRAINT ck_partners_default_split_percentage
        CHECK (default_split_percentage BETWEEN 0 AND 100)
);

CREATE TABLE partner_roles (
    partner_id BIGINT NOT NULL,
    role VARCHAR(255) NOT NULL,
    CONSTRAINT pk_partner_roles PRIMARY KEY (partner_id, role),
    CONSTRAINT fk_partner_roles_partner
        FOREIGN KEY (partner_id) REFERENCES partners (id) ON DELETE CASCADE,
    CONSTRAINT ck_partner_roles_role CHECK (role IN ('ADMIN', 'PRODUCER', 'VIEWER', 'EDITOR'))
);

CREATE TABLE items (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(255),
    base_unit VARCHAR(255),
    active BOOLEAN,
    low_stock_threshold NUMERIC(19, 3),
    critical_stock_threshold NUMERIC(19, 3),
    price_pf NUMERIC(19, 6),
    price_pj NUMERIC(19, 6),
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    CONSTRAINT ck_items_type CHECK (type IN ('INGREDIENT', 'PACKAGING', 'FINISHED_PRODUCT')),
    CONSTRAINT ck_items_base_unit CHECK (base_unit IN ('G', 'ML', 'UN')),
    CONSTRAINT ck_items_thresholds_non_negative CHECK (
        (low_stock_threshold IS NULL OR low_stock_threshold >= 0)
        AND (critical_stock_threshold IS NULL OR critical_stock_threshold >= 0)
    ),
    CONSTRAINT ck_items_threshold_order CHECK (
        low_stock_threshold IS NULL
        OR critical_stock_threshold IS NULL
        OR critical_stock_threshold <= low_stock_threshold
    ),
    CONSTRAINT ck_items_prices_non_negative CHECK (
        (price_pf IS NULL OR price_pf >= 0)
        AND (price_pj IS NULL OR price_pj >= 0)
    )
);

CREATE TABLE stocks (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    quantity NUMERIC(19, 3) NOT NULL,
    price_pf NUMERIC(19, 6),
    price_pj NUMERIC(19, 6),
    CONSTRAINT uk_stock_item UNIQUE (item_id),
    CONSTRAINT fk_stocks_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_stocks_quantity CHECK (quantity >= 0),
    CONSTRAINT ck_stocks_prices_non_negative CHECK (
        (price_pf IS NULL OR price_pf >= 0)
        AND (price_pj IS NULL OR price_pj >= 0)
    )
);

CREATE TABLE stock_movements (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    quantity NUMERIC(19, 3) NOT NULL,
    type VARCHAR(255) NOT NULL,
    source_id BIGINT,
    created_at TIMESTAMP(6),
    CONSTRAINT fk_stock_movements_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_stock_movements_quantity CHECK (quantity <> 0),
    CONSTRAINT ck_stock_movements_type CHECK (type IN (
        'PURCHASE_ENTRY',
        'PRODUCTION_CONSUMPTION',
        'PRODUCTION_OUTPUT',
        'MANUAL_ADJUSTMENT',
        'SALE_OUTPUT',
        'LOSS_OUTPUT',
        'ZERO_COST_ENTRY'
    )),
    CONSTRAINT ck_stock_movements_source CHECK (
        type = 'MANUAL_ADJUSTMENT' OR source_id IS NOT NULL
    )
);

CREATE TABLE zero_cost_entry (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    quantity NUMERIC(19, 3) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    note VARCHAR(255),
    created_at TIMESTAMP(6),
    CONSTRAINT fk_zero_cost_entry_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_zero_cost_entry_quantity CHECK (quantity > 0),
    CONSTRAINT ck_zero_cost_entry_reason
        CHECK (reason IN ('DONATION', 'INITIAL_STOCK', 'MANUAL_ADJUSTMENT'))
);

CREATE TABLE recipes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    output_item_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    CONSTRAINT fk_recipes_output_item FOREIGN KEY (output_item_id) REFERENCES items (id)
);

CREATE TABLE recipe_items (
    id BIGSERIAL PRIMARY KEY,
    recipe_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity NUMERIC(19, 3) NOT NULL,
    yield_percentage NUMERIC(5, 4) NOT NULL,
    CONSTRAINT uk_recipe_items_recipe_item UNIQUE (recipe_id, item_id),
    CONSTRAINT fk_recipe_items_recipe
        FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE,
    CONSTRAINT fk_recipe_items_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_recipe_items_quantity CHECK (quantity > 0),
    CONSTRAINT ck_recipe_items_yield CHECK (yield_percentage > 0 AND yield_percentage <= 1)
);

CREATE TABLE purchases (
    id BIGSERIAL PRIMARY KEY,
    purchase_date DATE NOT NULL,
    paid_by_partner_id BIGINT NOT NULL,
    total_amount NUMERIC(19, 2) NOT NULL,
    note VARCHAR(255),
    created_at TIMESTAMP(6),
    supplier VARCHAR(255) NOT NULL,
    CONSTRAINT fk_purchases_paid_by_partner
        FOREIGN KEY (paid_by_partner_id) REFERENCES partners (id),
    CONSTRAINT ck_purchases_total_amount CHECK (total_amount > 0)
);

CREATE TABLE purchase_items (
    id BIGSERIAL PRIMARY KEY,
    purchase_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity NUMERIC(19, 3) NOT NULL,
    unit VARCHAR(255) NOT NULL,
    total_value NUMERIC(19, 2) NOT NULL,
    CONSTRAINT uk_purchase_items_purchase_item UNIQUE (purchase_id, item_id),
    CONSTRAINT fk_purchase_items_purchase
        FOREIGN KEY (purchase_id) REFERENCES purchases (id) ON DELETE CASCADE,
    CONSTRAINT fk_purchase_items_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_purchase_items_quantity CHECK (quantity > 0),
    CONSTRAINT ck_purchase_items_unit CHECK (unit IN ('G', 'ML', 'UN')),
    CONSTRAINT ck_purchase_items_total_value CHECK (total_value > 0)
);

CREATE TABLE purchase_splits (
    id BIGSERIAL PRIMARY KEY,
    purchase_id BIGINT NOT NULL,
    partner_id BIGINT NOT NULL,
    percentage NUMERIC(5, 2) NOT NULL,
    owed_amount NUMERIC(19, 2) NOT NULL,
    CONSTRAINT uk_purchase_splits_purchase_partner UNIQUE (purchase_id, partner_id),
    CONSTRAINT fk_purchase_splits_purchase
        FOREIGN KEY (purchase_id) REFERENCES purchases (id) ON DELETE CASCADE,
    CONSTRAINT fk_purchase_splits_partner FOREIGN KEY (partner_id) REFERENCES partners (id),
    CONSTRAINT ck_purchase_splits_percentage CHECK (percentage > 0 AND percentage <= 100),
    CONSTRAINT ck_purchase_splits_owed_amount CHECK (owed_amount > 0)
);

CREATE TABLE productions (
    id BIGSERIAL PRIMARY KEY,
    recipe_id BIGINT NOT NULL,
    production_date DATE NOT NULL,
    produced_quantity NUMERIC(19, 3) NOT NULL,
    total_cost NUMERIC(19, 6) NOT NULL,
    unit_cost NUMERIC(19, 6) NOT NULL,
    created_at TIMESTAMP(6),
    CONSTRAINT fk_productions_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (id),
    CONSTRAINT ck_productions_quantity CHECK (produced_quantity > 0),
    CONSTRAINT ck_productions_costs CHECK (total_cost >= 0 AND unit_cost >= 0)
);

CREATE TABLE production_items (
    id BIGSERIAL PRIMARY KEY,
    production_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    consumed_quantity NUMERIC(19, 3) NOT NULL,
    usable_quantity NUMERIC(19, 3) NOT NULL,
    loss_quantity NUMERIC(19, 3) NOT NULL,
    yield_percentage NUMERIC(5, 4) NOT NULL,
    unit_cost NUMERIC(19, 6) NOT NULL,
    total_cost NUMERIC(19, 6) NOT NULL,
    CONSTRAINT uk_production_items_production_item UNIQUE (production_id, item_id),
    CONSTRAINT fk_production_items_production
        FOREIGN KEY (production_id) REFERENCES productions (id) ON DELETE CASCADE,
    CONSTRAINT fk_production_items_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_production_items_quantities CHECK (
        consumed_quantity > 0 AND usable_quantity > 0 AND loss_quantity >= 0
    ),
    CONSTRAINT ck_production_items_yield CHECK (yield_percentage > 0 AND yield_percentage <= 1),
    CONSTRAINT ck_production_items_costs CHECK (unit_cost >= 0 AND total_cost >= 0)
);

CREATE TABLE lots (
    id BIGSERIAL PRIMARY KEY,
    production_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    production_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    quantity NUMERIC(19, 3) NOT NULL,
    created_at TIMESTAMP(6),
    CONSTRAINT uk_lots_production UNIQUE (production_id),
    CONSTRAINT fk_lots_production FOREIGN KEY (production_id) REFERENCES productions (id),
    CONSTRAINT fk_lots_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_lots_quantity CHECK (quantity > 0),
    CONSTRAINT ck_lots_expiration CHECK (expiration_date >= production_date)
);

CREATE TABLE operational_costs (
    id BIGSERIAL PRIMARY KEY,
    cost_date DATE NOT NULL,
    category VARCHAR(255) NOT NULL,
    paid_by_partner_id BIGINT NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP(6),
    CONSTRAINT fk_operational_costs_paid_by_partner
        FOREIGN KEY (paid_by_partner_id) REFERENCES partners (id),
    CONSTRAINT ck_operational_costs_category CHECK (category IN ('FIXED', 'VARIABLE', 'FINANCIAL')),
    CONSTRAINT ck_operational_costs_amount CHECK (amount > 0)
);

CREATE TABLE operational_cost_splits (
    id BIGSERIAL PRIMARY KEY,
    operational_cost_id BIGINT NOT NULL,
    partner_id BIGINT NOT NULL,
    owed_amount NUMERIC(19, 2) NOT NULL,
    CONSTRAINT uk_operational_cost_splits_cost_partner
        UNIQUE (operational_cost_id, partner_id),
    CONSTRAINT fk_operational_cost_splits_cost
        FOREIGN KEY (operational_cost_id) REFERENCES operational_costs (id) ON DELETE CASCADE,
    CONSTRAINT fk_operational_cost_splits_partner
        FOREIGN KEY (partner_id) REFERENCES partners (id),
    CONSTRAINT ck_operational_cost_splits_owed_amount CHECK (owed_amount > 0)
);

CREATE TABLE operational_losses (
    id BIGSERIAL PRIMARY KEY,
    loss_date DATE NOT NULL,
    item_id BIGINT NOT NULL,
    quantity NUMERIC(19, 3) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    unit_cost NUMERIC(19, 6) NOT NULL,
    total_cost NUMERIC(19, 6) NOT NULL,
    note VARCHAR(255),
    created_at TIMESTAMP(6),
    CONSTRAINT fk_operational_losses_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_operational_losses_quantity CHECK (quantity > 0),
    CONSTRAINT ck_operational_losses_reason CHECK (reason IN ('DAMAGE', 'OPERATIONAL', 'EXPIRED')),
    CONSTRAINT ck_operational_losses_costs CHECK (unit_cost > 0 AND total_cost > 0)
);

CREATE TABLE sellable_stocks (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    available_quantity NUMERIC(19, 3) NOT NULL,
    infinite BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL,
    price_pf NUMERIC(19, 6),
    price_pj NUMERIC(19, 6),
    CONSTRAINT uk_sellable_stock_item UNIQUE (item_id),
    CONSTRAINT fk_sellable_stocks_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_sellable_stocks_quantity CHECK (infinite OR available_quantity >= 0),
    CONSTRAINT ck_sellable_stocks_prices_non_negative CHECK (
        (price_pf IS NULL OR price_pf >= 0)
        AND (price_pj IS NULL OR price_pj >= 0)
    )
);

CREATE TABLE sales_orders (
    id BIGSERIAL PRIMARY KEY,
    sale_date DATE NOT NULL,
    customer_type VARCHAR(10) NOT NULL,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    customer_phone VARCHAR(255),
    delivery_zip_code VARCHAR(255),
    delivery_street VARCHAR(255),
    delivery_number VARCHAR(255),
    delivery_neighborhood VARCHAR(255),
    delivery_state VARCHAR(255),
    delivery_city VARCHAR(255),
    delivery_complement VARCHAR(255),
    note VARCHAR(255),
    payment_status VARCHAR(20) NOT NULL,
    payment_provider VARCHAR(255),
    payment_provider_transaction_id VARCHAR(255) UNIQUE,
    payment_provider_status VARCHAR(255),
    payment_receipt_url VARCHAR(512),
    payment_checkout_url VARCHAR(512),
    payment_invoice_slug VARCHAR(255),
    paid_at TIMESTAMP(6),
    total_amount NUMERIC(19, 6) NOT NULL,
    total_cost NUMERIC(19, 6) NOT NULL,
    gross_profit NUMERIC(19, 6) NOT NULL,
    created_at TIMESTAMP(6),
    CONSTRAINT ck_sales_orders_customer_type CHECK (customer_type IN ('GUEST', 'PF', 'PJ')),
    CONSTRAINT ck_sales_orders_payment_status
        CHECK (payment_status IN ('PENDING', 'APPROVED', 'DECLINED', 'CANCELED')),
    CONSTRAINT ck_sales_orders_totals CHECK (total_amount > 0 AND total_cost >= 0)
);

CREATE TABLE sales_order_items (
    id BIGSERIAL PRIMARY KEY,
    sales_order_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity NUMERIC(19, 3) NOT NULL,
    unit_price NUMERIC(19, 6) NOT NULL,
    unit_price_pf NUMERIC(19, 6),
    unit_price_pj NUMERIC(19, 6),
    total_price NUMERIC(19, 6) NOT NULL,
    unit_cost NUMERIC(19, 6) NOT NULL,
    total_cost NUMERIC(19, 6) NOT NULL,
    gross_profit NUMERIC(19, 6) NOT NULL,
    cost_incomplete BOOLEAN NOT NULL,
    CONSTRAINT fk_sales_order_items_order
        FOREIGN KEY (sales_order_id) REFERENCES sales_orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_sales_order_items_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT ck_sales_order_items_quantity CHECK (quantity > 0),
    CONSTRAINT ck_sales_order_items_prices CHECK (
        unit_price > 0
        AND (unit_price_pf IS NULL OR unit_price_pf >= 0)
        AND (unit_price_pj IS NULL OR unit_price_pj >= 0)
        AND total_price > 0
    ),
    CONSTRAINT ck_sales_order_items_costs CHECK (unit_cost >= 0 AND total_cost >= 0)
);

CREATE TABLE financial_closings (
    id BIGSERIAL PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    summary_json TEXT NOT NULL,
    created_at TIMESTAMP(6),
    CONSTRAINT uk_financial_closings_period UNIQUE (start_date, end_date),
    CONSTRAINT ck_financial_closings_period CHECK (start_date <= end_date)
);

CREATE INDEX idx_customer_profiles_email ON customer_profiles (email);
CREATE INDEX idx_lots_item_expiration ON lots (item_id, expiration_date);
CREATE INDEX idx_lots_expiration ON lots (expiration_date);
CREATE INDEX idx_operational_costs_date ON operational_costs (cost_date);
CREATE INDEX idx_operational_losses_date ON operational_losses (loss_date);
CREATE INDEX idx_productions_recipe ON productions (recipe_id);
CREATE INDEX idx_purchase_items_item ON purchase_items (item_id);
CREATE INDEX idx_purchases_date ON purchases (purchase_date);
CREATE INDEX idx_sales_orders_date_status ON sales_orders (sale_date, payment_status);
CREATE INDEX idx_sales_order_items_item ON sales_order_items (item_id);
CREATE INDEX idx_stock_movements_created_at ON stock_movements (created_at DESC);
CREATE INDEX idx_stock_movements_item ON stock_movements (item_id);
CREATE INDEX idx_zero_cost_entry_item ON zero_cost_entry (item_id);
