package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Soundboard extends ApplicationAdapter {
	
	int WIDTH, HEIGHT;
	
	static float volumeVal; 
	
	InputHandler inputHandler;
	OrthographicCamera cam;
	SpriteBatch batch;
	ImageDrawer imageDrawer;
	AssetManager assetManager;
	
	Image backgroundImage;
	Image movingCircle;
	Image stillCircle;
	
	SoundScreenObject introCircle;
	SoundScreenObject note;
	
	Music introMusic;
	
	ArrayList<SoundScreenObject> soundEffects;
	ArrayList<SoundLabel> soundLabels;
	ArrayList<ScreenObject> rotating;
	ScreenObject volumeIcon;
	
	boolean introDone;
	int introProgress;
	
	SoundLabel volumeLabel;
	
	@Override
	public void create () {
		
		introDone = true;
		introProgress = 0;
		
		//	720p
		Gdx.graphics.setWindowedMode(1280, 720);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		//	Input Handler
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);
		
		//	Asset Preparation
		batch = new SpriteBatch();
		imageDrawer = new ImageDrawer(batch);
		assetManager = new AssetManager();
		assetManager.loadAssets("assetsconfig.cfg");
		
		//	Intro Prep
		prepIntro();
		
		//	Background Prep
		backgroundImage = assetManager.getImages().get(0);
		
		//	Pairing Assets with their sounds
		soundEffects = new ArrayList<SoundScreenObject>();
		soundLabels = new ArrayList<SoundLabel>();
		rotating = new ArrayList<ScreenObject>();
		assembleSoundEffects();
		
		volumeVal = 1.0f;

		//	Cam Preparation
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.translate(WIDTH/2, HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
	}

	@Override
	public void render () {
		
		processInput();
		for (ScreenObject so : rotating) processRotate(so);
		checkRotating();
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//	Update vol label
		volumeLabel.getLabel().setText(Float.toString(volumeVal));
		
		batch.begin();
		
		if (!introDone) {
			
			///	Intro Script
			
			++introProgress;
			if (introProgress > 240) {
				movingCircle.translate(-3, 0);
			}
			else movingCircle.translate(6, 0);
			
			if (introProgress > 40 && introProgress < 60) {
				stillCircle.setX(150);
				stillCircle.draw(batch, 0);
			}
			else if (introProgress > 80 && introProgress < 100) {
				stillCircle.setX(400);
				stillCircle.draw(batch, 0);
			}
			else if (introProgress > 120 && introProgress < 140) {
				stillCircle.setX(630);
				stillCircle.draw(batch, 0);
			}
			else if (introProgress > 160 && introProgress < 180) {
				stillCircle.setX(840);
				stillCircle.draw(batch, 0);
			}
			
			if (introProgress > 500 && introProgress < 600) {
				stillCircle.draw(batch, 0);
				cam.zoom -= .025f;
			}
			else if (introProgress > 650 && introProgress < 750) {
				cam.zoom += .025f;
				backgroundImage.draw(batch, 0);
			}
			else if (introProgress > 750) {
				cam.zoom = 1f;
				introDone = true;
			}
			else movingCircle.draw(batch, 0);
			
		}
		else {
			
			//	Main Soundboard render
			if (introCircle.getEvents().isEmpty()) {
				introCircle.getImage().setCoordinates(0, 00);
				introCircle.addEvent(EventTypes.LEFTMOUSE, new Event() {
					@Override
					public void run() { 
						prepIntro();
						introCircle.clearAllEvents();
					}
				});
			}
			assetManager.addToImageQueue(backgroundImage);
			for (SoundLabel sl : soundLabels) assetManager.addToImageQueue((Drawable)sl);
			for (SoundScreenObject seso : soundEffects) assetManager.addToImageQueue((Drawable) seso);
			renderImageQueue(assetManager.getImageRenderQueue());
			playSoundQueue(assetManager.getSoundQueue());
			
		}
		
		batch.end();
		PlayerInput.resetOneTimeEvents();
		
	}

	@Override
	public void dispose () {
		
		batch.dispose();
		assetManager.dispose();
		
	}
	
	///
	///	Helper Methods
	///
	
	private void processRotate(ScreenObject so) { so.getImage().rotate(50f); }
	
	private void checkRotating() {
		
		ArrayList<ScreenObject> toRemove = new ArrayList<ScreenObject>();
		for (ScreenObject so : rotating) {
			if (so.getImage().getRotationAngle() > 360f) toRemove.add(so);
		}
		for (ScreenObject soToRemove : toRemove) {
			soToRemove.getImage().setRotationAngle(0);
			rotating.remove(soToRemove);
		}
	}
	
	private void prepIntro() {
		movingCircle = assetManager.getImages().get(1);
		movingCircle.setCoordinates(-100, HEIGHT/2 - movingCircle.getHeight()*movingCircle.getScaleY()/2);
		stillCircle = assetManager.getImages().get(2);
		stillCircle.setY(HEIGHT/2 - movingCircle.getHeight()*movingCircle.getScaleY()/2);
		introMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gunBarrelMusic.mp3"));
		introProgress = 0;
		introDone = false;
		introMusic.play();
	}
	
	private void processInput() {
		
		Vector3 mouseWorldCoordinates = cam.unproject(new Vector3(PlayerInput.mouseX, PlayerInput.mouseY, 0));
		
		if (PlayerInput.leftMouseClicked) {
			processClickables(mouseWorldCoordinates.x, mouseWorldCoordinates.y, Buttons.LEFT);
			note.getImage().setCoordinates(mouseWorldCoordinates.x, mouseWorldCoordinates.y);
		}
		if (PlayerInput.middleMouseClicked) processClickables(mouseWorldCoordinates.x, mouseWorldCoordinates.y, Buttons.MIDDLE);
		if (PlayerInput.rightMouseClicked) processClickables(mouseWorldCoordinates.x, mouseWorldCoordinates.y, Buttons.RIGHT);
	}
	
	private void processClickables(float mouseXIn, float mouseYIn, int buttonIn) {
		
		ArrayList<Clickable> clickables = assetManager.getClickables();
		
		for (Clickable c : clickables) if (c.wasClicked(mouseXIn, mouseYIn)) { c.onClicked(buttonIn); }
		
	}
	
	
	/*
	 * Draw all images until the image render queue is empty.
	 */
	private void renderImageQueue(ArrayList<edu.lewisu.cs.kylevbye.Drawable> imageRenderQueue) {
		
		while (!imageRenderQueue.isEmpty()) {
			Drawable i = imageRenderQueue.remove(0);
			imageDrawer.draw(i);
		}
		
	}
	
	/*
	 * Play all sounds until the sound queue is empty.
	 */
	private void playSoundQueue(ArrayList<Playable> soundQueue) {
		
		while (!soundQueue.isEmpty()) {
			Playable sound = soundQueue.remove(0);
			sound.play(volumeVal);
		}
		
	}
	
	private void assembleSoundEffects() {
		
		float belowImageSpace = 20f;
		BitmapFont font = new BitmapFont();
		LabelStyle style = new LabelStyle();
		style.font = font;
		
		///	sound1
		SoundScreenObject sound1 = new SoundScreenObject(assetManager.getImages().get(3), assetManager.getSounds().get(0));
		SoundLabel sound1Label = new SoundLabel(
				sound1.getImage().getX(), sound1.getImage().getY()-belowImageSpace, 
				1f, 1f, assetManager.getSounds().get(0), "Alarm", style
				);
		soundLabels.add(sound1Label);
		assetManager.getClickables().add(sound1Label);
		soundEffects.add(sound1);
		sound1Label.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				Playable sound = soundEffects.get(0);
				assetManager.getSoundQueue().add(sound); 
				rotating.add(soundEffects.get(0));
			}
		});
		
		//	sound2
		SoundScreenObject sound2 = new SoundScreenObject(assetManager.getImages().get(4), assetManager.getSounds().get(1));
		SoundLabel sound2Label = new SoundLabel(
				sound2.getImage().getX(), sound2.getImage().getY()-belowImageSpace, 
				1f, 1f, assetManager.getSounds().get(1), "Gun", style
				);
		soundEffects.add(sound2);
		soundLabels.add(sound2Label);
		assetManager.getClickables().add(sound2Label);
		sound2Label.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				Playable sound = soundEffects.get(1);
				assetManager.getSoundQueue().add(sound); 
				rotating.add(soundEffects.get(1));
			}
		});
		
		//	sound3
		SoundScreenObject sound3 = new SoundScreenObject(assetManager.getImages().get(5), assetManager.getSounds().get(2));
		SoundLabel sound3Label = new SoundLabel(
				sound3.getImage().getX(), sound3.getImage().getY()-belowImageSpace, 
				1f, 1f, assetManager.getSounds().get(2), "Bomb", style
				);
		soundEffects.add(sound3);
		soundLabels.add(sound3Label);
		assetManager.getClickables().add(sound3Label);
		sound3Label.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				Playable sound = soundEffects.get(2);
				assetManager.getSoundQueue().add(sound); 
				rotating.add(soundEffects.get(2));
			}
		});
		
		//	sound4
		SoundScreenObject sound4 = new SoundScreenObject(assetManager.getImages().get(6), assetManager.getSounds().get(3));
		SoundLabel sound4Label = new SoundLabel(
				sound4.getImage().getX(), sound4.getImage().getY()-belowImageSpace, 
				1f, 1f, assetManager.getSounds().get(3), "2x Kill", style
				);
		soundEffects.add(sound4);
		soundLabels.add(sound4Label);
		assetManager.getClickables().add(sound4Label);
		sound4Label.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				Playable sound = soundEffects.get(3);
				assetManager.getSoundQueue().add(sound); 
				rotating.add(soundEffects.get(3));
			}
		});
		
		//	Sound 5
		SoundScreenObject sound5 = new SoundScreenObject(assetManager.getImages().get(7), assetManager.getSounds().get(4));
		SoundLabel sound5Label = new SoundLabel(
				sound5.getImage().getX(), sound5.getImage().getY()-belowImageSpace, 
				1f, 1f, assetManager.getSounds().get(4), "Spree", style
				);
		soundEffects.add(sound5);
		soundLabels.add(sound5Label);
		assetManager.getClickables().add(sound5Label);
		sound5Label.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				Playable sound = soundEffects.get(4);
				assetManager.getSoundQueue().add(sound); 
				rotating.add(soundEffects.get(4));
			}
		});
		
		//	Sound 6
		SoundScreenObject sound6 = new SoundScreenObject(assetManager.getImages().get(8), assetManager.getSounds().get(5));
		SoundLabel sound6Label = new SoundLabel(
				sound6.getImage().getX(), sound6.getImage().getY()-belowImageSpace, 
				1f, 1f, assetManager.getSounds().get(5), "Spy", style
				);
		soundEffects.add(sound6);
		soundLabels.add(sound6Label);
		assetManager.getClickables().add(sound6Label);
		sound6Label.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				Playable sound = soundEffects.get(5);
				assetManager.getSoundQueue().add(sound); 
				rotating.add(soundEffects.get(5));
			}
		});
		
		//	Sound 7
		SoundScreenObject sound7 = new SoundScreenObject(assetManager.getImages().get(9), assetManager.getSounds().get(6));
		SoundLabel sound7Label = new SoundLabel(
				sound7.getImage().getX(), sound7.getImage().getY()-belowImageSpace, 
				1f, 1f, assetManager.getSounds().get(6), "Wolf", style
				);
		soundEffects.add(sound7);
		soundLabels.add(sound7Label);
		assetManager.getClickables().add(sound7Label);
		sound7Label.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				Playable sound = soundEffects.get(6);
				assetManager.getSoundQueue().add(sound); 
				rotating.add(soundEffects.get(6));
			}
		});
		
		//	Sound 8
		SoundScreenObject sound8 = new SoundScreenObject(assetManager.getImages().get(10), assetManager.getSounds().get(7));
		SoundLabel sound8Label = new SoundLabel(
				sound8.getImage().getX(), sound8.getImage().getY()-belowImageSpace, 
				1f, 1f, assetManager.getSounds().get(7), "Water", style
				);
		soundEffects.add(sound8);
		soundLabels.add(sound8Label);
		assetManager.getClickables().add(sound8Label);
		sound8Label.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				Playable sound = soundEffects.get(7);
				assetManager.getSoundQueue().add(sound); 
				rotating.add(soundEffects.get(7));
			}
		});
		
		//	Intro Button
		introCircle = new SoundScreenObject(movingCircle, null);
		soundEffects.add(introCircle);
		assetManager.getClickables().add(introCircle);
		
		//	Volume
		SoundScreenObject volume = new SoundScreenObject(assetManager.getImages().get(11), assetManager.getSounds().get(7));
		volumeLabel = new SoundLabel(volume.getImage().getX(), volume.getImage().getY()-belowImageSpace,
				1f, 1f, null, "VolumeVal", style
				);
		soundEffects.add(volume);
		soundLabels.add(volumeLabel);
		assetManager.getClickables().add(volume);
		volume.addEvent(EventTypes.LEFTMOUSE, new Event() {
			@Override
			public void run() { 
				if (volumeVal < 1.0f) volumeVal += .1f;
				if (volumeVal > 1f) volumeVal = 1.f;
			}
		});
		volume.addEvent(EventTypes.RIGHTMOUSE, new Event() {
			@Override
			public void run() { 
				if (volumeVal > 0.f) volumeVal -= .1f;
				if (volumeVal < 0) volumeVal = 0.f;
			}
		});
		
		//	Note
		note = new SoundScreenObject(assetManager.getImages().get(12), null);
		note.getImage().setCoordinates(-1000, -1000);
		note.getImage().setOrgX(-45);
		note.getImage().setOrgY(-30);
		soundEffects.add(note);
		
	}
	
	
}
