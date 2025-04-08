package telran.java57.forum.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccountAlreadyExistsException extends RuntimeException {

}
