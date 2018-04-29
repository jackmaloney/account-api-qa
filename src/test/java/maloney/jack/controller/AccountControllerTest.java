package maloney.jack.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import maloney.jack.domain.Account;
import maloney.jack.exception.DeleteAccountException;
import maloney.jack.exception.InvalidAccountJsonException;
import maloney.jack.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

public class AccountControllerTest {

    @Mock
    AccountRepository accountRepository;

    AccountController controller;

    Account account1;
    Account account2;

    @Before
    public void setup() {
        initMocks(this);
        controller = new AccountController(accountRepository);

        account1 = new Account("Harry", "Williams", 12345678);
        account2 = new Account("Mary", "Piper", 54545454);
    }

    @Test
    public void givenAGetAllAccountsRequest_returnAllAccounts() {
        List<Account> accountList = new ArrayList();
        accountList.add(account1);
        accountList.add(account2);

        when(accountRepository.findAll()).thenReturn(accountList);

        List<Account> result = (List<Account>) controller.findAll();

        assertEquals(accountList, result);
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    public void givenAPostAccountRequest_createTheAccount() throws InvalidAccountJsonException {
        when(accountRepository.save(account1)).thenReturn(account1);

        ResponseEntity<String> result = controller.save(account1);

        assertEquals("Account has been successfully added", result.getBody());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    public void givenADeleteAccountRequest_deleteTheGivenAccount() throws DeleteAccountException {
        account1.setId("1");

        when(accountRepository.findById("1")).thenReturn(Optional.of(account1));

        doNothing().when(accountRepository).delete(account1);

        ResponseEntity<String> result = controller.delete(account1.getId());

        assertEquals("Account successfully deleted", result.getBody());
        verify(accountRepository, times(1)).deleteById("1");
    }
}
