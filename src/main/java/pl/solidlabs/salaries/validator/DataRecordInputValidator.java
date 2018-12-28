package pl.solidlabs.salaries.validator;

import pl.solidlabs.salaries.data.DataRecordDto;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;

public class DataRecordInputValidator {

    public static final String PROPERTY_EMPTY_OR_MISSING_TEMPLATE = "'%s' property empty or missing";

    public static List<String> validate(DataRecordDto input) {
        List<String> errors = new ArrayList<>();
        if (isBlank(input.getCity())) {
            errors.add(String.format(PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "city"));
        }
        if (isBlank(input.getPosition())) {
            errors.add(String.format(PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "position"));
        }
        if (input.getMonthsOfExperience() == null) {
            errors.add(String.format(PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "months_of_experience"));
        }
        if (input.getContractType() == null) {
            errors.add(String.format(PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "contract_type"));
        }
        if (input.getMonthlyGrossSalary() == null) {
            errors.add(String.format(PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "monthly_gross_salary"));
        }
        if (input.getCurrency() == null) {
            errors.add(String.format(PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "currency"));
        }

        return errors;
    }
}
