package com.game.pong;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("unused")
public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel() {
        this.newPaddles();
        this.newBall();
        this.score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void newBall() {
        this.random = new Random();
        this.ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2),
                this.random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER,
                BALL_DIAMETER);
    }

    public void newPaddles() {
        this.paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        this.paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH,
                (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH,
                PADDLE_HEIGHT, 2);
    }

    @Override
    public void paint(Graphics g) {
        this.image = this.createImage(this.getWidth(), this.getHeight());
        this.graphics = this.image.getGraphics();
        this.draw(this.graphics);
        g.drawImage(this.image, 0, 0, this);
    }

    public void draw(Graphics g) {
        this.paddle1.draw(g);
        this.paddle2.draw(g);
        this.ball.draw(g);
        this.score.draw(g);
        Toolkit.getDefaultToolkit().sync(); // I forgot to add this line of code in the video, it helps with the animation

    }

    public void move() {
        this.paddle1.move();
        this.paddle2.move();
        this.ball.move();
    }

    public void checkCollision() {

        //bounce ball off top & bottom window edges
        if (this.ball.y <= 0) {
            this.ball.setYDirection(-this.ball.yVelocity);
        }
        if (this.ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            this.ball.setYDirection(-this.ball.yVelocity);
        }
        //bounce ball off paddles
        if (this.ball.intersects(this.paddle1)) {
            this.ball.xVelocity = Math.abs(this.ball.xVelocity);
            this.ball.xVelocity++; //optional for more difficulty
            if (this.ball.yVelocity > 0) {
                this.ball.yVelocity++; //optional for more difficulty
            } else {
                this.ball.yVelocity--;
            }
            this.ball.setXDirection(this.ball.xVelocity);
            this.ball.setYDirection(this.ball.yVelocity);
        }
        if (this.ball.intersects(this.paddle2)) {
            this.ball.xVelocity = Math.abs(this.ball.xVelocity);
            this.ball.xVelocity++; //optional for more difficulty
            if (this.ball.yVelocity > 0) {
                this.ball.yVelocity++; //optional for more difficulty
            } else {
                this.ball.yVelocity--;
            }
            this.ball.setXDirection(-this.ball.xVelocity);
            this.ball.setYDirection(this.ball.yVelocity);
        }
        //stops paddles at window edges
        if (this.paddle1.y <= 0) {
            this.paddle1.y = 0;
        }
        if (this.paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            this.paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        if (this.paddle2.y <= 0) {
            this.paddle2.y = 0;
        }
        if (this.paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            this.paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        //give a player 1 point and creates new paddles & ball
        if (this.ball.x <= 0) {
            this.score.player2++;
            this.newPaddles();
            this.newBall();
            System.out.println("Player 2: " + this.score.player2);
        }
        if (this.ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            this.score.player1++;
            this.newPaddles();
            this.newBall();
            System.out.println("Player 1: " + this.score.player1);
        }
    }

    @Override
    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                this.move();
                this.checkCollision();
                this.repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            GamePanel.this.paddle1.keyPressed(e);
            GamePanel.this.paddle2.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            GamePanel.this.paddle1.keyReleased(e);
            GamePanel.this.paddle2.keyReleased(e);
        }
    }
}
