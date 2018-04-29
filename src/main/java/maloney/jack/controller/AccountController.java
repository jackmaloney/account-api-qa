package maloney.jack.controller;

import static org.springframework.http.ResponseEntity.ok;

import maloney.jack.domain.Account;
import maloney.jack.exception.DeleteAccountException;
import maloney.jack.exception.InvalidAccountJsonException;
import maloney.jack.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method= RequestMethod.GET, value="/accounts", produces= MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, value="/accounts")
    public ResponseEntity<String> save(@RequestBody Account account) throws InvalidAccountJsonException {
        if (account.getAccountNumber() == null || account.getFirstName() == null || account.getSecondName() == null) {
            throw new InvalidAccountJsonException();
        } else {
            accountRepository.save(account);
        }

        return ok().body("Account has been successfully added");
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/accounts/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) throws DeleteAccountException {

        if (accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
            return ok().body("Account successfully deleted");
        } else {
            throw new DeleteAccountException();
        }
    }
}
