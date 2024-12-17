package com.example.meditationapp.service;

import java.io.FileInputStream;
import java.util.Random;

import org.springframework.stereotype.Service;

import javazoom.jl.player.Player;

@Service
public class MusicPlayer {
	private Player player;
    private Thread musicThread; // 音楽再生用のスレッド
   

    public void playRandomMusic() {
        try {
    	Random random = new Random();
    	
    	String[] musicFiles = {
    	        "src/main/resources/static/music/between-sky-and-water-154383.mp3",
    	        "src/main/resources/static/music/full-moon-deep-relaxation-meditation-yoga-zen-positive-sleep-music-140639.mp3",
    	        "src/main/resources/static/music/inner-peace-meditation-106798.mp3",
    	        "src/main/resources/static/music/perfect-beauty-191271.mp3",
    	        "src/main/resources/static/music/reiki-meditation-patrizio-yoga-olistik-sound-project-2022-14295.mp3"
    	    };
    	String selectedMusic = musicFiles[random.nextInt(musicFiles.length)];

     // 音楽再生を別スレッドで実行
        musicThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(selectedMusic)){
                player = new Player(fis);
                System.out.println("音楽再生: " + selectedMusic);
                player.play();
            } catch (Exception e) {
            	System.err.println("音楽再生中にエラーが発生しました: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        musicThread.setDaemon(true); // アプリケーション終了時にスレッドを強制停止
        musicThread.start();

        } catch (Exception e) {
        	System.err.println("音楽ファイルの読み込み中にエラー: " + e.getMessage());
        	e.printStackTrace();
        }
    }
    // 音楽を停止する
    public void stopMusic() {
        if (player != null) {
        	try {
            player.close();
            System.out.println("音楽停止");
        }catch (Exception e){
            System.err.println("音楽停止エラー: " + e.getMessage());
        }
       
    }
}
}

