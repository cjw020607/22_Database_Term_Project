package com.instagram;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class PostContent {
    Statement state;
    ResultSet rs;
    JFrame f1;
    JLabel l1;
    JButton b1;

    TextArea ta1;

    public PostContent(Statement stmt) {
        state = stmt;
    }

    public void main() {
        f1 = new JFrame("Post Content");
        l1 = new JLabel("Type contents of your post");
        ta1 = new TextArea();
        b1 = new JButton("Upload");

        // Upload 버튼 눌렀을 때
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // content 입력 안했을 경우 오류 메시지
                if (ta1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please type contents of you post!", "Content", 1);
                }
                // content 입력 했을 경우
                else {
                    try {
                        String ex = String.format(
                                "insert into post(user_id,content,image_id) values (\'%d\', \'%s\',\'%d\')",
                                CurrentUser.user_id, ta1.getText(), Upload.image_id);
                        state.executeUpdate(ex);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "Post successfully uploaded!", "Uploaded", 1);
                    f1.setVisible(false);
                }
            }

        });

        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l1.setBounds(70, 40, 300, 40);
        ta1.setBounds(30, 80, 300, 120);
        b1.setBounds(300, 10, 80, 30);

        f1.add(l1);
        f1.add(b1);
        f1.add(ta1);

        f1.setSize(400, 500);
        f1.setLayout(null);
        f1.setVisible(true);

    }

}
