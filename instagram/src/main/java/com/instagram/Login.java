package com.instagram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Login {
    static public String currentUser;
    ResultSet rs1;
    static String nickname, password;
    Statement state;
    JFrame f1;
    JTextField tf1;
    JPasswordField pf1;
    JButton b1;
    int index = 0;

    public Login(Statement stmt) {
        state = stmt;
    }

    public void main() throws SQLException {

        f1 = new JFrame("Login");

        JLabel l2 = new JLabel("User Name(ID):");
        JLabel l3 = new JLabel("password:");

        // nickname 입력받는 곳
        tf1 = new JTextField();
        // pw 입력받는 곳
        pf1 = new JPasswordField();

        b1 = new JButton("LOGIN");

        l2.setBounds(50, 100, 100, 20);
        l3.setBounds(75, 150, 80, 20);

        tf1.setBounds(160, 100, 150, 20);
        pf1.setBounds(160, 150, 150, 20);

        b1.setBounds(190, 200, 80, 20);
        f1.add(l2);
        f1.add(tf1);
        f1.add(l3);
        f1.add(pf1);
        f1.add(b1);

        f1.setSize(400, 500);
        f1.setLayout(null);
        f1.setVisible(true);

        state.executeQuery(String.format("select * from users where nickname =\'%s\'", nickname));

        // login 버튼을 눌렀을 때
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // nickname과 pw 받기
                    nickname = tf1.getText();
                    password = new String(pf1.getPassword());

                    // login한 유저의 nickname과 그 외 정보들 저장
                    CurrentUser user = new CurrentUser();
                    user.setUser(nickname);
                    user.setOthers(state);

                    rs1 = state.executeQuery(String
                            .format("select * from users where nickname = \'%s\' and pw = \'%s\'", nickname, password));
                    // 입력한 nickname, 비밀번호와 일치하는 data를 찾지 못했을 때
                    if (!rs1.next()) {
                        JOptionPane.showMessageDialog(null, "Nickname or password wrong!",
                                "Login failed", 1);
                    } else {
                        JOptionPane.showMessageDialog(null, "Login successful!",
                                "Login success", 1);
                        index = 1;
                        f1.setVisible(false);// 로그인 화면 끄기
                        // main 창 불러오기
                        Main main = new Main(state);
                        main.main();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

}
