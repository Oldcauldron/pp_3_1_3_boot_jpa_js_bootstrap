package jm.homework.pp_3_1_3_boot_js.exception_handler;

import org.springframework.dao.DataIntegrityViolationException;

public class UserWithSuchEmailExist extends DataIntegrityViolationException {
    public UserWithSuchEmailExist(String msg) {
        super(msg);
    }
}
