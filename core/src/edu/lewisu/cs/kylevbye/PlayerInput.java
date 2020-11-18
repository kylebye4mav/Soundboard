package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.math.Vector2;

/**
 * This class is a container for many booleans
 * that are true or false whether the player
 * is intended to do certain actions when the
 * key for that action is held.
 */
public class PlayerInput {
	
	public static int mouseX;
	public static int mouseY;
	
	public static Vector2 leftMouseClickVector;
	public static Vector2 middleMouseClickVector;
	public static Vector2 rightMouseClickVector;
	
	
	///
	///	Held Events
	///
	
	/**
	 * True when shift is held. Shift in this
	 * case is intended to be used as a modifier
	 * key.
	 */
	
	public static boolean shiftHeld = false;
	
	public static boolean leftMouseDown = false;
	
	public static boolean middleMouseDown = false;
	
	public static boolean rightMouseDown = false;
	
	///
	///	One-Time Events
	///
	
	public static boolean leftMouseClicked = false;
	
	public static boolean middleMouseClicked = false;
	
	public static boolean rightMouseClicked = false;
	
	///
	///	Functions
	///
	
	public static void resetOneTimeEvents() {
		
		leftMouseClicked = false;
		middleMouseClicked = false;
		rightMouseClicked = false;
		
	}
	
	
	

}
