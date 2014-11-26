/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game.GameWorld;
import game.MathUtillities;
import game.Pod;
import game.Puck;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import networking.IPlayer;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Controller for managing game logic
 *
 * @author Maikel
 */
public class GameManager {

    private GameWorld gameworld;
    private GraphicsContext gc;

    final int scale = 10;

    public GameManager(GraphicsContext gc, ArrayList<IPlayer> players) {
        this.gc = gc;
        gameworld = new GameWorld(players);
    }

    /**
     * Draws the sides and puck on the field.
     */
    public void draw() {
        try {
            Platform.runLater(() -> {
                gc.setFill(Color.WHITESMOKE);
                gc.fillRect(0.0, 0.0, 550, 550);

                Body body = gameworld.getPhysWorld().getBodyList();
                while (body != null) {
                    drawBody(body);
                    body = body.getNext();
                }
            });

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void drawBody(Body body) {
        Vec2 position = MathUtillities.rotateVector(body.getPosition());
        ShapeType type = body.getFixtureList().getShape().getType();
        float angle = body.getAngle();
        if (type == ShapeType.EDGE) {
            EdgeShape edge = (EdgeShape) body.getFixtureList().getShape();
            Vec2 point1 = MathUtillities.rotateVector(edge.m_vertex1);
            Vec2 point2 = MathUtillities.rotateVector(edge.m_vertex2);
            gc.strokeLine(point1.x, point2.y, point2.x, point2.y);
        } else if (type == ShapeType.CIRCLE) {
            CircleShape circle = (CircleShape) body.getFixtureList().getShape();
            float radius = circle.getRadius();
            gc.fillOval(position.x, position.y, radius * 20, radius * 20);
        }
    }

    public void update() {
        try {
            Timer physTimer = new Timer("Simulate Physics", true);
            physTimer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    gameworld.getPhysWorld().step(1 / 60f, 10, 5);
                }
            }, 0, (long) (1 / 0.06));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
