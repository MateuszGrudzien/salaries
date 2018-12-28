package pl.solidlabs.salaries.integration;

import org.apache.commons.lang.time.DateUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.solidlabs.salaries.data.Currency;
import pl.solidlabs.salaries.data.DataRecord;
import pl.solidlabs.salaries.validator.DataRecordInputValidator;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.Assert.notNull;
import static pl.solidlabs.salaries.configuration.UrlConfiguration.DATA_RECORD_URL;

@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
public class AddingDataRecordIntegrationTests {

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    private Long id = 0L;
    private Date recordDate = new Date();
    private String city = "Lublin";
    private String position = "Java Developer";
    private Integer age = 26;
    private Integer monthsOfExperience = 36;
    private String contractType = "B2B";
    private BigDecimal monthlyGrossSalary = new BigDecimal(13500);
    private Currency currency = Currency.PLN;
    private String company = "Company A";
    private String companySizeRange = "100-250";
    private String techStack = "Java, Spring, Hibernate";

    @Test
    public void whenInputCorrect_thenDataIsPersisted() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("city", city)
                .put("position", position)
                .put("age", age)
                .put("months_of_experience", monthsOfExperience)
                .put("contract_type", contractType)
                .put("monthly_gross_salary", monthlyGrossSalary)
                .put("currency", currency.name())
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();
        String expectedOutput = json
                .put("id", id)
                .put("record_date", dateFormatter.format(recordDate))
                .toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(input))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutput));

        DataRecord persistedRecord = entityManager.find(DataRecord.class, id);
        notNull(persistedRecord, "Persisting data record to database failed!");
        notNull(persistedRecord.getId(), "Persisted record does not contain id!");
        notNull(persistedRecord.getCity(), "Persisted record does not contain city!");
        notNull(persistedRecord.getPosition(), "Persisted record does not contain position!");
        notNull(persistedRecord.getAge(), "Persisted record does not contain age!");
        notNull(persistedRecord.getMonthsOfExperience(), "Persisted record does not contain months of experience!");
        notNull(persistedRecord.getContractType(), "Persisted record does not contain contract type!");
        notNull(persistedRecord.getMonthlyGrossSalary(), "Persisted record does not contain monthly gross salary!");
        notNull(persistedRecord.getCurrency(), "Persisted record does not contain currency!");
        notNull(persistedRecord.getCompany(), "Persisted record does not contain company!");
        notNull(persistedRecord.getCompanySizeRange(), "Persisted record does not contain company size range!");
        notNull(persistedRecord.getTechStack(), "Persisted record does not contain tech stack!");
        notNull(persistedRecord.getRecordDate(), "Persisted record does not contain record date!");
        assertEquals(id, persistedRecord.getId());
        assertEquals(city, persistedRecord.getCity());
        assertEquals(position, persistedRecord.getPosition());
        assertEquals(age, persistedRecord.getAge());
        assertEquals(monthsOfExperience, persistedRecord.getMonthsOfExperience());
        assertEquals(contractType, persistedRecord.getContractType().getName());
        assertEquals(monthlyGrossSalary, persistedRecord.getMonthlyGrossSalary());
        assertEquals(currency, persistedRecord.getCurrency());
        assertEquals(company, persistedRecord.getCompany());
        assertEquals(companySizeRange, persistedRecord.getCompanySizeRange());
        assertEquals(techStack, persistedRecord.getTechStack());
        assertEquals(true, DateUtils.isSameDay(persistedRecord.getRecordDate(), recordDate));
    }

    @Test
    public void whenInputLacksCityProperty_thenReturnBadRequest() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("position", position)
                .put("age", age)
                .put("months_of_experience", monthsOfExperience)
                .put("contract_type", contractType)
                .put("monthly_gross_salary", monthlyGrossSalary)
                .put("currency", currency.name())
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();

        String expectedContent = new JSONObject()
                .put("error", String.format(DataRecordInputValidator.PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "city"))
                .toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(input))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedContent));
    }

    @Test
    public void whenInputLacksPositionProperty_thenReturnBadRequest() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("city", city)
                .put("age", age)
                .put("months_of_experience", monthsOfExperience)
                .put("contract_type", contractType)
                .put("monthly_gross_salary", monthlyGrossSalary)
                .put("currency", currency.name())
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();

        String expectedContent = new JSONObject()
                .put("error", String.format(DataRecordInputValidator.PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "position"))
                .toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(input))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedContent));
    }

    @Test
    public void whenInputLacksMonthsOfExperienceProperty_thenReturnBadRequest() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("city", city)
                .put("position", position)
                .put("age", age)
                .put("contract_type", contractType)
                .put("monthly_gross_salary", monthlyGrossSalary)
                .put("currency", currency.name())
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();

        String expectedContent = new JSONObject()
                .put("error", String.format(DataRecordInputValidator.PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "months_of_experience"))
                .toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(input))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedContent));
    }

    @Test
    public void whenInputLacksContractTypeProperty_thenReturnBadRequest() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("city", city)
                .put("position", position)
                .put("age", age)
                .put("months_of_experience", monthsOfExperience)
                .put("monthly_gross_salary", monthlyGrossSalary)
                .put("currency", currency.name())
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();

        String expectedContent = new JSONObject()
                .put("error", String.format(DataRecordInputValidator.PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "contract_type"))
                .toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(input))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedContent));
    }

    @Test
    public void whenInputLacksMonthlyGrossSalaryProperty_thenReturnBadRequest() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("city", city)
                .put("position", position)
                .put("age", age)
                .put("months_of_experience", monthsOfExperience)
                .put("contract_type", contractType)
                .put("currency", currency.name())
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();

        String expectedContent = new JSONObject()
                .put("error", String.format(DataRecordInputValidator.PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "monthly_gross_salary"))
                .toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(input))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedContent));
    }

    @Test
    public void whenInputLacksCurrencyProperty_thenReturnBadRequest() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("city", city)
                .put("position", position)
                .put("age", age)
                .put("months_of_experience", monthsOfExperience)
                .put("contract_type", contractType)
                .put("monthly_gross_salary", monthlyGrossSalary)
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();

        String expectedContent = new JSONObject()
                .put("error", String.format(DataRecordInputValidator.PROPERTY_EMPTY_OR_MISSING_TEMPLATE, "currency"))
                .toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(input))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedContent));
    }

    @Test
    public void whenContractTypeHasUnrecognizedValue_thenReturnBadRequest() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("city", city)
                .put("position", position)
                .put("age", age)
                .put("months_of_experience", monthsOfExperience)
                .put("contract_type", "B@B")
                .put("monthly_gross_salary", monthlyGrossSalary)
                .put("currency", currency.name())
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(input))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCurrencyHasUnrecognizedValue_thenReturnBadRequest() throws Exception {
        // given
        JSONObject json = new JSONObject()
                .put("city", city)
                .put("position", position)
                .put("age", age)
                .put("months_of_experience", monthsOfExperience)
                .put("contract_type", contractType)
                .put("monthly_gross_salary", monthlyGrossSalary)
                .put("currency", "PLNN")
                .put("company", company)
                .put("company_size_range", companySizeRange)
                .put("tech_stack", techStack);

        String input = json.toString();

        // then
        mvc.perform(
                post(DATA_RECORD_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(input))
                .andExpect(status().isBadRequest());
    }
}
