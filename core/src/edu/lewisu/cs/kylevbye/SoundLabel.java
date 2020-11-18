package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Disposable;

public class SoundLabel implements Clickable, Disposable, Drawable {
	
	///
	///	Fields
	///
	
	private Sound sound;
	private Label label;
	private LinkedHashMap<Integer, ArrayList<Event>> events;
	
	///
	///	Getters
	///
	
	public Sound getSound() { return sound; }
	public Label getLabel() { return label; }
	
	///
	///	Setters
	///
	
	public void setSound(Sound soundIn) { sound = soundIn; }
	public void setLabel(Label labelIn) { label = labelIn; }
	
	///
	///	Functions
	///
	
	@Override
	public void draw(Batch batchIn, float parentAlphaIn) {
		label.draw(batchIn, parentAlphaIn);
	}
	
	@Override
	public boolean wasClicked(float xIn, float yIn) {
		
		float x = label.getX(); 
		float y = label.getY();
		float width = label.getWidth();
		float height = label.getHeight();
		float scaleX = label.getScaleX();
		float scaleY = label.getScaleY();
		
		if ( (xIn >= x && xIn <= (x + (width)*scaleX)) && (yIn >= y && yIn <= (y + (height)*scaleY)) ) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onClicked(int eventTypeIn) {
		
		if (events.containsKey(eventTypeIn)) for (Event e : events.get(eventTypeIn)) e.run();
		
	}
	
	/**
	 * Registers the event to this instance by its type.
	 * In other words, this method adds the passed event
	 * to events with eventType being its key.
	 * 
	 * Please use a value from EventTypes for the integer
	 * parameter.
	 * 
	 * @param	eventType	integer from EventTypes
	 * @param	e	event to register
	 * @see edu.lewisu.cs.kylevbye.EventTypes
	 */
	public void addEvent(int eventType, Event e) { 
		
		//	Register event type if it already isn't registered.
		if (!events.containsKey(eventType)) events.put(eventType, new ArrayList<Event>());
		
		//	Register
		events.get(eventType).add(e);
		
	}
	
	/**
	 * Unregisters the event to this instance by its type.
	 * In other words, this method removes the passed event
	 * from events with eventType being its key.
	 * 
	 * Please use a value from EventTypes for the integer
	 * parameter.
	 * 
	 * @param	eventType	integer from EventTypes
	 * @param	e	event to unregister
	 * @see edu.lewisu.cs.kylevbye.EventTypes
	 */
	public void removeEvent(int eventType, Event e) { events.remove(eventType, e); }
	
	/**
	 * Unregisters all the events associated with the provided
	 * event type.
	 * 
	 * Please use a value from EventTypes for the integer
	 * parameter.
	 * 
	 * @param	eventType	integer from EventTypes 
	 * @see edu.lewisu.cs.kylevbye.EventTypes
	 */
	public void removeEventType(int eventType) { events.remove(eventType); }
	
	/**
	 * Clears the events hash map. Removes all events
	 * and event types.
	 */
	public void clearAllEvents() { events.clear(); }
	
	///
	///	Constructors
	///
	
	public SoundLabel(float xIn, float yIn, float scaleXIn, float scaleYIn) {
		
		this(0,0,0,0,null,null,null);
		
	}
	
	public SoundLabel(float xIn, float yIn, float scaleXIn, float scaleYIn, Sound soundIn, Label labelIn) {
		
		setSound(soundIn); setLabel(labelIn);
		label.setX(xIn); label.setY(yIn); label.setScaleX(scaleXIn); label.setScaleY(scaleYIn);
		
		events = new LinkedHashMap<Integer, ArrayList<Event>>();
		
	}
	
	public SoundLabel(float xIn, float yIn, float scaleXIn, float scaleYIn, Sound soundIn, String labelTextIn, LabelStyle labelStyleIn) {
		
		setSound(soundIn); 
		
		Label newLabel = new Label(labelTextIn, labelStyleIn);
		newLabel.setText(labelTextIn);
		setLabel(newLabel);
		label.setX(xIn); label.setY(yIn); label.setScaleX(scaleXIn); label.setScaleY(scaleYIn);
		
		events = new LinkedHashMap<Integer, ArrayList<Event>>();
		
	}
	
	///
	///	Disposable
	///
	
	@Override
	public void dispose() {
		
		//	Clean
		sound.dispose();
		
		//	Nullify
		sound = null;
		label = null;
		
	}
}
