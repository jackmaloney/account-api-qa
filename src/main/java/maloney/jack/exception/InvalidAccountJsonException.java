package maloney.jack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account could not be added: given Json is invalid")
public class InvalidAccountJsonException extends Exception {

    public InvalidAccountJsonException() {
        super("Given Json is invalid");
    }
}
