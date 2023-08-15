package com.instagram;

import javax.swing.*;
import java.awt.*;

public class ListTest extends JFrame {
    JLabel l1;

    ListTest(String[] array, String current) {

        l1 = new JLabel(current);
        l1.setFont(new Font("Serif", Font.BOLD, 20));

        setTitle(current);

        Container c = getContentPane();
        c.add(l1);
        c.setLayout(new FlowLayout());

        // array를 list에 넣기
        JList scrollList = new JList(array);

        // list 안의 내용의 크기 설정
        scrollList.setFixedCellHeight(50);
        scrollList.setFixedCellWidth(100);

        JScrollPane scrolled = new JScrollPane(scrollList);
        c.add(scrolled);

        setSize(400, 500);
        setVisible(true);
    }
}