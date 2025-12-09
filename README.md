# Rhythm Game 

**Team Members:**
-Rigo Tesoro
-Jerry Miao
 
## Project description:
- The game is a simple rhythm game where notes fall down the four lanes with the song and user will need to hit D/F/J/K at the right time to score points, and a missed note or a misclick will result losing points.

## How To Run
- Java 17 or 21 (required by Kilt Graphics)
- Course graphics library on the classpath (`edu.macalester.graphics`)
- The `audio` and `audioplayer` packages from the Audio Synth homework

**Main Class**
- RhythmGame.java

**References**
- Game concept based on Guitar Hero and many others
- Coding format from Audio Synth Homework
- Song created by Rigo Tesoro

**Expected project layout**

- `RhythmGame.java` – sets up the window, background, HUD, input, and links audio time to visuals
- `LevelScroller.java` – turns song notes into falling rectangles in 4 lanes and handles removing them when hit
- `InputHandler.java` / `Input.java` – polls keyboard input (D, F, J, K) and tracks “just pressed” events
- `audioplayer/Note.java`, `audioplayer/Song.java`, `audioplayer/SongReader.java` – represent and load the song
- `audio/` – waveform classes (e.g., `audio.D`, `audio.F`, `audio.J`, `audio.K`)
- `resources/song.csv` (or similar) – note data for the level
- `resources/background.png` – background image for the playfield
- `resources/End.png` – Ending screen for player

**To play the game**
1. Make sure the resources are on the classpath. (Song and the background and End pngs)
2. Run 'RhythmGame.java'.
3. When the song starts, there will be note falling down and user should press D/F/J/K correspond to the path, when notes reach the hitting timing.

**Known issues**
- The game only supports one song at a time, only one song for our game is avaliable.
- Controls of the keyboard can not be modified.
- Timing window is fixed and stict to the timing player hits.
- There is no pause menu, start menu, or a restart button. Restart requires closing the program.

## Societal impact:

**Accessibility**
- The game has visual and auditory focus. A blind player or a plaer with low vision will have difficulty, as the song is their only reference to when to click, and there is no direct feedback for whether any click succeeded. Also player with hearing issues will lead to they can only rely on their visual to hit the square as it drop. Either of the above circumstances will increase the difficulty for a player to play the game.
- The game expects a good hand eye coordinations. Player with limited mobility in their hands will find the game very difficult to play.
- There is no options for colorbind-friendly palettes, or text size adjustments, or difficulty settings. Colorblindness is probably not an issue, but many things including (e.g.) astigmatism could be.

**Potential Harms**
- There is no harmful content in the game, and there are no vulnerable groups being targeted. The risk of misuse of the game is low.

**Future Improvements**
- Add difficulty options to accomodate player who have no experience with similar rhythm game before.
- Add more visible timing feedbacks, such as when player hit on the corresponding notes there is text feedback of "perfect, good, miss."
- Add more levels to the game.