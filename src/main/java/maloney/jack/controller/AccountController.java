package maloney.jack.controller;

import maloney.jack.domain.Account;
import maloney.jack.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public String save(@RequestBody Account account) {
        accountRepository.save(account);

        return "Account has been successfully added";
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/accounts/{id}")
    public String delete(@PathVariable String id) {
       accountRepository.deleteById(id);

       return "Account successfully deleted";
    }
}
