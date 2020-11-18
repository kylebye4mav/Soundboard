package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

/**
 * This class contains all the components of
 * a 2D image regarding texture region, coordinates,
 * origin points, scaling, and rotation angle.
 * 
 * For use with LibGDX API.
 * 
 * @author	Kyle V Bye
 * @see com.badlogic.gdx.graphics.TextureRegion
 * @see com.badlogic.gdx.scenes.scene2d.Actor
 * @see com.badlogic.gdx.utils.Disposable;
 * @see java.util.ArrayList
 * @see java.util.LinkedHashMap;
 */
public class Image implements Disposable, Drawable {
	
	///
	///	Fields
	///
	
	private TextureRegion textureRegion;
	private float x, y;
	private float width, height;
	private float orgX, orgY;
	private float scaleX, scaleY;
	private float rotationAngle;
	
	///
	///	Getters
	///
	
	public TextureRegion getTextureRegion() { return textureRegion; }
	public float getX() { return x; }
	public float getY() { return y; }
	public float getWidth() { return this.width; }
	public float getHeight() { return this.height; }
	public float getOrgX() { return orgX; }
	public float getOrgY() { return orgY; }
	public float getScaleX() { return scaleX; }
	public float getScaleY() { return scaleY; }
	public float getRotationAngle() { return rotationAngle; }
	
	///
	///	Setters
	///
	
	public void setTextureRegion(TextureRegion textureRegionIn) {
		
		textureRegion = textureRegionIn;
		
		//	Change width and height accordingly
		//	based on provided texture region if not null.
		if (textureRegion != null) {
			this.width = (float)textureRegionIn.getTexture().getWidth();
			this.height = (float)textureRegionIn.getTexture().getHeight();
		}
		else { width = 0; height = 0; }
		
	}
	public void setX(float xIn) { x = xIn; }
	public void setY(float yIn) { y = yIn; }
	public void setOrgX(float orgXIn) { orgX = orgXIn; }
	public void setOrgY(float orgYIn) { orgY = orgYIn; }
	public void setScaleX(float scaleXIn) { scaleX = scaleXIn; }
	public void setScaleY(float scaleYIn) { scaleY = scaleYIn; }
	public void setRotationAngle(float rotationAngleIn) { rotationAngle = rotationAngleIn; }
	
	///
	///	Functions
	///
	
	/**
	 * Changes this Image's coordinates based on the given
	 * parameters.
	 * 
	 * @param	xIn	x coordinate
	 * @param	yIn	y coordinate
	 */
	public void setCoordinates(float xIn, float yIn) {
		setX(xIn); setY(yIn);
	}
	
	/**
	 * Adds the coordinates given to this instance's coordinates.
	 * 
	 * @param	dxIn	units to translate in the x direction.
	 * @param	dyIn	units to translate in the y direction.
	 */
	public void translate(float dxIn, float dyIn) {
		translateX(dxIn); translateY(dyIn);
	}
	
	/**
	 * Adds the given units to the x coordinate.
	 * 
	 * @param	dxIn	units to translate in the x direction.
	 */
	public void translateX(float dxIn) { x += dxIn; }
	
	/**
	 * Adds the given units to the y coordinate.
	 * 
	 * @param	dyIn	units to translate in the y direction.
	 */
	public void translateY(float dyIn) { y += dyIn; }
	
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(textureRegion, x, y, orgX, orgY, width, height, scaleX, scaleY, rotationAngle);
	}
	
	public void rotate(float rotationAngleIn) { rotationAngle += rotationAngleIn; }
	
	///
	///	toString
	///
	@Override
	public String toString() {
		return String.format("Image[Text, X:%.2f, Y:%.2f, W:%.2f, H:%.2f", x, y, width, height);
	}
	
	///
	///	Constructors
	///
	
	/**
	 * Default Constructor
	 * 
	 * Initializes this Image with 1 by 1 scaling, 0
	 * for all coordinates and origin points, 0 for rotation 
	 * angle and a nullified texture region.
	 * 
	 * @param	textureRegionIn	texture region of the image
	 * @param	xIn	x coordinate
	 * @param	yIn	y coordinate
	 */
	public Image() { this(null, 0f, 0f, 0f, 0f, 0f, 1f, 1f); }
	
	/**
	 * Initializes this Image based on the provided parameters.
	 * Initialized to 1 by 1 scaling, 0 for origin coordinates, and
	 * 0 for rotation angle.
	 * 
	 * @param	textureRegionIn	texture region of the image
	 * @param	xIn	x coordinate
	 * @param	yIn	y coordinate
	 */
	public Image(TextureRegion textureRegionIn, float xIn, float yIn) {
		this(textureRegionIn, xIn, yIn, 0f, 0f, 0f, 1f, 1f);
	}
	
	/**
	 * Initializes this Image based on the provided parameters.
	 * Initialized to 1 by 1 scaling.
	 * 
	 * @param	textureRegionIn	texture region of the image
	 * @param	xIn	x coordinate
	 * @param	yIn	y coordinate
	 * @param	orgXIn	x origin point
	 * @param	orgYIn	y origin point
	 * @param	rotationAngleIn	rotation angle of the image
	 */
	public Image(TextureRegion textureRegionIn, float xIn, float yIn, float orgXIn, float orgYIn, float rotationAngleIn) {
		this(textureRegionIn, xIn, yIn, orgXIn, orgYIn, rotationAngleIn, 1f, 1f);
	}
	
	/**
	 * Initializes this Image based on the provided parameters.
	 * 
	 * @param	textureRegionIn	texture region of the image
	 * @param	xIn	x coordinate
	 * @param	yIn	y coordinate
	 * @param	orgXIn	x origin point
	 * @param	orgYIn	y origin point
	 * @param	rotationAngleIn	rotation angle of the image
	 * @param	scaleXIn	horizontal scale
	 * @param	scaleYIn	vertical scale
	 */
	public Image(TextureRegion textureRegionIn, float xIn, float yIn, float orgXIn, float orgYIn, float rotationAngleIn, float scaleXIn, float scaleYIn) {
		
		setTextureRegion(textureRegionIn);
		setX(xIn); setY(yIn);
		setOrgX(orgXIn); setOrgY(orgYIn);
		setRotationAngle(rotationAngleIn);
		setScaleX(scaleXIn); setScaleY(scaleYIn);
		
	}
	
	///
	///	Destructor
	///
	public void dispose() {
		
		//	Dispose and clear all objects.
		textureRegion.getTexture().dispose();
		
		//	Nullify all objects
		textureRegion = null;
		
	}

}
