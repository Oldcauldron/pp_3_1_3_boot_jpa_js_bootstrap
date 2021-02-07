package jm.homework.pp_3_1_1_boot_2.exception_handler;

import org.springframework.dao.DataIntegrityViolationException;

public class UserWithSuchEmailExist extends DataIntegrityViolationException {
    public UserWithSuchEmailExist(String msg) {
        super(msg);
    }
}
