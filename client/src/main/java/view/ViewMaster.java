package view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.players.User;
import view.allmenu.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ViewMaster {
    public static MediaPlayer goodInRed;
    public static MediaPlayer daytona;
    public static MediaPlayer shadows;
    public static MediaPlayer[] songs;
    public static Scanner scanner = new Scanner(System.in);
    private static ViewMaster viewMaster;
    private static Menu currentMenu;
    private static User user;
    private static boolean isMuted = false;
    private final LoginView loginView;
    private final ShopView shopView;
    private final ScoreboardLabel scoreboardView;
    private final ProfileView profileView;
    private final MainView mainView;
    private final DeckView deckView;
    private final DuelView duelView;
    private GameView gameView;
    private ShowGraveyardView showGraveyardView;

    static {
        goodInRed = new MediaPlayer(new Media(ViewMaster.class
                .getResource("/gameMusic/goodInRed.mp3").toExternalForm()));
        shadows = new MediaPlayer(new Media(ViewMaster.class
                .getResource("/gameMusic/shadows.mp3").toExternalForm()));
        daytona = new MediaPlayer(new Media(ViewMaster.class
                .getResource("/gameMusic/daytona.mp3").toExternalForm()));
        songs = new MediaPlayer[]{goodInRed, shadows, daytona};
    }

    private ViewMaster() {
        loginView = new LoginView();
        shopView = new ShopView();
        scoreboardView = new ScoreboardLabel();
        profileView = new ProfileView();
        mainView = new MainView();
        deckView = new DeckView();
        duelView = new DuelView();
        currentMenu = Menu.LOGIN_MENU;
    }

    public static void playAudio(String place) {
        if (isMuted)
            return;
        URL audio = ViewMaster.class.getResource(place);
        AudioClip audioClip = new AudioClip(audio.toString());
        audioClip.play();
    }


    public static void startSong() {
        Collections.shuffle(Arrays.asList(songs));
        songs[0].play();
        Arrays.stream(songs).forEach(
                x -> {
                    x.setVolume(0.3);
                    x.setOnEndOfMedia(() -> {
                        Collections.shuffle(Arrays.asList(songs));
                        songs[0].play();
                    });
                }
        );
    }
    public static void btnSoundEffect(){
        playAudio("/soundEffects/clk1.wav");
    }
    public static void attackSoundEffect(){
        playAudio("/soundEffects/attack.wav");
    }
    public static void heartbeatSoundEffect(){
        playAudio("/soundEffects/heartBeat.wav");
    }
    public static void beginBattleSoundEffect(){
        playAudio("/soundEffects/letBattleBegin.wav");
    }
    public static void completeSoundEffect(){
        playAudio("/soundEffects/complete.wav");
    }
    public static void setUser(User user) {
        ViewMaster.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        ViewMaster.currentMenu = currentMenu;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public DeckView getDeckView() {
        return deckView;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setShowGraveyardMenu(ShowGraveyardView showGraveyardView) {
        this.showGraveyardView = showGraveyardView;
    }

    public static ViewMaster getViewMaster() {
        if (viewMaster == null)
            viewMaster = new ViewMaster();
        return viewMaster;
    }

    public void run() throws IOException {
        startSong();
        loginView.setLogin();
    }

}
