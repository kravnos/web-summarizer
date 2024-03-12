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
     void mapUserReqAtoToEto() {
        //test = UserMapper.mapUserEtoToResAto(User.builder().first_name());
    }

    @Test
    void mapUserEtoToResAto() {
    }

    @Test
    void updateUser() {
    }
}