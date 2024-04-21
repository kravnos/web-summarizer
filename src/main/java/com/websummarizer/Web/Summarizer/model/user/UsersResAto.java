package com.websummarizer.Web.Summarizer.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class UsersResAto {
    private List<UserResAto> users;

    public int getTotalCount() {
        if (users == null) {
            return 0;
        }
        return users.size();
    }
}
