package pl.solidlabs.salaries.integration;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.LinkedList;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.solidlabs.salaries.configuration.UrlConfiguration.DATA_RECORD_URL;

@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
public class FetchingDataRecordIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Sql(statements = SqlStatements.INSERT_TEST_DATA_RECORDS)
    public void whenRequestedAllRecords_thenReturnsAllRecordsOrderedByIdDesc() throws Exception {
        JSONObject expectedObjectWithId1 = new JSONObject()
                .put("id", 1)
                .put("city", "Lublin")
                .put("position", "Java Developer")
                .put("age", 26)
                .put("months_of_experience", 36)
                .put("contract_type", "B2B")
                .put("monthly_gross_salary", 13500)
                .put("currency", "PLN")
                .put("company", "Company A")
                .put("company_size_range", "100-250")
                .put("tech_stack", "Java, Spring, Hibernate")
                .put("record_date", LocalDate.of(2018, 12, 31).format(ISO_LOCAL_DATE));
        JSONObject expectedObjectWithId2 = new JSONObject()
                .put("id", 2)
                .put("city", "Lublin")
                .put("position", "Senior Java Developer")
                .put("age", 30)
                .put("months_of_experience", 65)
                .put("contract_type", "B2B")
                .put("monthly_gross_salary", 18500)
                .put("currency", "PLN")
                .put("company", "Company B")
                .put("company_size_range", "100-250")
                .put("tech_stack", "Java, Spring")
                .put("record_date", LocalDate.of(2018, 12, 30).format(ISO_LOCAL_DATE));

        String expectedContent = new JSONArray(new LinkedList(asList(expectedObjectWithId2, expectedObjectWithId1)))
                .toString();

        mvc.perform(get(DATA_RECORD_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedContent));
    }
}
