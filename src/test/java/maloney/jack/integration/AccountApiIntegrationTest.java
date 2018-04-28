package maloney.jack.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import maloney.jack.controller.AccountController;
import maloney.jack.domain.Account;
import maloney.jack.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountApiIntegrationTest {

    MockMvc mockMvc;

    AccountController controller;

    @MockBean
    AccountRepository repository;

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        controller = new AccountController(repository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void givenAGetAccountsRequest_shouldReturnJsonForAllAccounts() throws Exception {
        MockHttpServletRequestBuilder request = get("/accounts").contentType(APPLICATION_JSON);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(repository, times(1)).findAll();
    }

    @Test
    public void givenAValidPostAccountRequest_shouldReturnASuccessMessage() throws Exception {
        String jsonString = "{\"firstName\" : \"Harry\", \"secondName\" : \"William\", \"accountNumber\" : \"1233456\"}";

        Account account = mapper.readValue(jsonString, Account.class);

        MockHttpServletRequestBuilder request = post("/accounts").contentType(APPLICATION_JSON).content(jsonString);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(repository, times(1)).save(account);
    }

    @Test
    public void givenAnInvalidPostAccountRequest_shouldReturnA400Status() throws Exception {
        String jsonString = "{\"invalid\" : \"Harry\"}";

        MockHttpServletRequestBuilder request = post("/accounts").contentType(APPLICATION_JSON).content(jsonString);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void givenADeleteAccountRequest_shouldReturnASuccessMessage() throws Exception {
        MockHttpServletRequestBuilder request = delete("/accounts/1").contentType(APPLICATION_JSON);

        HttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(repository, times(1)).deleteById("1");
    }
}