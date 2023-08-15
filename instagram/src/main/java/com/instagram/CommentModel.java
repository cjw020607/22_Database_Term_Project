package com.instagram;

import java.sql.ResultSet;

public class CommentModel {
    User_info user_info;
    int comment_id;
    String comment_body;
    int post_id;
    java.sql.Timestamp comment_time;

    public CommentModel(ResultSet result) throws Exception {
        user_info = new User_info(result);
        comment_id = result.getInt("comment_id");
        comment_body = result.getString("comment_body");
        post_id = result.getInt("post_id");
        comment_time = result.getTimestamp("comment_time");
    }
}
