package controllers;

import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Manages all those xXx_MLG_xXx Qu1ck5c0p3s, m8.
 * @author Etienne
 */
public class SoundManager implements Observer{
    
    /**
     * Holds the track to be played by the mediaplayer.
     */
    static private Media track;
    
    /**
     * Responsible for playing the sound effects.
     */
    static private MediaPlayer mediaplayer;
    
    Thread bgm;
    Thread channel;
    
    /**
     * Indicates the event when firing the mediaplayer.
     */
    static public enum State{
	/**
	 * Use when any player scores.
	 */
	on_score,
	
	/**
	 * Use when the application starts.
	 */
	on_startup,
	
	/**
	 * Use when the round has ended.
	 */
	on_endround,
	
	/**
	 * Use when the puck collides.
	 */
	on_collide
    }
    
    /**
     * Array of sounds from the soundpack.
     */
    static private final String[] Sounds = {
	"hitmarker.mp3",
	"intervention 420.mp3",
	"GET NOSCOPED.mp3",
	"Oh Baby A Triple.mp3",
	"DAMN SON WHERED YOU FIND THIS.mp3",
	"SPOOKY.mp3",
	"2SAD4ME.mp3",
	"SKRILLEX Scary.mp3",
	"Darude - Dankstorm.mp3"
    };
    
    /**
     * u havn' a giggle m8?
     */
    private SoundManager(){
	//setTrack(3);
	//mediaplayer = new MediaPlayer(track);
    }
    
    /**
     * 
     */
    static public void setTrack(int index){
	String mp3 = Paths.get("audio/" + Sounds[index]).toUri().toString();
	track = new Media(mp3);
    }
    
    /**
     * 
     */
    static public String getTrack(){
	return track.getSource();
    }
    
    /**
     * 
     */
    static public void play(int index){
	setTrack(index);
	mediaplayer = new MediaPlayer(track);
	mediaplayer.play();
    }
    
    /**
     * 
     */
    static public void pause(){
	mediaplayer.pause();
    }
    
    /**
     * 
     */
    static public void stop(){
	mediaplayer.stop();
    }
    
    @Override
    public void update(Observable o, Object arg) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}