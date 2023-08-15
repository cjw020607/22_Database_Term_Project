package com.instagram;

import java.sql.SQLException;
import java.sql.Statement;

public class Unfollow {
    Statement state;

    public Unfollow(Statement stmt) {
        state = stmt;
    }

    public void main() throws SQLException {

        String nickname = CurrentUser.nickname;
        String browsed = Browsed.nickname;

        // 현재 로그인 된 user가 browse하고 있는 user를 팔로우 취소하기
        String sql = "delete from follow where nickname=\'" + nickname + "\' and following_name=\'" + browsed + "\'";

        state.executeUpdate(sql);

    }

}
