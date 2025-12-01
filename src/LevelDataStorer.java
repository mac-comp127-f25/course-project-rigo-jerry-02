public class LevelDataStorer {
    private String backgroundPath;
    private String songPath;

    public LevelDataStorer(String backgroundPath, String songPath) {
        this.backgroundPath = backgroundPath;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public String getSongPath() {
        return songPath;
    }
}
