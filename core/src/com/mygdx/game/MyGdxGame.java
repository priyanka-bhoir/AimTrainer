package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture targetTexture;
	private float x;
	private float y;
	private Rectangle targetRect;

	private Random rng;

	private Timer gameTimer;


	private int secondsLeft;
	private final int seconds = 30; //Set this to change how long each aim training session is.
	private boolean gameOver = false;

	// add scroing
	private int score;

	private BitmapFont font;




	@Override
	public void create () {
		batch = new SpriteBatch();
		rng = new Random();

		targetTexture = new Texture("target.png");

		targetRect = new Rectangle((Gdx.graphics.getWidth() / 2) - targetTexture.getWidth() * 2, (Gdx.graphics.getHeight() / 2) - targetTexture.getHeight() * 2, targetTexture.getWidth() * 2, targetTexture.getHeight() * 2);

		gameTimer = new Timer();

		startGame();
		startTimer();

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5);


	}

	private void startTimer()
	{
		secondsLeft = seconds;
		gameTimer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				if(!gameOver)
					secondsLeft--;
			}
		}, 0, 1000);
	}

	@Override
	public void render () {

//		if(secondsLeft <= 0)
//		{
//			stopGame();
//		}

		// clearing the background
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(targetTexture, targetRect.x, targetRect.y, targetRect.width, targetRect.height);
		batch.end();

		// to make it move
		if(Gdx.input.justTouched())
		{
			//TODO
			Vector2 touchPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
			Rectangle touchedRect = new Rectangle(touchPosition.x, touchPosition.y, 1, 1);
			if(Intersector.overlaps(touchedRect, targetRect))
			{
				changeTargetPosition();
				score++;
			}
		}
		if(secondsLeft <= 0)
		{
			gameOver = true;
		}

	}
	private void startGame()
	{
		score = 0;
		gameOver = false;
		secondsLeft = seconds;
		targetRect = new Rectangle((Gdx.graphics.getWidth() / 2) - targetTexture.getWidth() * 2,
				(Gdx.graphics.getHeight() / 2) - targetTexture.getHeight() * 2,
				targetTexture.getWidth() * 2, targetTexture.getHeight() * 2);
	}

	private void changeTargetPosition()
	{
		targetRect.setPosition(rng.nextInt(Gdx.graphics.getWidth() - (int) targetRect.width / 2) +
				targetRect.height, rng.nextInt(Gdx.graphics.getHeight() - (int) targetRect.height / 2) +
				targetRect.height);
	}
	@Override
	public void dispose () {
		batch.dispose();

	}
}
