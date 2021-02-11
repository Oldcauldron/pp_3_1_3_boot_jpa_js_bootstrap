package jm.homework.pp_3_1_1_boot_2.exception_handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    UserIncorrectData userIncorrectData;

    @Autowired
    public GlobalControllerExceptionHandler(UserIncorrectData userIncorrectData) {
        this.userIncorrectData = userIncorrectData;
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(NoUserWithSuchIdException exception) {
        userIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(userIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(UserWithSuchEmailExist exception) {
        userIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(userIncorrectData, HttpStatus.NOT_FOUND);
    }


}
