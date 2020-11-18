package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class ScreenObject implements Clickable, Disposable, Drawable {
	
	private Image image;
	private LinkedHashMap<Integer, ArrayList<Event>> events;
	
	///
	///	Getters
	///
	
	public Image getImage() { return image; }
	public LinkedHashMap<Integer, ArrayList<Event>> getEvents() { return events; }
	
	///
	///	Setters
	///
	
	public void setImage(Image imageIn) { image = imageIn; }
	
	///
	///	Functions
	///
	
	@Override
	public void draw(Batch batch, float parentAlpha) { image.draw(batch, parentAlpha); }
	
	@Override
	public boolean wasClicked(float xIn, float yIn) {
		
		float x = image.getX(); 
		float y = image.getY();
		float width = image.getWidth();
		float height = image.getHeight();
		float scaleX = image.getScaleX();
		float scaleY = image.getScaleY();
		
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
		
	public ScreenObject() {
		this(null,new LinkedHashMap<Integer, ArrayList<Event>>());
	}
	
	public ScreenObject(Image imageIn) {
		this(imageIn,new LinkedHashMap<Integer, ArrayList<Event>>());
	}
	
	public ScreenObject(Image imageIn, LinkedHashMap<Integer, ArrayList<Event>> eventsIn) {
		setImage(imageIn);
		events = eventsIn;
	}
	
	///
	///	Destructor
	///
	
	@Override
	public void dispose() {
		
		//	Clean
		image.dispose();
		events.clear();
		
		//	Nullify
		image = null;
		events.clear();
	}

}
