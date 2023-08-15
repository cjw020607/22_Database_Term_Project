package com.instagram;

import java.awt.Font;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class About {
    Statement state;
    String mode;
    static JFrame f1;
    JLabel l1;

    public About(Statement stmt, String m) {
        state = stmt;
        mode = m;
    }

    public void main() throws SQLException {

        // 지금 보고있는 page 주인의 정보를 다 넘김
        Browsed browsed = new Browsed();
        browsed.setOthers(state);

        f1 = new JFrame("About");
        if (mode == "other")
            l1 = new JLabel("About " + Browsed.nickname);
        else if (mode == "me")
            l1 = new JLabel("About me");

        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l1.setBounds(140, 50, 700, 50);

        String[] array = new String[6];

        // 내가 아닌 다른 user의 페이지를 볼 때
        if (mode == "other") {
            array[0] = Browsed.name;
            array[1] = Browsed.email;
            array[2] = Browsed.gender;
            array[3] = Browsed.article;
            array[4] = Browsed.birthday;
            array[5] = Browsed.created_date;
        }
        // 내 페이지를 볼 때
        else if (mode == "me") {
            array[0] = CurrentUser.name;
            array[1] = CurrentUser.email;
            array[2] = CurrentUser.gender;
            array[3] = CurrentUser.article;
            array[4] = CurrentUser.birthday;
            array[5] = CurrentUser.created_date;
        }
        int i = 0;
        // null 값일 경우 비워놓기
        while (i <= 5) {
            if (array[i] == null)
                array[i] = "";
            i++;
        }

        array[0] = "User name: " + array[0];
        array[1] = "Email: " + array[1];
        array[2] = "Gender: " + array[2];
        array[3] = "Article: " + array[3];
        array[4] = "Birthday: " + array[4];
        array[5] = "Created date: " + array[5];

        // array를 list에 넣기
        JList scrollList = new JList(array);
        scrollList.setFixedCellHeight(40);
        scrollList.setFixedCellWidth(300);

        JScrollPane scrolled = new JScrollPane(scrollList);
        scrolled.setBounds(30, 100, 320, 300);

        f1.add(scrolled);
        f1.add(l1);
        f1.setSize(400, 500);
        f1.setLayout(null);
        f1.setVisible(true);

    }
}
