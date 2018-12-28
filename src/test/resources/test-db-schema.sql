CREATE TABLE PUBLIC.DATA_RECORD(
    id INTEGER PRIMARY KEY,
    city VARCHAR(200) NOT NULL,
    position VARCHAR(300) NOT NULL,
    age INTEGER,
    months_of_experience INTEGER NOT NULL,
    contract_type VARCHAR(50) NOT NULL,
    monthly_gross_salary NUMERIC NOT NULL,
    currency VARCHAR(5) NOT NULL,
    company VARCHAR(300),
    company_size_range VARCHAR(50),
    tech_stack VARCHAR,
    record_date DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE DATA_RECORD_ID_SEQ START WITH 1;