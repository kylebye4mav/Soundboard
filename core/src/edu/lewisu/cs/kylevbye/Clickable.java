package edu.lewisu.cs.kylevbye;

public interface Clickable {
	
	/**
	 * When a mouse event occurs, return true if the mouse was over this object area.
	 * 
	 * @param	xIn	mouse screen position x
	 * @param	yIn	mouse screen position y
	 * @param	button type that was clicked
	 * @return	true/false
	 */
	public boolean wasClicked(float xIn, float yIn);
	
	/**
	 * Execute if object was left clicked on.
	 * 
	 * Please use values from <code>EventTypes</code>
	 * in the eventTypeIn parameter.
	 * 
	 * @param	button type that was clicked
	 * @see	edu.lewisu.cs.kylevbye.EventTypes
	 */
	public void onClicked(int eventTypeIn);
	
}
