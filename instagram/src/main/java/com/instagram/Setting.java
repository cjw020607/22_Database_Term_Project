package com.instagram;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class Setting {

    private JFrame frame;
    private JTextField current_pw_field;
    private JPasswordField new_pw_field;
    Statement state;
    ResultSet rs;

    public Setting(Statement stmt) {
        state = stmt;
    }

    public void main() {
        // frame
        frame = new JFrame("Setting");
        frame.setBounds(0, 0, 400, 500);
        frame.setVisible(true);
        frame.setResizable(false);

        frame.getContentPane().setLayout(null);

        // panel
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 786, 763);
        panel.setLayout(null);

        frame.getContentPane().add(panel); // frame에 panel 올라감

        // current_pw_label
        JLabel Current_pw_label = new JLabel("Current Password");
        Current_pw_label.setBounds(50, 39, 110, 18);
        panel.add(Current_pw_label);

        // 현재 패스워드가 들어가는 칸
        final String current_pw_query = CurrentUser.password; // 현재 접속해있는 user의 PassWord하는 변수
        current_pw_field = new JTextField();
        current_pw_field.setBounds(250, 38, 96, 21);
        panel.add(current_pw_field);
        current_pw_field.setColumns(10);

        // new_pw_label
        JLabel New_pw_label = new JLabel("New password");
        New_pw_label.setBounds(50, 95, 96, 18);
        panel.add(New_pw_label);

        // 새로운 패스워드가 들어가는 칸
        new_pw_field = new JPasswordField();
        new_pw_field.setBounds(250, 94, 96, 21);
        panel.add(new_pw_field);

        // Change button
        JButton Change_button = new JButton("Change");
        Change_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 현재 비밀번호 입력 칸의 값 가져오기
                String cpw = new String(current_pw_field.getText());
                // 새로운 비밀번호 입력 칸의 값 가져오기
                String npw = new String(new_pw_field.getPassword());

                String url = "jdbc:mysql://localhost:3306/instagram";
                Connection conn;
                try {
                    // 8자 영문+특문+숫자 조건
                    Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
                    Matcher passMatcher = passPattern1.matcher(npw);
                    // 현재 user의 비밀번호와 입력한 비밀번호가 같을 때
                    if (current_pw_query.equals(cpw)) {
                        // 새로운 비밀번호가 현재 비밀번호와 같을 때
                        if (npw.equals(cpw))
                            JOptionPane.showMessageDialog(null, "Must be different from your current password!",
                                    "Wrong", JOptionPane.ERROR_MESSAGE);
                        // 비밀번호 생성 조건에 맞지 않을 때
                        else if (!passMatcher.find())
                            JOptionPane.showMessageDialog(null, "Password should be 8+ with Eng+Num+Special Symbol!",
                                    "Wrong", 1);
                        // 모든 조건 다 충족 할 때
                        else {
                            // 비밀번호 바꾸기
                            conn = DriverManager.getConnection(url, "root", "12345");
                            String sql = "update users set pw=? where user_id=" + CurrentUser.user_id;
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            CurrentUser.password = npw;
                            pstmt.setString(1, npw);
                            pstmt.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Changed successfully!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            frame.setVisible(false);
                        }
                    }
                    // 현재 user의 비밀번호와 입력한 비밀번호가 다를 때
                    else {
                        JOptionPane.showMessageDialog(null, "Check your current password!", "Wrong",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        Change_button.setBounds(50, 159, 280, 51);
        panel.add(Change_button);

    }
}