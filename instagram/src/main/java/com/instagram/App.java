package com.instagram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

public class App extends JFrame {

    static User_info user;
    static Connection conn;

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        final Start start;
        try {
            // 연결
            String url = "jdbc:mysql://localhost:3306/instagram";
            conn = DriverManager.getConnection(url, "root", "12345");
            Statement stmt = conn.createStatement();

            // 시작화면 불러오기
            start = new Start(stmt);
            start.main();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
