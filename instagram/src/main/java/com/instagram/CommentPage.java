package com.instagram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentPage extends JFrame {
    PostModel postmodel;
    static Font font1 = new Font("맑은 고딕", Font.PLAIN, 20);

    public CommentPage(PostModel postmodel) {
        this.postmodel = postmodel;
    }

    boolean comment_like_state = false;

    public void paint() throws SQLException {
        Statement stmt = App.conn.createStatement();
        String select_comments = String.format(
                "select * from post_comment inner join Users on post_comment.user_id = Users.user_id and post_comment.post_id = \"%s\" order by post_comment.comment_time desc;",
                postmodel.post_id);
        ResultSet result = stmt.executeQuery(select_comments);

        int i = 0;
        while (result.next()) {
            try {
                final CommentModel commentmodel = new CommentModel(result);
                JPanel comment = new JPanel();
                comment.setLayout(new FlowLayout(FlowLayout.LEFT));
                JLabel name_label = new JLabel(commentmodel.user_info.nickname);
                name_label.setFont(font1);
                JLabel l_contents = new JLabel(commentmodel.comment_body);
                l_contents.setFont(font1);
                JLabel l_contents_time = new JLabel(commentmodel.comment_time.toString());
                l_contents_time.setFont(font1);

                comment.add(name_label);
                comment.add(Common.sizedbox(20, 50)); // 빈 정사각형 하나 넣기
                comment.add(l_contents);
                comment.add(Common.sizedbox(20, 50));
                comment.add(l_contents_time);
                comment.add(Common.sizedbox(20, 50));

                // 댓글 삭제
                JButton delete_button = new JButton("삭제");
                delete_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Statement stmt = App.conn.createStatement();
                            String delete_comment = String.format(
                                    "delete from post_comment where comment_id = %d;",
                                    commentmodel.comment_id);

                            stmt.executeUpdate(delete_comment);

                            String delete_comment_num = String.format(
                                    "update post set comment_num =comment_num-1 where post.post_id = %d;",
                                    postmodel.post_id);
                            stmt.executeUpdate(delete_comment_num);
                            getContentPane().removeAll();
                            paint();
                            invalidate();
                            validate();
                            repaint();
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });

                // 댓글 좋아요
                final JButton b_comment_like = new JButton("LIKE");
                b_comment_like.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        try {
                            Statement stmt = App.conn.createStatement();
                            if (comment_like_state == false) { // 좋아요 눌러져 있지 않음

                                comment_like_state = true; // like버튼 누름
                                b_comment_like.setBackground(new Color(255, 102, 102));// 버튼 색 변화

                            } else { // 좋아요 눌러져 있음

                                comment_like_state = false; // 좋아요 취소

                                b_comment_like.setBackground(null);// 버튼 색 변화

                            }
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });

                int height = 70;

                comment.setBounds(0, 0 + (height) * i, 500, height);
                // comment.setLocation(0, 0 + 50 * i);

                // if (App.user.user_id == commentmodel.user_info.user_id)
                // if (App.user.user_id == CurrentUser.user_id)
                if (commentmodel.user_info.user_id == CurrentUser.user_id)
                    comment.add(delete_button);
                comment.add(b_comment_like, BorderLayout.EAST);
                add(comment);
                i++;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // 댓글 입력 panel
        JPanel p_writecmt = new JPanel();
        p_writecmt.setLayout(new BorderLayout());
        final JTextField cmt = new JTextField(35);
        p_writecmt.add(cmt);
        JButton b_comment = new JButton("ENTER");
        b_comment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cmt.getText().equals("")) {
                    try {
                        Statement stmt = App.conn.createStatement();
                        String insert_comment = String.format(
                                "insert into post_comment(comment_body, user_id, post_id, comment_time) Values(\"%s\", %d, %d, now());",
                                cmt.getText(), CurrentUser.user_id, postmodel.post_id);
                        stmt.executeUpdate(insert_comment);
                        String increase_comment_num = String.format(
                                "update post set comment_num =comment_num+1 where post.post_id = %d;",
                                postmodel.post_id);
                        stmt.executeUpdate(increase_comment_num);
                        getContentPane().removeAll();
                        paint();
                        validate();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    cmt.setText("");
                } else
                    JOptionPane.showMessageDialog(null, "댓글을 입력해주세요.");
            }
        });
        p_writecmt.add(b_comment, BorderLayout.EAST);
        p_writecmt.setBounds(0, 800, 480, 50);
        add(p_writecmt);
    }

    public void main() throws SQLException {
        setTitle("Comment");
        setSize(500, 900);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        paint();
        setVisible(true);
    }
}
