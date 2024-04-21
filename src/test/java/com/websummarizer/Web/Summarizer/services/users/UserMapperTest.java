package com.websummarizer.Web.Summarizer.services.users;

import com.websummarizer.Web.Summarizer.model.user.UserResAto;
import com.websummarizer.Web.Summarizer.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class UserMapperTest{

    @Autowired
    public User test;


    @Test
     void mapUserReqAtoToEto() { //initialize all variables
        User test = this.test;
        test.setFirst_name("TEst");
        test.setLast_name("Attempt");
        test.setEmail("testemail@gmail.com");
        test.setPassword("12345");
        test.setPhone_number("9051112222");
        test.setId(12345);
    }

    @Test
    @Autowired
    public static UserResAto mapUserEtoToResAto(User test) {
        test.setFirst_name("Test");
        test.setLast_name("Attempt");
        test.setPhone_number("9051234567");
        test.setId(12345);
        test.setPassword("12345");
        test.setEmail("testuser@gmail.com");
        return UserMapper.mapUserEtoToResAto(test);
    }

    @Test
    void updateUser() {
        //long t = 12345;
        //UserResAto userResAto = new UserResAto(t, "test", "attempt",
           //     "test@gmail.com", "12345", "9051112222");
        mapUserEtoToResAto(test);
    }
}