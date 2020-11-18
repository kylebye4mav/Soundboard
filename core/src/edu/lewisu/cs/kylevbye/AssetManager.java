package edu.lewisu.cs.kylevbye;

import java.io.File;
import java.util.ArrayList;

import edu.lewisu.cs.kylevbye.util.io.TextFileReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;

public class AssetManager implements Disposable {
	
	///
	///	Fields
	///
	
	private ArrayList<edu.lewisu.cs.kylevbye.Drawable> imageRenderQueue; 
	private ArrayList<Image> images;
	private ArrayList<Sound> sounds;
	private ArrayList<Playable> soundQueue;
	private ArrayList<Clickable> clickables;
	
	///
	///	Getters
	///
	
	public ArrayList<edu.lewisu.cs.kylevbye.Drawable> getImageRenderQueue() { return imageRenderQueue; }
	public ArrayList<Image> getImages() { return images; }
	public ArrayList<Sound> getSounds() { return sounds; }
	public ArrayList<Playable> getSoundQueue() { return soundQueue; }
	public ArrayList<Clickable> getClickables() { return clickables; }
	
	///
	///	Setters
	///
	
	///
	///	Functions
	///
	
	public void addToImageQueue(edu.lewisu.cs.kylevbye.Drawable image) { imageRenderQueue.add(image); }
	
	public boolean loadAssets(String assetConfigFileNameIn) {
		
		String assetFileName = (new File(System.getProperty("user.dir"))).getParent()+ "\\core\\assets\\" + assetConfigFileNameIn;
		TextFileReader fileReader = new TextFileReader(assetFileName);
		if (!fileReader.readFile()) { 
			
			System.err.format("%s cannot be read.", assetConfigFileNameIn);
			
			//	Operation Unsuccessful
			return false;
			
		}
		
		for (String assetLine : fileReader.getData()) {
			
			if (assetLine.contains("#") || assetLine.trim().length() == 0) continue;
			
			String[] assetLineParts = assetLine.split("\t");
			
			String fileName = assetLineParts[0];
			String assetType = assetLineParts[1];
			
			//	If Image
			if (assetType.trim().toLowerCase().equals("i")) {
				
				Image newImage = loadImage("images/" + fileName);
				
				float x, y, scaleX, scaleY;
				x = Float.parseFloat(assetLineParts[2]);
				y = Float.parseFloat(assetLineParts[3]);
				scaleX = Float.parseFloat(assetLineParts[4]);
				scaleY = Float.parseFloat(assetLineParts[5]);
				newImage.setCoordinates(x, y);
				newImage.setScaleX(scaleX);
				newImage.setScaleY(scaleY);
				
			}
			//	If Sound
			else if (assetType.trim().toLowerCase().equals("s")) {
				
				loadSound("sounds/" + fileName);
				
			}
			
		}
		
		//	Operation Successful
		return true;
		
	}
	
	public Image loadImage(String imageFileNameIn) {
		
		Image newImage = new Image(new TextureRegion(new Texture(imageFileNameIn)), 0f, 0f);
		images.add(newImage);
		return newImage;
		
	}
	
	public Sound loadSound(String soundFileNameIn) {
		
		Sound newSound = Gdx.audio.newSound(Gdx.files.internal(soundFileNameIn));
		sounds.add(newSound);
		
		return newSound;
		
	}
	
	///
	///	Constructors
	///
	
	public AssetManager() {
		imageRenderQueue = new ArrayList<edu.lewisu.cs.kylevbye.Drawable>();
		images = new ArrayList<Image>();
		sounds = new ArrayList<Sound>();
		soundQueue = new ArrayList<Playable>();
		clickables = new ArrayList<Clickable>();
	}
	
	///
	///	Desctructor
	///
	
	public void dispose() {
		
		imageRenderQueue.clear();
		soundQueue.clear();
		clickables.clear();
		for (Image i : images) i.dispose();
		images.clear();
		
		imageRenderQueue = null;
		soundQueue = null;
		clickables = null;
		images = null;
		
	}

}
