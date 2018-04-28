package maloney.jack.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import maloney.jack.domain.Account;
import maloney.jack.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class AccountControllerTest {

    @Mock
    AccountRepository repository;

    AccountController controller;

    Account account1;
    Account account2;

    @Before
    public void setup() {
        initMocks(this);
        controller = new AccountController(repository);

        account1 = new Account("Harry", "Williams", 12345678);
        account2 = new Account("Mary", "Piper", 54545454);
    }

    @Test
    public void givenAGetAllAccountsRequest_returnAllAccounts() {
        List<Account> accountList = new ArrayList();
        accountList.add(account1);
        accountList.add(account2);

        when(repository.findAll()).thenReturn(accountList);

        List<Account> result = (List<Account>) controller.findAll();

        assertEquals(accountList, result);
    }

    @Test
    public void givenAPostAccountRequest_createTheAccount() {
        when(repository.save(account1)).thenReturn(account1);

        String result = controller.save(account1);

        assertEquals("Account has been successfully added", result);
    }

    @Test
    public void givenADeleteAccountRequest_deleteTheGivenAccount() {
        doNothing().when(repository).delete(account1);

        String result = controller.delete(account1.getId());

        assertEquals("Account successfully deleted", result);
    }
}
