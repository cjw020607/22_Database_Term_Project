package com.instagram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostModel {
    User_info user_info;
    int post_id;
    String contents;
    int likecnt;
    int commentscnt;
    boolean islike;
    String image_name;
    java.sql.Timestamp post_time;

    public PostModel(ResultSet result) throws SQLException {
        this.user_info = new User_info(result);
        this.post_id = result.getInt("post_id");
        this.contents = result.getString("content");
        this.likecnt = result.getInt("like_num");
        this.commentscnt = result.getInt("comment_num");
        Statement stmt = App.conn.createStatement();
        // String select_like = String.format("select * from post_like where post_id =
        // \"%d\" and user_id = \"%d\";",
        // result.getInt("post_id"), App.user.user_id);
        String select_like = String.format("select * from post_like where post_id = \"%d\" and user_id = \"%d\";",
                result.getInt("post_id"), CurrentUser.user_id);
        ResultSet result2 = stmt.executeQuery(select_like);
        this.islike = result2.next();
        this.image_name = result.getString("file_name");
        post_time = result.getTimestamp("post_time");
    }

    public PostModel(User_info user_info, String nickname, int post_id, String contents, int likecnt, int commentscnt) {
        this.user_info = user_info;
        this.post_id = post_id;
        this.contents = contents;
        this.likecnt = likecnt;
        this.commentscnt = commentscnt;
        this.post_time = post_time;
    }
}
