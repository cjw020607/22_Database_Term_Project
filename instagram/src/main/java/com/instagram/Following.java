package com.instagram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Following {
    Statement state;
    String follower, mode;
    ResultSet rs;
    JFrame f1;
    JLabel l1;
    String[] array = {};

    public Following(Statement stmt, String m) {
        state = stmt;
        mode = m;
    }

    public void main() throws SQLException {
        String browsed = Browsed.nickname;
        String nickname = CurrentUser.nickname;

        // 다른 user의 following을 볼 때
        if (mode == "other") {
            rs = state.executeQuery(
                    String.format("select* from follow where nickname = \'%s\'", browsed));
        }
        // 나의 follower를 볼 때
        else if (mode == "me") {
            rs = state.executeQuery(
                    String.format("select* from follow where nickname = \'%s\'", nickname));
        }
        String var;

        // 정보가 있으면 following_name만 가져와서 array에 넣음
        while (rs.next()) {
            var = rs.getString("following_name");
            // 값을 배열에 넣는 함수 호출
            array = Add(array, var);
        }

        ListTest listTest;
        String current = "Following List";
        // following list 가져오기
        if (array != null)
            listTest = new ListTest(array, current);
    }

    private static String[] Add(String[] originArray, String Val) {

        // 원래 배열이 null이면 들어갈 값을 배열의 0번째에 넣어줌
        if (originArray == null) {
            originArray[0] = Val;
            return originArray;
        }

        // (원본 배열의 크기 + 1)를 크기를 가지는 배열을 생성
        String[] newArray = new String[originArray.length + 1];

        // 새로운 배열에 값을 순차적으로 할당
        for (int index = 0; index < originArray.length; index++) {
            newArray[index] = originArray[index];
        }

        // 새로운 배열의 마지막 위치에 추가하려는 값을 할당
        newArray[originArray.length] = Val;

        // 새로운 배열을 반환
        return newArray;
    }
}