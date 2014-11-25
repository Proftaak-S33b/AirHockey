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
import org.jbox2d.common.Vec2;

/**
 * Controller for managing game logic
 *
 * @author Joris
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

                    //Blue side
                    drawSide(Color.BLUE);
            
            //Green side
            drawSide(Color.GREEN);

            //Red side
            drawSide(Color.RED);

            drawPuck();

            player_Move(true, true);
            
            drawPod();
                            }
            });

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Draws a given side. Refactored from Draw().
     *
     * @param color
     */
    public void drawSide(Color color) {
        gc.setStroke(color);
        gc.setFill(color);

        // fieldcorners
        Vec2 field_bottomleft = Convert(MathUtillities.getFieldCorners(MathUtillities.Corner.A));
        Vec2 field_top = Convert(MathUtillities.getFieldCorners(MathUtillities.Corner.I));
        Vec2 field_bottomright = Convert(MathUtillities.getFieldCorners(MathUtillities.Corner.D));

        // goalcorners / sides
        Vec2 goal_bottomleft = Convert(MathUtillities.getGoalCorners(MathUtillities.Corner.B));
        Vec2 goal_bottomright = Convert(MathUtillities.getGoalCorners(MathUtillities.Corner.C));
        Vec2 goal_lefttop = Convert(MathUtillities.getGoalCorners(MathUtillities.Corner.E));
        Vec2 goal_leftbottom = Convert(MathUtillities.getGoalCorners(MathUtillities.Corner.F));
        Vec2 goal_rightbottom = Convert(MathUtillities.getGoalCorners(MathUtillities.Corner.G));
        Vec2 goal_righttop = Convert(MathUtillities.getGoalCorners(MathUtillities.Corner.H));

        if (color == Color.RED) {

            //  links onder naar linksonder van goal
            gc.strokeLine(
                    field_bottomleft.x,
                    field_bottomleft.y,
                    goal_bottomleft.x,
                    goal_bottomleft.y);

            // omlaag
            gc.strokeLine(
                    goal_bottomleft.x,
                    goal_bottomleft.y,
                    goal_bottomleft.x,
                    goal_bottomleft.y + 20 // moet halve pucksize zijn
            );

            //  verbind twee punten
            gc.strokeLine(
                    goal_bottomright.x,
                    goal_bottomright.y,
                    goal_bottomright.x,
                    goal_bottomright.y + 20
            );

            //  omlaag
            gc.strokeLine(
                    goal_bottomright.x,
                    goal_bottomright.y + 20,
                    goal_bottomleft.x,
                    goal_bottomleft.y + 20
            );

            // schaduw
            gc.setStroke(Color.GREY);
            gc.strokeLine(
                    goal_bottomleft.x,
                    goal_bottomleft.y,
                    goal_bottomright.x,
                    goal_bottomright.y);
            gc.setStroke(color);

            // rechtsonder naar rechtsonder van goal
            gc.strokeLine(
                    field_bottomright.x,
                    field_bottomright.y,
                    goal_bottomright.x,
                    goal_bottomright.y);
        }

        if (color == Color.BLUE) {

            //
            gc.strokeLine(
                    field_top.x,
                    field_top.y,
                    goal_lefttop.x,
                    goal_lefttop.y);

            /* teken goal */
            //
            gc.strokeLine(
                    goal_lefttop.x,
                    goal_lefttop.y,
                    goal_lefttop.x - 18,
                    goal_lefttop.y - 12
            );

            //
            gc.strokeLine(
                    goal_leftbottom.x,
                    goal_leftbottom.y,
                    goal_leftbottom.x - 18,
                    goal_leftbottom.y - 12
            );

            //
            gc.strokeLine(
                    goal_lefttop.x - 18,
                    goal_lefttop.y - 12,
                    goal_leftbottom.x - 18,
                    goal_leftbottom.y - 12
            );

            // schaduw
            gc.setStroke(Color.GREY);
            gc.strokeLine(
                    goal_lefttop.x,
                    goal_lefttop.y,
                    goal_leftbottom.x,
                    goal_leftbottom.y);
            gc.setStroke(color);

            //
            gc.strokeLine(
                    field_bottomleft.x,
                    field_bottomleft.y,
                    goal_leftbottom.x,
                    goal_leftbottom.y);

        }

        if (color == Color.GREEN) {

            //
            gc.strokeLine(
                    field_bottomright.x,
                    field_bottomright.y,
                    goal_rightbottom.x,
                    goal_rightbottom.y);

            /* teken goal */
            gc.strokeLine(
                    goal_righttop.x,
                    goal_righttop.y,
                    goal_righttop.x + 18,
                    goal_righttop.y - 12
            );

            //
            gc.strokeLine(
                    goal_rightbottom.x,
                    goal_rightbottom.y,
                    goal_rightbottom.x + 18,
                    goal_rightbottom.y - 12
            );

            //
            gc.strokeLine(
                    goal_righttop.x + 18,
                    goal_righttop.y - 12,
                    goal_rightbottom.x + 18,
                    goal_rightbottom.y - 12
            );

            //
            gc.setStroke(Color.GRAY);
            gc.strokeLine(
                    goal_rightbottom.x,
                    goal_rightbottom.y,
                    goal_righttop.x,
                    goal_righttop.y);
            gc.setStroke(color);

            //
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
        vector.y = MathUtillities.rotateVector(vector).y * scale;

        return vector;
    }

    /**
     * Draws the puck. Refactored from Draw().
     */
    public void drawPuck() {
        gc.setFill(Color.BLACK);

        Puck puck = gameworld.getPuck();
        Vec2 position = Convert(puck.getPosition());
        double pucksize = MathUtillities.getPuckSize() * scale;

        gc.fillOval(position.x, position.y, pucksize, pucksize);
    }

    /**
     * Draws an individual pod.
     */
    public void drawPod() {
        Pod playerone = gameworld.getPod(0);
        Pod playertwo = gameworld.getPod(1);
        Pod playerthree = gameworld.getPod(2);
        double size = MathUtillities.getPodSize() * 10;
        Vec2 vector = Convert(playerone.getPosition());
        Vec2 vector2 = Convert(playertwo.getPosition());
        Vec2 vector3 = Convert(playerthree.getPosition());

        gc.setFill(Color.RED);
        gc.fillOval(vector.x, vector.y, size, size);

        gc.setFill(Color.BLUE);
        gc.fillOval(vector2.x, vector2.y, size, size);

        gc.setFill(Color.GREEN);
        gc.fillOval(vector3.x, vector3.y, size, size);

        // AI
        //AI_CalculateMovement(corners);
    }

    public void player_Move(boolean playerMoveRight, boolean playerMoveLeft) {
        if (playerMoveRight) {
            if (gameworld.getPod(0).getPosition().x < MathUtillities.getGoalCorners(MathUtillities.Corner.C).x - MathUtillities.getPodSize()) {
                gameworld.getPod(0).moveRight(0);
            }
        }
        if (playerMoveLeft) {
            if (gameworld.getPod(0).getPosition().x > MathUtillities.getGoalCorners(MathUtillities.Corner.B).x) {
                gameworld.getPod(0).moveLeft(0);
            }
        }
        drawPod();
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
