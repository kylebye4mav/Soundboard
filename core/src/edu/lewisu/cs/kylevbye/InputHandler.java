package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

public class InputHandler extends InputAdapter {
	
	///
	///	Functions
	///
	
	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		
		PlayerInput.mouseX = screenX;
		PlayerInput.mouseY = screenY;
		
		return true;
		
	}
	
	@Override 
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		
		switch(button) {
		
		case Buttons.LEFT:
			PlayerInput.leftMouseDown = true;
			PlayerInput.leftMouseClicked = true;
			PlayerInput.leftMouseClickVector = new Vector2(screenX, screenY);
			break;
		case Buttons.MIDDLE:
			PlayerInput.middleMouseDown = true;
			PlayerInput.middleMouseClicked = true;
			PlayerInput.middleMouseClickVector = new Vector2(screenX, screenY);
			break;
		case Buttons.RIGHT:
			PlayerInput.rightMouseDown = true;
			PlayerInput.rightMouseClicked = true;
			PlayerInput.rightMouseClickVector = new Vector2(screenX, screenY);
			break;
			
		}
		
		return true;
		
	}
	
	@Override 
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		
		switch(button) {
		
		case Buttons.LEFT:
			PlayerInput.leftMouseDown = false;
		case Buttons.MIDDLE:
			PlayerInput.middleMouseDown = false;
		case Buttons.RIGHT:
			PlayerInput.rightMouseDown = false;
			
		}
		
		return true;
		
	}
	
	
	
	@Override
	public boolean keyDown(int keyCode) {
			
		switch (keyCode) {
		
		//	Shift
		case Keys.SHIFT_LEFT:
			PlayerInput.shiftHeld = true;
			break;
		case Keys.SHIFT_RIGHT:
			PlayerInput.shiftHeld = true;
			break;
			
		}
		
		
		return true;
			
	}

	@Override 
	public boolean keyUp(int keyCode) {
		
		switch (keyCode) {
		
		//	Shift
		case Keys.SHIFT_LEFT:
			PlayerInput.shiftHeld = false;
			break;
		case Keys.SHIFT_RIGHT:
			PlayerInput.shiftHeld = false;
			break;
		
			
		}
		
		return true;
		
	}
	
}
