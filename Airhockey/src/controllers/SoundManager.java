package controllers;

import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Manages all those xXx_MLG_xXx Qu1ck5c0p3s, m8.
 * @author Etienne
 */
public final class SoundManager {
    
    /**
     * Holds the track to be played by the mediaplayer.
     */
    static Media track;
    
    /**
     * Responsible for playing the sound effects.
     */
    static MediaPlayer mediaplayer;
    
    /**
     * Indicates the event when firing the mediaplayer.
     */
    static enum State{
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
    static final String[] Sounds = {
	"hitmarker.mp3",
	"intervention 420.mp3",
	"GET NOSCOPED.mp3",
	"Oh Baby A Triple.mp3",
	"DAMN SON WHERED YOU FIND THIS.mp3",
	"SPOOKY.mp3",
	"2SAD4ME.mp3",
	"SKRILLEX Scary.mp3"
    };
    
    /**
     * u havn' a giggle m8?
     */
    private SoundManager(){
	track = new Media(Sounds[0]);
	mediaplayer = new MediaPlayer(track);
    }
    
    /**
     * 
     */
    static void setTrack(String sound){
	String mp3 = Paths.get(sound).toUri().toString();
	Media track = new Media(mp3);
    }
    
    /**
     * 
     */
    static String getTrack(){
	return track.getSource();
    }
    
    /**
     * 
     */
    static void play(){
	mediaplayer.play();
    }
    
    /**
     * 
     */
    static void pause(){
	mediaplayer.pause();
    }
    
    /**
     * 
     */
    static void stop(){
	mediaplayer.stop();
    }
}