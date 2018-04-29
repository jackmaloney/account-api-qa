package maloney.jack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account could not be deleted: given Account ID does not exist")
public class DeleteAccountException extends Exception {

    public DeleteAccountException() {
        super("Account could not be deleted");
    }
}
