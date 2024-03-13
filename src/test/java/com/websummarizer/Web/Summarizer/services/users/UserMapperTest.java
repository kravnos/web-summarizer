package com.websummarizer.Web.Summarizer.services.users;

import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.controller.user.UserResAto;
import com.websummarizer.Web.Summarizer.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Name;

import static org.junit.jupiter.api.Assertions.*;


class UserMapperTest {

    private User test;


    @Test
     void mapUserReqAtoToEto() { //initialize all variables
        test.setFirst_name("TEst");
        test.setLast_name("Attempt");
        test.setEmail("testemail@gmail.com");
        test.setPassword("12345");
        test.setPhone_number("9051112222");
        test.setId(12345);
    }

    @Test
    void mapUserEtoToResAto() {
        test.getFirst_name();
        test.getLast_name();
        test.getPhone_number();
        test.getId();
        test.getPassword();
        test.getPhone_number();
    }

    @Test
    void updateUser() {

    }
}