package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.badlogic.gdx.audio.Sound;

public class SoundScreenObject extends ScreenObject implements Playable {
	
	///
	///	Fields
	///
	
	private Sound sound;
	
	///
	///	Getters
	///
	
	public Sound getSound() { return sound; }
	
	///
	///	Setters
	///
	
	public void setSound(Sound soundIn) { sound = soundIn; }
	
	///
	///	Functions
	///
	
	public void play() { sound.play(); }
	
	public void play(float volumeIn) { sound.play(volumeIn); }
	
	public void play(float volumeIn, float pitchIn, float panIn) { sound.play(volumeIn, pitchIn, panIn); }
	
	
	///
	///	Constructor
	///
	
	public SoundScreenObject() {
		this(null, new LinkedHashMap<Integer, ArrayList<Event>>(), null);
	}
	
	public SoundScreenObject(Sound soundIn) {
		this(null, new LinkedHashMap<Integer, ArrayList<Event>>(), soundIn);
	}
	
	public SoundScreenObject(Image imageIn, Sound soundIn) {
		this(imageIn, new LinkedHashMap<Integer, ArrayList<Event>>(), soundIn);
	}
	
	public SoundScreenObject(Image imageIn, LinkedHashMap<Integer, ArrayList<Event>> eventsIn, Sound soundIn) {
		super(imageIn, eventsIn);
		setSound(soundIn);
	}
	
	///
	///	Destructor
	///
	
	public void dispose() {
		
		//	Clean superclass
		super.dispose();
		
		//	Clean
		sound.dispose();
		
		//	Nullify
		sound = null;
		
	}
	
	
}
