package com.instagram;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class Image {
    public static final int SCALE_SMOOTH = 0;

    // 이미지 초기값=5(기본 이미지)
    static int imageId = 5;

    static ImageIcon img1;
    static ImageIcon img2;
    static ImageIcon img3;
    static ImageIcon img4;
    static ImageIcon img5;

    ResultSet rs;
    Statement state;

    JPanel p1;

    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;

    JFrame f1;

    public Image(Statement stmt) throws SQLException {
        state = stmt;
        rs = state.executeQuery(
                String.format("select* from image"));

        // image의 파일 이름과 일치하는 data가 있으면 img 배열에 넣음
        int i = 0;
        while (rs.next()) {
            img[i] = rs.getString("file_name");
            i++;
        }
        // 최종 이미지 파일 이름 설정
        String file = "./image/";
        Image.img1 = new ImageIcon(file + img[0]);
        Image.img2 = new ImageIcon(file + img[1]);
        Image.img3 = new ImageIcon(file + img[2]);
        Image.img4 = new ImageIcon(file + img[3]);
        Image.img5 = new ImageIcon(file + img[4]);

    }

    String[] img = new String[5];

    public void main() {

        f1 = new JFrame("Profile Image");
        p1 = new JPanel();
        p1.setBackground(Color.WHITE);

        b1 = new JButton(img1);
        b1.setPreferredSize(new Dimension(180, 180));

        b2 = new JButton(img2);
        b2.setPreferredSize(new Dimension(180, 180));

        b3 = new JButton(img3);
        b3.setPreferredSize(new Dimension(180, 180));

        b4 = new JButton(img4);
        b4.setPreferredSize(new Dimension(180, 180));

        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        p1.add(b4);

        f1.add(p1);

        // 각 사진이 눌릴 때 그 사진의 image id를 설정함

        // pochacco 사진이 눌릴 때
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.setVisible(false);
                Image.imageId = 1;
            }
        });
        // quokka 사진이 눌릴 떄
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.setVisible(false);
                Image.imageId = 2;
            }
        });

        // moon 사진이 눌릴 때
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.setVisible(false);
                Image.imageId = 3;

            }
        });

        // cake 사진이 눌릴 때
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.setVisible(false);
                Image.imageId = 4;

            }
        });

        f1.setSize(400, 500);
        // f1.setLayout(null);
        f1.setVisible(true);
    }

}
