package com.instagram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class CreateAccount extends JFrame {
    // String[] gender = { "man", "woman" };
    Statement state;
    String nickname, pw, pwc, gender, name, year, month, day, date, email, article, image;

    ResultSet rs;
    JFrame f1;
    JTextField tf1;
    JPasswordField pf1;
    JPasswordField pf2;
    JTextField tf2;
    JTextField tf3;
    JTextField tf4;
    JTextField tf5;
    JTextField tf6;
    JRadioButton rb1;
    JRadioButton rb2;
    ButtonGroup bg1;
    JComboBox<String> cb1;
    JComboBox<String> cb2;
    Login lg;

    public CreateAccount(Statement stmt) {
        state = stmt;
    }

    public void main() {
        f1 = new JFrame("Create new account");

        pf1 = new JPasswordField();
        pf2 = new JPasswordField();

        tf1 = new JTextField("No space, 5+");
        tf2 = new JTextField();
        tf3 = new JTextField("YYYY");
        tf4 = new JTextField();
        tf5 = new JTextField();
        tf6 = new JTextField();

        // 성별
        rb1 = new JRadioButton("woman");
        rb2 = new JRadioButton("man");
        bg1 = new ButtonGroup();
        bg1.add(rb1);
        bg1.add(rb2);

        // 월
        cb1 = new JComboBox<String>(
                new String[] { "MM", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
        // 일
        cb2 = new JComboBox<String>(new String[] { "DD", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31" });

        JButton b1 = new JButton("Choose");
        JButton b2 = new JButton("SIGN UP");

        JLabel l1 = new JLabel("ID:");
        JLabel l2 = new JLabel("Password:");
        JLabel l3 = new JLabel("Double Check:");
        JLabel l4 = new JLabel("User Name:");
        JLabel l5 = new JLabel("Birth Date:");
        JLabel l6 = new JLabel("Gender:");
        JLabel l7 = new JLabel("Email:");
        JLabel l8 = new JLabel("Intro article:");
        JLabel l9 = new JLabel("Profile image:");

        pf1.setBounds(110, 80, 130, 20);
        pf2.setBounds(110, 130, 130, 20);

        tf1.setBounds(110, 30, 130, 20);
        tf2.setBounds(110, 180, 130, 20);
        tf3.setBounds(110, 210, 50, 20);
        tf4.setBounds(110, 290, 130, 20);
        tf5.setBounds(110, 340, 130, 20);

        cb1.setBounds(170, 210, 50, 20);
        cb2.setBounds(230, 210, 50, 20);

        rb1.setBounds(110, 260, 70, 20);
        rb2.setBounds(180, 260, 130, 20);

        l1.setBounds(90, 30, 100, 20);
        l2.setBounds(45, 80, 100, 20);
        l3.setBounds(20, 130, 100, 20);
        l4.setBounds(35, 180, 100, 20);
        l5.setBounds(40, 210, 100, 20);
        l6.setBounds(55, 260, 100, 20);
        l7.setBounds(65, 290, 100, 20);
        l8.setBounds(40, 340, 100, 20);
        l9.setBounds(20, 370, 100, 20);

        b1.setBounds(110, 370, 130, 20);
        b2.setBounds(150, 430, 90, 20);

        f1.add(l1);
        f1.add(l2);
        f1.add(l3);
        f1.add(l4);
        f1.add(l5);
        f1.add(l6);
        f1.add(l7);
        f1.add(l8);
        f1.add(l9);

        f1.add(pf1);
        f1.add(pf2);

        f1.add(tf1);
        f1.add(tf2);
        f1.add(tf3);
        f1.add(tf4);
        f1.add(tf5);
        f1.add(tf6);

        f1.add(cb1);
        f1.add(cb2);

        f1.add(rb1);
        f1.add(rb2);

        f1.add(b1);
        f1.add(b2);

        f1.setSize(400, 500);
        f1.setLayout(null);
        f1.setVisible(true);

        // 월 눌렸을때 월 저장
        cb1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cb1) {
                    JComboBox monthBox = (JComboBox) e.getSource();
                    month = (String) monthBox.getSelectedItem();
                    System.out.println(month);
                }

            }
        });
        // 일 눌렀을때 일 저장
        cb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cb2) {
                    JComboBox dayBox = (JComboBox) e.getSource();
                    day = (String) dayBox.getSelectedItem();
                    System.out.println(day);
                }
            }
        });

        // woman 눌렸을때 woman 저장
        rb1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gender = e.getActionCommand();
            }

        });

        // man 눌렸을때 man 저장
        rb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gender = e.getActionCommand();
            }

        });

        // 프로필 choose 눌렀을때
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Image image;
                try {
                    // 프로필 사진으로 설정할 사진 불러옴
                    image = new Image(state);
                    image.main();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }

        });

        // 회원가입 눌렸을때
        b2.addActionListener(new ActionListener() { // 회원가입버튼

            @Override
            public void actionPerformed(ActionEvent e) {

                nickname = tf1.getText();
                pw = new String(pf1.getPassword());
                pwc = new String(pf2.getPassword());
                name = tf2.getText();
                year = tf3.getText();
                email = tf4.getText();
                article = tf5.getText();
                image = tf6.getText();

                String sql = "insert into users(nickname,user_name, pw,email,gender,birth_date,intro_article,profile_image) values (?,?,?,?,?,?,?,?)";

                // 8자 영문+특문+숫자(비밀번호 조건)
                Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
                Matcher passMatcher = passPattern1.matcher(pw);
                try {
                    rs = state.executeQuery(String.format("select * from users where nickname = \'%s\'", nickname));
                    // 입력한 nickname과 중복되는 nickname이 이미 존재할 때
                    while (rs.next()) {
                        if (tf1.getText().equals(rs.getString("nickname"))) {
                            JOptionPane.showMessageDialog(null, "Nickname already exists!", "Same nickname", 1);
                        }
                    }
                    // 입력한 nickname에 공백이 포함될 때
                    if (nickname.replace(" ", "").length() != nickname.length()) {
                        JOptionPane.showMessageDialog(null, "Space not allowed!", "Space not allowed", 1);

                    }
                    // 입력한 nickname의 길이가 5보다 짧을 때
                    else if (nickname.length() < 5)
                        JOptionPane.showMessageDialog(null, "Nickname at least 5 letters!", "nickname length", 1);

                    // 비밀번호가 조건(8자 영문+특문+숫자)을 만족하지 않을 때
                    else if (!passMatcher.find()) {
                        JOptionPane.showMessageDialog(null, "Password should be 8+ with Eng+Num+Special Symbol!",
                                "비밀번호 오류", 1);
                    }
                    // 비밀번호와 재확인란의 비밀번호가 서로 일치하지 않을때
                    else if (!pw.equals(pwc)) {
                        JOptionPane.showMessageDialog(null, "Password doesn't match!", "Password wrong", 1);

                    } else {
                        try {
                            String url = "jdbc:mysql://localhost:3306/instagram";
                            Connection conn = DriverManager.getConnection(url, "root", "12345");
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            // 년/월/일 다 제대로 입력됐다면 생일 정보 저장
                            String date;
                            if (year == null || month == null || day == null)
                                date = null;
                            else
                                date = year + "-" + month + "-" + day;
                            nickname = tf1.getText();
                            // 각 정보들 db에 저장
                            // nickname
                            pstmt.setString(1, nickname);
                            // username
                            if (tf2.getText() == null)
                                pstmt.setString(2, "null");
                            else
                                pstmt.setString(2, tf2.getText());
                            // pw
                            pstmt.setString(3, pw);
                            // email
                            if (tf4.getText() == null)
                                pstmt.setString(4, "null");
                            else
                                pstmt.setString(4, tf4.getText());
                            // gender
                            pstmt.setString(5, gender);
                            // birthday
                            pstmt.setString(6, date);
                            // article
                            if (tf5.getText() == null)
                                pstmt.setString(7, "null");
                            else
                                pstmt.setString(7, tf5.getText());
                            // profile
                            pstmt.setInt(8, Image.imageId);
                            pstmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Registered Successfully!", "Register", 1);

                            f1.setVisible(false);// 다 완료되면 로그인 화면으로
                        } catch (SQLException e1) {
                            // 그 외의 오류가 날 경우
                            System.out.println("SQL error" + e1.getMessage());
                            JOptionPane.showMessageDialog(null, "Type information correctly!", "Wrong", 1);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

}
