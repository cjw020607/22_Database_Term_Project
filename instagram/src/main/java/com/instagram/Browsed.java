package com.instagram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Browsed {
    static int user_id;
    static String nickname;
    static String profile;
    static String name;
    static String email;
    static String gender;
    static String article;
    static String birthday;
    static String created_date;
    static ResultSet rs;
    static Statement state;

    // 현재 user가 보고있는 다른 유저의 정보를 세팅
    public static void setUser(String user) {
        nickname = user;
    }

    public static void setOthers(Statement stmt) throws SQLException {
        state = stmt;

        rs = state.executeQuery(String.format("select * from users where nickname = \'%s\'", nickname));

        while (rs.next()) {
            user_id = rs.getInt("user_id");
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
