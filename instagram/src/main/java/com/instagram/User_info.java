package com.instagram;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User_info {

    public String nickname;
    public String user_name;
    public String pw;
    public String email;
    public String birth_date;
    public String intro_article;
    public String profile_image;
    // public String website;
    public String is_created;
    public String is_deleted;
    public int user_id;

    public User_info(ResultSet rows) throws SQLException {

        user_id = rows.getInt("user_id");
        nickname = rows.getString("nickname");
        user_name = rows.getString("user_name");
        email = rows.getString("email");
        birth_date = rows.getString("birth_date");
        intro_article = rows.getString("intro_article");
        profile_image = rows.getString("profile_image");
        // website = rows.getString("website");
        is_created = rows.getString("is_created");
        is_deleted = rows.getString("is_deleted");

    }
}
