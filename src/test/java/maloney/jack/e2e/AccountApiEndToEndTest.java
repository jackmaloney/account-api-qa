package maloney.jack.e2e;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import maloney.jack.controller.AccountController;
import maloney.jack.domain.Account;
import maloney.jack.repository.AccountRepository;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountApiEndToEndTest {

    MockMvc mockMvc;

    AccountController controller;

    @Autowired
    AccountRepository accountRepository;

    ObjectMapper mapper = new ObjectMapper();


    @Before
    public void setup() {
        controller = new AccountController(accountRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void givenTwoAccounts_createInDatabaseThenReturnThenDelete() throws Exception {
        Account account1 = new Account("Harry", "William" , 1233456);
        Account account2 = new Account("Wendy", "Piper" , 5454545);

        //Create
        MockHttpServletRequestBuilder postRequest1 = post("/accounts").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(account1));
        MockHttpServletRequestBuilder postRequest2 = post("/accounts").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(account2));

        mockMvc.perform(postRequest1).andReturn().getResponse();
        mockMvc.perform(postRequest2).andReturn().getResponse();

        //Find all
        List<Account> accounts = findAllCases();

        assertEquals(2, accounts.size());

        assertEquals(account1.getFirstName(), accounts.get(0).getFirstName());
        assertEquals(account1.getSecondName(), accounts.get(0).getSecondName());
        assertEquals(account1.getAccountNumber(), accounts.get(0).getAccountNumber());

        assertEquals(account2.getFirstName(), accounts.get(1).getFirstName());
        assertEquals(account2.getSecondName(), accounts.get(1).getSecondName());
        assertEquals(account2.getAccountNumber(), accounts.get(1).getAccountNumber());

        //Delete
        MockHttpServletRequestBuilder deleteRequest = delete("/accounts/" + accounts.get(1).getId()).contentType(APPLICATION_JSON);

        mockMvc.perform(deleteRequest).andReturn().getResponse();

        //Find all
        accounts = findAllCases();

        assertEquals(1, accounts.size());

        assertEquals(account1.getFirstName(), accounts.get(0).getFirstName());
        assertEquals(account1.getSecondName(), accounts.get(0).getSecondName());
        assertEquals(account1.getAccountNumber(), accounts.get(0).getAccountNumber());
        //Convert response to 2 accounts
    }

    private List<Account> findAllCases() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/accounts").contentType(APPLICATION_JSON);

        String responseContent = mockMvc.perform(getRequest).andReturn().getResponse().getContentAsString();

        List<Account> accounts = new ArrayList<>();
        JSONArray jArray = new JSONArray(responseContent);
        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++){
                Account resultAccount = (Account) convertJSONStringToObject(jArray.getString(i), Account.class);
                accounts.add(resultAccount);
            }
        }

        return accounts;
    }

    private static <T>  Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        return mapper.readValue(json, objectClass);
    }
}

