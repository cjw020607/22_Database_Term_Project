package com.instagram;

import javax.swing.JPanel;

public class Common {
    static JPanel sizedbox(int width, int height) {
        JPanel size = new JPanel();
        size.setSize(width, height);
        return size;
    }
}
