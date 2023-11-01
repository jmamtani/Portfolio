package com.game.pong;

import java.awt.Color;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GamePanel panel;

    GameFrame() {
        this.panel = new GamePanel();
        this.add(this.panel);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
