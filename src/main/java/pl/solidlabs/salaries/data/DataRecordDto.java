package pl.solidlabs.salaries.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class DataRecordDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "city", required = true)
    private String city;

    @JsonProperty(value = "position", required = true)
    private String position;

    @JsonProperty(value = "age")
    private Integer age;

    @JsonProperty(value = "months_of_experience", required = true)
    private Integer monthsOfExperience;

    @JsonProperty(value = "contract_type", required = true)
    private ContractType contractType;

    @JsonProperty(value = "monthly_gross_salary", required = true)
    private BigDecimal monthlyGrossSalary;

    @JsonProperty(value = "currency", required = true)
    private Currency currency;

    @JsonProperty(value = "company")
    private String company;

    @JsonProperty(value = "company_size_range")
    private String companySizeRange;

    @JsonProperty(value = "tech_stack")
    private String techStack;

    @JsonProperty(value = "record_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

    public DataRecordDto(DataRecord entity) {
        this.id = entity.getId();
        this.city = entity.getCity();
        this.position = entity.getPosition();
        this.age = entity.getAge();
        this.monthsOfExperience = entity.getMonthsOfExperience();
        this.contractType = entity.getContractType();
        this.monthlyGrossSalary = entity.getMonthlyGrossSalary();
        this.currency = entity.getCurrency();
        this.company = entity.getCompany();
        this.companySizeRange = entity.getCompanySizeRange();
        this.techStack = entity.getTechStack();
        this.recordDate = entity.getRecordDate();
    }
}
