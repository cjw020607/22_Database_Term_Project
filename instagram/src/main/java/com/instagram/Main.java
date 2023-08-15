package com.instagram;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class Main {
    Statement state;
    ResultSet rs;
    String nickname;
    JFrame f1;
    JLabel l1;
    JLabel l2;
    JLabel l3;
    JTextField tf1;

    JButton b1;
    JButton b2;
    JButton b3;

    int index = 0;

    public Main(Statement stmt) {
        state = stmt;
    }

    public void main() throws SQLException {
        // 이미지들 세팅
        Image image = new Image(state);

        f1 = new JFrame("Main");
        l1 = new JLabel("My Page");
        l2 = new JLabel("See Other Users");
        l3 = new JLabel("Upload New Post");

        b1 = new JButton("SEE");
        b2 = new JButton("SEARCH");
        b3 = new JButton("GO");

        tf1 = new JTextField();

        l1.setFont(new Font("Serif", Font.BOLD, 25));
        l1.setBounds(150, 30, 150, 40);
        l2.setFont(new Font("Serif", Font.BOLD, 25));
        l2.setBounds(120, 130, 200, 40);
        l3.setFont(new Font("Serif", Font.BOLD, 25));
        l3.setBounds(110, 230, 200, 40);

        b1.setBounds(160, 70, 60, 30);
        b2.setBounds(260, 180, 100, 30);
        b3.setBounds(160, 280, 60, 30);

        tf1.setBounds(100, 180, 150, 30);
        f1.add(l1);
        f1.add(l2);
        f1.add(l3);

        f1.add(b1);
        f1.add(b2);
        f1.add(b3);

        f1.add(tf1);

        f1.setSize(400, 500);
        f1.setLayout(null);
        f1.setVisible(true);

        state.executeQuery(String.format("select * from users where nickname =\'%s\'", nickname));

        // see 누를때
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MyPage myPage = new MyPage(state);
                try {
                    // 내 page 불러오기
                    myPage.main();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });

        // search 누를때
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 검색할 user의 이름 받아옴
                    nickname = tf1.getText();

                    rs = state.executeQuery(
                            String.format("select * from users where nickname = \'%s\'",
                                    nickname));

                    // 검색한 user의 정보가 없을 때
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "User not found!",
                                "User not found", 1);
                    }
                    // 검색한 user가 login된 user와 같을 때
                    else if (nickname.equals(CurrentUser.nickname))
                        JOptionPane.showMessageDialog(null, "It's me! Just click 'see' button on top",
                                "My Page", 1);
                    // 검색한 user의 정보를 찾았을 때
                    else {
                        Browsed.setUser(nickname);
                        Browsed.setOthers(state);
                        Post post = new Post(state);
                        post.main();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // go 누를때
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // upload 창 띄우기
                // Upload.image_id = 5;
                Upload upload = new Upload(state);
                upload.main();

            }

        });

    }
}
