CREATE DATABASE salaries
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1;

CREATE TABLE data_record (
  id BIGSERIAL PRIMARY KEY,
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
  record_date DATE NOT NULL
);
