package com.instagram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CurrentUser {
    static int user_id;
    static String password;
    static String nickname;
    static String profile;
    static String name;
    static String email;
    static String gender;
    static String article;
    static String birthday;
    static String created_date;
    ResultSet rs;
    Statement state;

    // 현재 로그인된 user의 정보들을 다 가져와서 넣어주기
    public void setUser(String user) {
        nickname = user;
    }

    public void setOthers(Statement stmt) throws SQLException {
        state = stmt;
        rs = state.executeQuery(String.format("select * from users where nickname = \'%s\'", nickname));

        while (rs.next()) {
            user_id = rs.getInt("user_id");
            password = rs.getString("pw");
            name = rs.getString("user_name");
            email = rs.getString("email");
            gender = rs.getString("gender");
            article = rs.getString("intro_article");
            birthday = rs.getString("birth_date");
            profile = rs.getString("profile_image");
            created_date = rs.getString("is_created");

        }

    }

}
