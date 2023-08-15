package com.instagram;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MyPage implements ListSelectionListener {
    Statement state;
    ResultSet rs;
    // static Connection conn;

    JFrame f1;
    JList list;
    JScrollPane scrolled;

    JButton b1;
    JButton b2;

    JButton b3;
    JButton b4;
    JButton b5;

    JLabel l1;
    JLabel l2;
    JLabel l3;
    JLabel l4;

    public MyPage(Statement stmt) {
        state = stmt;
    }

    public void main() throws SQLException {
        f1 = new JFrame("My Page");

        String nickname = CurrentUser.nickname;

        b1 = new JButton();
        b2 = new JButton("About");
        b3 = new JButton();
        b5 = new JButton("Change Password");

        rs = state.executeQuery(String.format("select * from users where nickname=\'%s\'", nickname));

        // 현재 로그인된 user의 nickname과 일치하는 data가 있을 때 그 user의 image_id 따라 프로필 사진 설정함
        while (rs.next()) {
            int imageId = rs.getInt("profile_image");
            if (imageId == 1)
                b4 = new JButton(Image.img1);
            else if (imageId == 2)
                b4 = new JButton(Image.img2);
            else if (imageId == 3)
                b4 = new JButton(Image.img3);
            else if (imageId == 4)
                b4 = new JButton(Image.img4);
            else
                b4 = new JButton(Image.img5);
        }

        l1 = new JLabel("Follower");
        l2 = new JLabel("Following");
        l3 = new JLabel("This is my page!");
        l3.setFont(new Font("Serif", Font.BOLD, 20));
        l4 = new JLabel("(" + CurrentUser.nickname + ")");
        l4.setFont(new Font("Serif", Font.BOLD, 20));

        b1.setBounds(250, 50, 90, 40);
        b2.setBounds(200, 100, 90, 30);

        b3.setBounds(150, 50, 90, 40);

        b4.setBounds(10, 10, 130, 130);
        b5.setBounds(170, 140, 150, 30);

        l1.setBounds(170, 20, 90, 30);
        l2.setBounds(270, 20, 90, 30);
        l3.setBounds(20, 140, 300, 40);
        l4.setBounds(20, 170, 300, 40);

        f1.add(b3);
        f1.add(b1);
        f1.add(b2);
        f1.add(b4);
        f1.add(b5);

        f1.add(l1);
        f1.add(l2);
        f1.add(l3);
        f1.add(l4);

        f1.setSize(400, 500);
        f1.setLayout(null);
        f1.setVisible(true);

        int followerC = 0, followingC = 0;

        // follower 수
        rs = state.executeQuery(String.format("select count(*) from follow where following_name=\'%s\'", nickname));
        if (rs.next())
            followerC = rs.getInt(1);
        b3.setText(String.valueOf(followerC));

        // following 수
        rs = state.executeQuery(String.format("select count(*) from follow where nickname=\'%s\'", nickname));
        if (rs.next())
            followingC = rs.getInt(1);
        b1.setText(String.valueOf(followingC));

        // follower 버튼 누를때
        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // follower list 가져옴
                Follower follower = new Follower(state, "me");
                try {
                    follower.main();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // following 버튼 누를때
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Following following = new Following(state, "me");
                try {
                    // following list 가져옴
                    following.main();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // about 버튼 누를때
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                About about = new About(state, "me");
                try {
                    // 나에 대한 정보 가져옴
                    about.main();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // change password 눌릴때
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 비밀번호 바꾸는 창 불러옴
                Setting setting = new Setting(state);
                setting.main();
            }
        });

        // post 최대 99개
        String[] array = new String[99];
        int i = 0;
        rs = state.executeQuery(String.format("select * from post where user_id=\'%s\'", CurrentUser.user_id));

        // login된 user의 post들이 있을때 array에 content를 넣어줌 '/'' 앞부분은 post_id
        while (rs.next()) {
            array[i] = rs.getInt("post_id") + "/" + (i + 1) + ". " + rs.getString("content");
            i++;
        }

        // array를 list에 넣기
        list = new JList(array);
        list.setFixedCellHeight(40);
        list.setFixedCellWidth(300);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        scrolled = new JScrollPane(list);

        scrolled.setBounds(30, 200, 320, 250);

        f1.add(scrolled, "Center"); // 가운데 list

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) { // 이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
            List<String> ls = list.getSelectedValuesList();
            for (String value : ls) {
                // '/'를 기준으로 문자열 나눔
                String[] split = value.split("/");
                // '/'앞의 값은 postId
                int postId = Integer.valueOf(split[0]);
                System.out.println(postId);

                String url = "jdbc:mysql://localhost:3306/instagram";

                try {
                    Connection conn = DriverManager.getConnection(url, "root", "12345");
                    Statement querystmt = conn.createStatement();

                    String login_my = "select * from users where user_id = " + CurrentUser.user_id;
                    ResultSet login_result = querystmt.executeQuery(login_my);
                    login_result.next();
                    App.user = new User_info(login_result);

                    querystmt = conn.createStatement();
                    String select_posts = "select * from post inner join Users on post.user_id =Users.user_id left join image on post.image_id = image.image_id where post.user_id="
                            +
                            CurrentUser.user_id + " and post_id=" + postId;

                    ResultSet result = querystmt.executeQuery(select_posts);
                    while (result.next()) {
                        PostNext post = new PostNext(new PostModel(result));
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
