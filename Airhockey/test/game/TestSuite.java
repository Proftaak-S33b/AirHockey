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
    
    private CoordinateTest coordinate;
    private AdministratorTest administrator;
    private PlayerTest player;
    private PuckTest puck;
    private PodTest pod;
    private HumanTest human;
    private AITest ai;
    private GoalTest goal;
    private DifficultyTest difficulty;
    private FieldTest field;
    
    public TestSuite(){
        coordinate = new CoordinateTest();
        administrator = new AdministratorTest();
        player = new PlayerTest();
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
