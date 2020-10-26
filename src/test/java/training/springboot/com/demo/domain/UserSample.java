package training.springboot.com.demo.domain;

import java.util.Date;

public class UserSample {

    public static User.UserBuilder anyUser(){
        return User.builder()
                .lastLogin(new Date())
                .name("Trung")
                .password("hello");

    }

    public static User.UserBuilder anyAPassword(String password){
        return anyUser().password(password);
    }

}
