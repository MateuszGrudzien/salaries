package pl.solidlabs.salaries.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Table(name = "data_record")
@NoArgsConstructor @Getter @Setter
public class DataRecord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "position")
    private String position;

    @Column(name = "age")
    private Integer age;

    @Column(name = "months_of_experience")
    private Integer monthsOfExperience;

    @Column(name = "contract_type")
    @Enumerated(value = EnumType.STRING)
    private ContractType contractType;

    @Column(name = "monthly_gross_salary")
    private BigDecimal monthlyGrossSalary;

    @Column(name = "currency")
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Column(name = "company")
    private String company;

    @Column(name = "company_size_range")
    private String companySizeRange;

    @Column(name = "tech_stack")
    private String techStack;

    @Column(name = "record_date")
    private Date recordDate;

    public DataRecord(DataRecordDto dto) {
        this.id = dto.getId();
        this.city = dto.getCity();
        this.position = dto.getPosition();
        this.age = dto.getAge();
        this.monthsOfExperience = dto.getMonthsOfExperience();
        this.contractType = dto.getContractType();
        this.monthlyGrossSalary = dto.getMonthlyGrossSalary();
        this.currency = dto.getCurrency();
        this.company = dto.getCompany();
        this.companySizeRange = dto.getCompanySizeRange();
        this.techStack = dto.getTechStack();
        this.recordDate = dto.getRecordDate();
    }
}
