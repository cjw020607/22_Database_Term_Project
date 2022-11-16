package com.instagram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/instagram";
            Connection conn = DriverManager.getConnection(url, "root", "12345");
            Statement stmt = conn.createStatement();
            Scanner sc = new Scanner(System.in);

            while (true) {
                login(stmt);
                regiter(stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static boolean regiter(Statement stmt) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID: ");
        String nickname = sc.nextLine();
        System.out.print("PW: ");
        String pw = sc.nextLine();
        System.out.print("PW: ");
        String email = sc.nextLine();

        ResultSet rs = stmt.executeQuery(String.format("select * from users where nickname = \'%s\'", nickname));
        if (rs.next()) {
            System.out.println("중복되는 닉네임이 존재합니다.");
            return false;

        } else {
            stmt.executeUpdate(String.format(
                    "Insert into Users(nickname, pw, email, is_created) values( \'%s\', \'%s\', \'%s\', now())",
                    nickname, pw, email));
            return true;
        }
    }

    static boolean login(Statement stmt) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID: ");
        String nickname = sc.nextLine();
        System.out.print("PW: ");
        String pw = sc.nextLine();

        ResultSet rs = stmt
                .executeQuery(String.format("select * from users where nickname = \'%s\'", nickname));
        if (!rs.next()) {
            System.out.println("입력한 아이디가 존재하지 않습니다.");
            return false;
        } else {
            rs = stmt.executeQuery(
                    String.format("select * from users where nickname = \'%s\' and pw = \'%s\'", nickname, pw));
            if (!rs.next()) {
                System.out.println("비밀번호가 일치하지 않습니다.");
                return false;
            } else {
                System.out.println(String.format("%s님 환영합니다. 로그인에 성공하였습니다.", rs.getString("nickname")));
                // print(rs, 12);
                return true;
            }
        }
    }

    static void print(ResultSet rs, int length) throws SQLException {
        for (int i = 1; i < length + 1; i++) {
            System.out.print(rs.getString(i));
            System.out.print(' ');
        }
        System.out.println();
    }
}
