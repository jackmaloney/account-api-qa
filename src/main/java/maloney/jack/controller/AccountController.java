package maloney.jack.controller;

import maloney.jack.domain.Account;
import maloney.jack.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(method= RequestMethod.GET, value="/accounts")
    public Iterable<Account> account() {
        return null;
    }

    @RequestMapping(method=RequestMethod.POST, value="/accounts")
    public String save(@RequestBody Account account) {
        return null;
    }


    @RequestMapping(method=RequestMethod.DELETE, value="/accounts/{id}")
    public String delete(@PathVariable String id) {
       return null;
    }
}
