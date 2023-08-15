package com.instagram;

import java.sql.SQLException;
import java.sql.Statement;

public class Follow {
    Statement state;

    public Follow(Statement stmt) {
        state = stmt;
    }

    public void main() throws SQLException {

        String nickname = CurrentUser.nickname;
        String browsed = Browsed.nickname;

        // 현재 로그인 된 user가 browse하고 있는 user를 팔로우하기
        String ex = String.format("insert into follow(nickname,following_name) values (\'%s\', \'%s\')", nickname,
                browsed);
        state.executeUpdate(ex);

    }

}
