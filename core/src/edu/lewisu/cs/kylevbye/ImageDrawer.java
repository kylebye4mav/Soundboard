package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class is responsible for using a sprite batch
 * to draw images onto the screen.
 * 
 * @author	Kyle V Bye
 * @see	Image
 * @see	com.badlogic.gdx.graphics.g2d.SpriteBatch
 */
public class ImageDrawer {
	
	///
	///	Fields
	///
	
	private SpriteBatch batch;
	
	///
	///	Getters
	///
	
	public SpriteBatch getBatch() { return batch; }
	
	///
	///	Setters
	///
	
	public void setBatch(SpriteBatch batchIn) { batch = batchIn; }
	
	///
	///	Functions
	///
	
	/**
	 * Draws the provided image to the sprite batch.
	 * 
	 * @param	i	image to draw
	 */
	public void draw(Drawable i) {
		i.draw(batch, 1);
	}
	
	///
	///	Constructors
	///
	
	public ImageDrawer(SpriteBatch batchIn) { setBatch(batchIn); }

}
