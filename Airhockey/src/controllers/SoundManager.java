package controllers;

//<editor-fold defaultstate="collapsed" desc="imports">

import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//</editor-fold>

/**
 * Manages all those dank xXx_MLG_xXx Qu1ck5c0p3s, m8.
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
    // States are not currently used but should to dynamize/randomize sounds played.
	
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
     * If you find indexes confusing and generally 2complex4u, this should help.
     * Just make sure to keep this in line with Sounds. It doesn't update itself.
     * Values given correspond to the filenames.
     * <i>To keep this correctly updated with ease, copypaste Sounds' contents into 
     * this, then remove quotes and the ".mp3" postfix, replace spaces with underscores,
     * then make sure names do not start with numbers.</i>
     */
    static public enum SoundEffects{
	hitmarker,
	intervention_420,
	GET_NOSCOPED,
	Oh_Baby_A_Triple,
	DAMN_SON_WHERED_YOU_FIND_THIS,
	SPOOKY,
	_2SAD4ME, // can't start with numbers, hence the underscore.
	SKRILLEX_Scary,
	Darude_Dankstorm
    };
    
    /**
     * u havn' a giggle m8?
     */
    private SoundManager(){
	//setTrack(3);
	//mediaplayer = new MediaPlayer(track);
    }
    
    /**
     * Sets the track to be played.
     * @param index the indexer of the list of sounds to indicate what to play.
     */
    static public void setTrack(int index){
	String mp3 = Paths.get("audio/" + Sounds[index]).toUri().toString();
	track = new Media(mp3);
    }

    /**
     * Sets the track to be played.
     * @param effect_to_be_played The effect to...seriously?
     */
    static public void setTrack(SoundEffects effect_to_be_played){
	// Ordinal converts the enum to an int without adding getters.
	String mp3 = Paths.get("audio/" + Sounds[effect_to_be_played.ordinal()]).toUri().toString();
	track = new Media(mp3);
    }
    
    /**
     * Returns the track from the MediaPlayer.
     * @return a String with the (file)name of the track.
     */
    static public String getTrack(){
	return track.getSource();
    }
    
    /**
     * Plays a given track.
     * @param index an integer with the indexer of the Sounds to indicate what to play.
     */
    static public void play(int index){
	setTrack(index);
	mediaplayer = new MediaPlayer(track);
	mediaplayer.play();
    }

    /**
     * Plays a given track.
     * @param effect the SoundEffects object of the track to play.
     */
    static public void play(SoundEffects effect) {
	setTrack(effect);
	mediaplayer = new MediaPlayer(track);
	mediaplayer.play();
    }
    
    /**
     * Pauses the mediaplayer.
     */
    static public void pause(){
	mediaplayer.pause();
    }
    
    /**
     * Stops the mediaplayer.
     */
    static public void stop(){
	mediaplayer.stop();
    }
    
    /**
     * @deprecated This isn't used, yet. However SoundManager is made an Observer
     * for extensibility, so if at any point this is needed, feel free to change.
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}