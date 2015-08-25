package com.kingscastle.nuzi.towerdefence.framework.implementation;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.kingscastle.nuzi.towerdefence.framework.Audio;
import com.kingscastle.nuzi.towerdefence.framework.Music;

import java.io.IOException;

public class AndroidAudio implements Audio {
	private final AssetManager assets;
	private final SoundPool soundPool;

	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}


	@Override
	public Music createMusic(String filename)
	{
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			return new AndroidMusic(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + filename + "'");
		}
	}


	@Override
	public AndroidSound createSound(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			System.out.println("Couldn't load sound '" + filename + "'");
		}
		return null;
	}
}

