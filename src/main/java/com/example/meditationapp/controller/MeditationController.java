package com.example.meditationapp.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.meditationapp.model.MeditationSession;
import com.example.meditationapp.service.MeditationService;
import com.example.meditationapp.service.MusicPlayer;

@Controller
public class MeditationController {

    @Autowired
    private MeditationService service;

    @Autowired
    private MusicPlayer musicPlayer; // MusicPlayerを依存性注入で管理
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    // トップページ (index.html) を表示
    @GetMapping("/")
    public String showForm(Model model) {
    	System.out.println("showFormメソッドが呼び出されました");
        return "index";
    }

    // 瞑想を開始する処理
    @PostMapping("/start")
    public String startMeditation(@RequestParam("duration") int duration, Model model) {
        // MeditationSessionの作成と保存
        MeditationSession session = new MeditationSession();
        
        session.setDurationInMinutes(duration);
        service.saveSession(session);
        System.out.println("瞑想セッション開始: " + duration + " 分");
        // 音楽を再生する
        
            musicPlayer.playRandomMusic();
         // 指定時間後に音楽を停止
            scheduler.schedule(() -> {
                musicPlayer.stopMusic();
                System.out.println("瞑想時間が終了しました。音楽を停止しました。");
            }, duration, TimeUnit.MINUTES);
         
        
        
        // モデルにセッション情報を追加し、meditation.htmlを返す
        model.addAttribute("obj", session);
        return "meditation";
    }
    
 // 音楽を停止し、アプリケーションを終了する処理
    @PostMapping("/stop")
    public String stopMeditation(Model model) {
        // 音楽を停止
        musicPlayer.stopMusic();

        // アプリケーションを終了
        System.out.println("アプリケーションを終了します...");
        System.exit(0); // アプリケーションを強制終了

        return "index"; // 終了後、トップページを表示
    }
}
