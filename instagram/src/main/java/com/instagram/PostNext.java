package com.instagram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class PostNext extends JFrame {
    private static final String NULL = null;
    static Font font1 = new Font("맑은 고딕", Font.PLAIN, 20);
    static int topbarHeight = 35;
    PostModel postmodel;

    // reload 없애보기
    public void reload() throws SQLException {
        Statement querystmt = App.conn.createStatement();
        String select_posts = String.format(
                "select * from post inner join Users on post.user_id = Users.user_id left join image on post.image_id = image.image_id where post.post_id = %d;",
                postmodel.post_id);

        ResultSet result = querystmt.executeQuery(select_posts);
        result.next();
        postmodel = new PostModel(result);
    }

    static String getLikeLabel(PostModel postmodel) {
        return "좋아요 " + postmodel.likecnt + "개";
    }

    static String getCommentLabel(PostModel postmodel) {
        return "댓글보기 " + postmodel.commentscnt + "개";
    }

    public PostNext(PostModel param_post) {
        this.postmodel = param_post;
        setTitle("PostNext");
        setSize(500, 900);
        setLayout(null);
        setResizable(false);

        // user panel
        JPanel p_user = new JPanel();
        p_user.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l_nickname = new JLabel(postmodel.user_info.nickname);
        l_nickname.setFont(font1);
        p_user.add(l_nickname);
        p_user.setBounds(15, 40 - topbarHeight, 240, 30);
        add(p_user);

        // image panel
        if (Upload.image_id != 5) {
            ImageIcon icon = new ImageIcon("./image/" + postmodel.image_name);
            JPanel img_panel = new JPanel();
            // 수정
            java.awt.Image img = icon.getImage();
            java.awt.Image changeImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            ImageIcon changeIcon = new ImageIcon(changeImg);
            //
            JLabel image = new JLabel(changeIcon);
            img_panel.add(image);
            img_panel.setBounds(0, 60, 500, 500);
            add(img_panel);
        } else {
            JPanel image_panel = new JPanel();
            image_panel.setBounds(0, 60, 500, 500);
            image_panel.setBackground(Color.BLACK);
            add(image_panel);

        }

        // like panel
        JPanel p_like = new JPanel();
        p_like.setLayout(new FlowLayout(FlowLayout.LEFT));
        final JButton b_like = new JButton("LIKE");
        if (postmodel.islike == true) { // 이미 좋아요 누름
            b_like.setBackground(new Color(255, 102, 102));
        }

        final JLabel l_like = new JLabel(PostNext.getLikeLabel(postmodel));
        l_like.setFont(font1);
        p_like.add(b_like);
        p_like.add(l_like);
        p_like.setBounds(0, 600 - topbarHeight, 300, 60);
        add(p_like);
        b_like.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                try {
                    Statement stmt = App.conn.createStatement();
                    if (postmodel.islike == false) { // 좋아요 눌러져 있지 않음
                        // String insert_like = String.format("insert into post_like(user_id, post_id)
                        // Values(%d, %d);",
                        // App.user.user_id, postmodel.post_id);
                        String insert_like = String.format("insert into post_like(user_id, post_id) Values(%d, %d);",
                                CurrentUser.user_id, postmodel.post_id);
                        stmt.executeUpdate(insert_like);

                        String update_like_num = String.format(
                                "update post set like_num=like_num +1 where post.post_id = %d;",
                                postmodel.post_id);
                        postmodel.islike = true; // like버튼 누름
                        stmt.executeUpdate(update_like_num);// 좋아요 개수 update
                        reload();
                        b_like.setBackground(new Color(255, 102, 102));// 버튼 색 변화
                        l_like.setText(PostNext.getLikeLabel(postmodel));

                    } else { // 좋아요 눌러져 있음
                        // String insert_like = String.format("delete from post_like where user_id = %d
                        // and post_id = %d;",
                        // App.user.user_id, postmodel.post_id);
                        String insert_like = String.format("delete from post_like where user_id = %d and post_id = %d;",
                                CurrentUser.user_id, postmodel.post_id);
                        stmt.executeUpdate(insert_like);

                        String update_like_num = String.format(
                                "update post set like_num=like_num-1 where post.post_id = %d;",
                                postmodel.post_id);
                        postmodel.islike = false; // 좋아요 취소
                        stmt.executeUpdate(update_like_num);// 좋아요 개수 update

                        reload();
                        b_like.setBackground(null);// 버튼 색 변화
                        l_like.setText(PostNext.getLikeLabel(postmodel));
                    }
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        // content panel
        JPanel p_content = new JPanel();
        p_content.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l_contents = new JLabel(postmodel.contents);
        l_contents.setFont(font1);
        p_content.add(l_contents);
        p_content.setBounds(0, 660 - topbarHeight, 600, 150);
        add(p_content);

        // post_time panel
        JPanel p_post_time = new JPanel();
        p_post_time.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l_post_time = new JLabel(postmodel.post_time.toString());
        p_post_time.add(l_post_time);
        p_post_time.setBounds(0, 780, 600, 30);
        add(p_post_time);

        // comment panel
        JPanel p_comment = new JPanel();
        p_comment.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton b_comment = new JButton("COMMENT");
        final CommentPage comments = new CommentPage(postmodel);
        b_comment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    comments.main();// comment 버튼 누르면 comment 창 띄우기

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        final JLabel l_comment = new JLabel("댓글보기 " + postmodel.commentscnt + "개");
        l_comment.setFont(font1);
        p_comment.add(b_comment);
        p_comment.add(l_comment);
        p_comment.setBounds(0, 840 - topbarHeight, 300, 60);
        add(p_comment);
        comments.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    reload();
                    l_comment.setText(getCommentLabel(postmodel));

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        setVisible(true);

    }
}
