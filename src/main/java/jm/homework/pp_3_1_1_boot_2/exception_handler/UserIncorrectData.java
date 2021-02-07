package jm.homework.pp_3_1_1_boot_2.exception_handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserIncorrectData {
    private String info;

    public UserIncorrectData(String info) {
        this.info = info;
    }

    public UserIncorrectData() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
