/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game.GameWorld;
import game.MathUtillities;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import networking.IPlayer;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.collision.shapes.CircleShape;

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
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    gc.setFill(Color.WHITESMOKE);
                    gc.fillRect(0.0, 0.0, 550, 550);

                    //Draw field
                    drawField(Color.RED, Color.GREEN, Color.BLUE);

                    drawPuckandPod(Color.BLACK);
                    //player_Move(true, true);
                }
            });

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Draws the field. Refactored from Draw().
     *
     * @param colorBottom Color of the bottom side
     * @param colorRight Color of the right side
     * @param colorLeft Color of the left side
     */
    public void drawField(Color colorBottom, Color colorRight, Color colorLeft) {
        // fieldcorners
        Vec2 field_bottomleft = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.A));
        Vec2 field_top = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.I));
        Vec2 field_bottomright = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.D));

        // goalcorners / sides
        Vec2 goal_bottomleft = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.B));
        Vec2 goal_bottomright = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.C));
        Vec2 goal_lefttop = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.E));
        Vec2 goal_leftbottom = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.F));
        Vec2 goal_rightbottom = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.G));
        Vec2 goal_righttop = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.H));

        //Draw bottom side
        gc.setStroke(colorBottom);
        gc.setFill(colorBottom);
        {

            // Bottom left corner to goal
            gc.strokeLine(
                    field_bottomleft.x,
                    field_bottomleft.y,
                    goal_bottomleft.x,
                    goal_bottomleft.y);

            // Drawing goal down
            gc.strokeLine(
                    goal_bottomleft.x,
                    goal_bottomleft.y,
                    goal_bottomleft.x,
                    goal_bottomleft.y - 20 // moet halve pucksize zijn
            );

            //  Drawing goal
            gc.strokeLine(
                    goal_bottomright.x,
                    goal_bottomright.y,
                    goal_bottomright.x,
                    goal_bottomright.y - 20
            );

            //  Drawing goal down
            gc.strokeLine(
                    goal_bottomright.x,
                    goal_bottomright.y - 20,
                    goal_bottomleft.x,
                    goal_bottomleft.y - 20
            );

            // schaduw
            gc.setStroke(Color.GREY);
            gc.strokeLine(
                    goal_bottomleft.x,
                    goal_bottomleft.y,
                    goal_bottomright.x,
                    goal_bottomright.y);
            gc.setStroke(colorBottom);

            // rechtsonder naar rechtsonder van goal
            gc.strokeLine(
                    field_bottomright.x,
                    field_bottomright.y,
                    goal_bottomright.x,
                    goal_bottomright.y);
        }

        //Drawing left side
        gc.setStroke(colorLeft);
        gc.setFill(colorLeft);
        {
            //Draw from top of the field to goal
            gc.strokeLine(
                    field_top.x,
                    field_top.y,
                    goal_lefttop.x,
                    goal_lefttop.y);

            //Draw goal in
            gc.strokeLine(
                    goal_lefttop.x,
                    goal_lefttop.y,
                    goal_lefttop.x - 18,
                    goal_lefttop.y + 12
            );

            //Draw goal in
            gc.strokeLine(
                    goal_leftbottom.x,
                    goal_leftbottom.y,
                    goal_leftbottom.x - 18,
                    goal_leftbottom.y + 12
            );

            //Draw goal
            gc.strokeLine(
                    goal_lefttop.x - 18,
                    goal_lefttop.y + 12,
                    goal_leftbottom.x - 18,
                    goal_leftbottom.y + 12
            );

            // schaduw
            gc.setStroke(Color.GREY);
            gc.strokeLine(
                    goal_lefttop.x,
                    goal_lefttop.y,
                    goal_leftbottom.x,
                    goal_leftbottom.y);
            gc.setStroke(colorLeft);

            //Draw side from bottom left to goal
            gc.strokeLine(
                    field_bottomleft.x,
                    field_bottomleft.y,
                    goal_leftbottom.x,
                    goal_leftbottom.y);

        }

        //Draw right side
        gc.setStroke(colorRight);
        gc.setFill(colorRight);
        {

            //From bottom right corner to goal
            gc.strokeLine(
                    field_bottomright.x,
                    field_bottomright.y,
                    goal_rightbottom.x,
                    goal_rightbottom.y);

            // Goal in
            gc.strokeLine(
                    goal_righttop.x,
                    goal_righttop.y,
                    goal_righttop.x + 18,
                    goal_righttop.y + 12
            );

            // Goal in
            gc.strokeLine(
                    goal_rightbottom.x,
                    goal_rightbottom.y,
                    goal_rightbottom.x + 18,
                    goal_rightbottom.y + 12
            );

            // Draw goal
            gc.strokeLine(
                    goal_righttop.x + 18,
                    goal_righttop.y + 12,
                    goal_rightbottom.x + 18,
                    goal_rightbottom.y + 12
            );

            // Draw goal line
            gc.setStroke(Color.GRAY);
            gc.strokeLine(
                    goal_rightbottom.x,
                    goal_rightbottom.y,
                    goal_righttop.x,
                    goal_righttop.y);
            gc.setStroke(colorRight);

            // Draw side from top to goal
            gc.strokeLine(
                    field_top.x,
                    field_top.y,
                    goal_righttop.x,
                    goal_righttop.y);
        }
    }

    /**
     * Scales the vector and rotates the Y-coordinate.
     *
     * @param vector a Vec2 object to convert.
     */
    private Vec2 Convert(Vec2 vector) {
        vector.x = vector.x * scale;
        vector.y = vector.y * scale;

        return vector;
    }

    /**
     * Draws the puck. Refactored from Draw().
     *
     * @param color The color that should be used to draw the puck and pods
     */
    public void drawPuckandPod(Color color) {
        //Set the color
        gc.setStroke(color);
        gc.setFill(color);

        Body bodylist = gameworld.getPhysWorld().getBodyList();
        //While there are physics items, check if circle
        while (bodylist != null) {
            //Get position and type
            Vec2 pos = bodylist.getPosition();
            ShapeType type = bodylist.getFixtureList().getShape().getType();
            //Draw circles
            if (type == ShapeType.CIRCLE) {
                CircleShape cs = (CircleShape) bodylist.getFixtureList().getShape();
                float x = pos.x - cs.getRadius();
                float y = pos.y - cs.getRadius();
                Vec2 drawingPos = Convert(new Vec2(x, y));
                gc.fillOval(drawingPos.x, drawingPos.y, cs.getRadius() * scale * 2, cs.getRadius() * scale * 2);
            }
            bodylist = bodylist.getNext();
        }
    }

    public void player_Move(boolean playerMoveRight, boolean playerMoveLeft) {
        if (playerMoveRight) {
            if (gameworld.getPod(0).getPosition().x < MathUtillities.getCoordinates(MathUtillities.Corner.C).x - MathUtillities.getPodSize() / 2) {
                gameworld.getPod(0).moveRight(0);
            }
        }
        if (playerMoveLeft) {
            if (gameworld.getPod(0).getPosition().x > MathUtillities.getCoordinates(MathUtillities.Corner.B).x + MathUtillities.getPodSize() / 2) {
                gameworld.getPod(0).moveLeft(0);
            }
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
