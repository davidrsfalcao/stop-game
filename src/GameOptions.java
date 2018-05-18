public class GameOptions {
    private static GameOptions ourInstance = new GameOptions();

    public static GameOptions getInstance() {
        return ourInstance;
    }

    private GameOptions() {
    }
}
