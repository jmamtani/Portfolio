package com.game.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {

    int id;
    int yVelocity;
    int speed = 10;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        switch (this.id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    this.setYDirection(-this.speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    this.setYDirection(this.speed);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    this.setYDirection(-this.speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    this.setYDirection(this.speed);
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (this.id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    this.setYDirection(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    this.setYDirection(0);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    this.setYDirection(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    this.setYDirection(0);
                }
                break;
        }
    }

    public void setYDirection(int yDirection) {
        this.yVelocity = yDirection;
    }

    public void move() {
        this.y = this.y + this.yVelocity;
    }

    public void draw(Graphics g) {
        if (this.id == 1) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.red);
        }
        g.fillRect(this.x, this.y, this.width, this.height);
    }
}