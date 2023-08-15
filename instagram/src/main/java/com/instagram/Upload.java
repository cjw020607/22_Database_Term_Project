package com.instagram;

import java.sql.*;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class Upload {

    private JFrame frame;
    // private JButton image3_button;
    static int image_id = 5;
    Statement state;

    public Upload(Statement stmt) {
        state = stmt;
        Upload.image_id = 5;
    }

    public void main() {
        frame = new JFrame("Upload");
        frame.setBounds(100, 100, 800, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        // upload_section(업로드 할 사진 + next page 버튼이 들어갈 공간)
        JPanel Upload_section = new JPanel();
        Upload_section.setBounds(0, 60, 786, 273);
        frame.getContentPane().add(Upload_section);
        Upload_section.setLayout(null);

        // 제목
        JLabel l1 = new JLabel("Select an image to upload(not necessary!)");
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l1.setBounds(200, 10, 400, 30);
        frame.add(l1);

        // next 버튼
        JButton Next_button = new JButton("Next");
        Next_button.setVisible(true);
        Next_button.setBackground(SystemColor.activeCaption);
        Next_button.setBounds(695, 10, 91, 45);
        frame.add(Next_button);

        // next 버튼 눌렀을 때
        Next_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // post의 content를 입력할 창 불러오기
                PostContent content = new PostContent(state);
                content.main();
                // upload창은 끄기
                frame.setVisible(false);
            }
        });

        // 사진이 들어갈 버튼
        final JButton Upload_section_button = new JButton();
        Upload_section_button.setBackground(new Color(255, 255, 255));
        Upload_section_button.setBounds(0, 0, 786, 333);
        Upload_section.add(Upload_section_button);

        // pochacco image
        ImageIcon icon1 = new ImageIcon("./image/pochacco.png");
        Image image1 = icon1.getImage();
        Image changeImage1 = image1.getScaledInstance(380, 224, Image.SCALE_SMOOTH);
        final ImageIcon pochacco = new ImageIcon(changeImage1);

        // pochacco가 들어갈 위치 설정
        JPanel section1 = new JPanel();
        section1.setBounds(0, 333, 380, 224);
        frame.getContentPane().add(section1);
        section1.setLayout(null);

        // pochacco 이미지가 들어갈 버튼 관련
        JButton image1_button = new JButton(pochacco);
        image1_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 클릭 시 upload_section_button의 이미지를 pochacco로 변경
                Upload_section_button.setIcon(pochacco);
                // image_id 설정하기
                Upload.image_id = 1;
            }
        });
        image1_button.setBounds(0, 0, 380, 224);
        section1.add(image1_button);

        // quokka image
        ImageIcon icon2 = new ImageIcon("./image/quokka.png");
        Image image2 = icon2.getImage();
        Image changeImage2 = image2.getScaledInstance(404, 224, Image.SCALE_SMOOTH);
        final ImageIcon quokka = new ImageIcon(changeImage2);

        // quokka가 들어갈 위치 설정
        JPanel section2 = new JPanel();
        section2.setBounds(382, 333, 404, 224);
        frame.getContentPane().add(section2);

        // quokka 이미지가 들어갈 버튼 관련
        JButton image2_button = new JButton(quokka);
        image2_button.setBounds(0, 0, 404, 224);
        image2_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 클릭 시 upload_section_button의 이미지를 quokka로 변경
                Upload_section_button.setIcon(quokka);
                // image_id 설정하기
                Upload.image_id = 2;
            }
        });
        section2.setLayout(null);
        section2.add(image2_button);

        // moon image
        ImageIcon icon3 = new ImageIcon("./image/moon.png");
        Image image3 = icon3.getImage();
        Image changeImage3 = image3.getScaledInstance(380, 207, Image.SCALE_SMOOTH);
        final ImageIcon moon = new ImageIcon(changeImage3);

        // moon 이미지가 들어갈 위치 설정
        JPanel section3 = new JPanel();
        section3.setBounds(0, 556, 380, 207);
        frame.getContentPane().add(section3);
        section3.setLayout(null);

        // moon 이미지가 들어갈 버튼 관련
        JButton image3_button;
        image3_button = new JButton(moon);
        image3_button.setBounds(0, 0, 380, 197);
        image3_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 클릭 시 upload_section_button의 이미지를 moon로 변경
                Upload_section_button.setIcon(moon);
                // image_id 설정하기
                Upload.image_id = 3;
            }
        });
        section3.add(image3_button);

        // cake image
        ImageIcon icon4 = new ImageIcon("./image/cake.png");
        Image image4 = icon4.getImage();
        Image changeImage4 = image4.getScaledInstance(404, 207, Image.SCALE_SMOOTH);
        final ImageIcon cake = new ImageIcon(changeImage4);

        // cake 이미지가 들어갈 위치 설정
        JPanel section4 = new JPanel();
        section4.setBounds(382, 556, 404, 207);
        frame.getContentPane().add(section4);
        section4.setLayout(null);

        // cake 이미지가 들어갈 버튼 관련
        JButton image4_button = new JButton(cake);
        image4_button.setBounds(0, 0, 404, 197);
        image4_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 클릭 시 upload_section_button의 이미지를 cake로 변경
                Upload_section_button.setIcon(cake);
                // image_id 설정하기
                Upload.image_id = 4;
            }
        });
        section4.add(image4_button);
    }
}
