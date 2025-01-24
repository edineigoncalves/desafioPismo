CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,
    document_number VARCHAR(11) NOT NULL UNIQUE,
    CONSTRAINT document_number_unique UNIQUE (document_number)
);
