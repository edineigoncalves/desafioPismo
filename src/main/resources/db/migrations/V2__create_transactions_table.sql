
CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    operation_type_id INTEGER NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    event_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);
