package maloney.jack.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import maloney.jack.controller.AccountController;
import maloney.jack.domain.Account;
import maloney.jack.repository.AccountRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountApiIntegrationTest {

    MockMvc mockMvc;

    AccountController controller;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setup() {
        controller = new AccountController(accountRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void givenAGetAccountsRequest_shouldReturnJsonForAllAccounts() throws Exception {
        MockHttpServletRequestBuilder request = get("/accounts").contentType(APPLICATION_JSON);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void givenAValidPostAccountRequest_shouldReturnASuccessMessage() throws Exception {
        String jsonString = "{\"firstName\" : \"Harry\", \"secondName\" : \"William\", \"accountNumber\" : \"1233456\"}";

        MockHttpServletRequestBuilder request = post("/accounts").contentType(APPLICATION_JSON).content(jsonString);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(((MockHttpServletResponse) response).getContentAsString()).isEqualTo("Account has been successfully added");
    }

    @Test
    public void givenAnInvalidPostAccountRequest_shouldReturnA400StatusWithErrorMessage() throws Exception {
        String jsonString = "{\"invalid\" : \"Harry\"}";

        MockHttpServletRequestBuilder request = post("/accounts").contentType(APPLICATION_JSON).content(jsonString);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(((MockHttpServletResponse) response).getErrorMessage()).isEqualTo("Account could not be added: given Json is invalid");
    }

    @Test
    public void givenADeleteAccountRequest_shouldReturnASuccessMessage() throws Exception {
        String jsonString = "{\"firstName\" : \"Harry\", \"secondName\" : \"William\", \"accountNumber\" : \"1233456\"}";

        MockHttpServletRequestBuilder createRequest = post("/accounts").contentType(APPLICATION_JSON).content(jsonString);
        mockMvc.perform(createRequest).andReturn().getResponse();

        Iterable<Account> accountList = controller.findAll();

        Account account = Lists.newArrayList(accountList).get(0);

        MockHttpServletRequestBuilder request = delete("/accounts/" + account.getId()).contentType(APPLICATION_JSON);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(((MockHttpServletResponse) response).getContentAsString()).isEqualTo("Account successfully deleted");
    }

    @Test
    public void givenADeleteAccountRequestForAnAccountThatDoesNotExist_shouldReturnA400StatusWithErrorMessage() throws Exception {
        MockHttpServletRequestBuilder request = delete("/accounts/2").contentType(APPLICATION_JSON);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(((MockHttpServletResponse) response).getErrorMessage()).isEqualTo("Account could not be deleted: given Account ID does not exist");
    }
}