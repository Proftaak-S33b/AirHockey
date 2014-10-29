/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 * Runs all the tests in one run.
 * Initialize to start.
 * @author Etienne
 */
public class TestSuite {
    
    private GameWorldTest gameworld;
    private AdministratorTest administrator;
    private PuckTest puck;
    private PodTest pod;
    private HumanTest human;
    private AITest ai;
    private GoalTest goal;
    private DifficultyTest difficulty;
    private FieldTest field;
    
    public TestSuite(){
        gameworld = new GameWorldTest();
        administrator = new AdministratorTest();
        puck = new PuckTest();
        pod = new PodTest();
        human = new HumanTest();
        ai = new AITest();
        goal = new GoalTest();
        difficulty = new DifficultyTest();
        field = new FieldTest();
        
        System.out.println("Tests finished.");
    }        
}
