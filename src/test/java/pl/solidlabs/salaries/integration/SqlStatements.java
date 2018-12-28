package pl.solidlabs.salaries.integration;

public class SqlStatements {

    public static final String INSERT_TEST_DATA_RECORDS =
            "INSERT INTO data_record (id, city, position, age, months_of_experience, contract_type, monthly_gross_salary, currency, company, company_size_range, tech_stack, record_date) VALUES " +
            "(1, 'Lublin', 'Java Developer', 26, 36, 'B2B', 13500, 'PLN', 'Company A', '100-250', 'Java, Spring, Hibernate', PARSEDATETIME('12-31-2018 08:00:00','MM-dd-yyyy HH:mm:ss'));" +
            "INSERT INTO data_record (id, city, position, age, months_of_experience, contract_type, monthly_gross_salary, currency, company, company_size_range, tech_stack, record_date) VALUES " +
            "(2, 'Lublin', 'Senior Java Developer', 30, 65, 'B2B', 18500, 'PLN', 'Company B', '100-250', 'Java, Spring', PARSEDATETIME('12-30-2018 08:00:00','MM-dd-yyyy HH:mm:ss'));";

}
