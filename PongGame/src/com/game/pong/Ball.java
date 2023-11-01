package com.game.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball extends Rectangle {

    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 2;

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.random = new Random();
        int randomXDirection = this.random.nextInt(2);
        if (randomXDirection == 0) {
            randomXDirection--;
        }
        this.setXDirection(randomXDirection * this.initialSpeed);

        int randomYDirection = this.random.nextInt(2);
        if (randomYDirection == 0) {
            randomYDirection--;
        }
        this.setYDirection(randomYDirection * this.initialSpeed);

    }

    public void setXDirection(int randomXDirection) {
        this.xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        this.yVelocity = randomYDirection;
    }

    public void move() {
        this.x += this.xVelocity;
        this.y += this.yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(this.x, this.y, this.height, this.width);
    }
}