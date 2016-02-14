package boulier.remi.peak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PeakMain extends ApplicationAdapter implements PeakStage.OnDragListener {
//    SpriteBatch batch;

    private int FRAME_COLS = 5;
    private int FRAME_ROWS = 3;

    private Stage stage;

    @Override
    public void create() {
//        batch = new SpriteBatch();

//        Table table = new Table();

        stage = new PeakStage(this);

        int actorSize = Gdx.graphics.getWidth() / 4;

        int xPos = 0;

        LetterSquareTextures textures = new LetterSquareTextures();

        String[] letters = {"A", "B", "C", "D"};

        for (int i = 0; i < 4; i++) {
            LetterSquare actor = new LetterSquare(letters[i], textures);
            actor.setHeight(actorSize);
            actor.setWidth(actorSize);
            actor.setPosition(xPos, 0);

            stage.addActor(actor);
            xPos += actorSize;
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

//        float squareWidth = camera.viewportWidth / squaresOnWidth;
//        float squareHeight = camera.viewportHeight / squaresOnHeight;
//        square.setWidth(squareWidth);
//        square.setHeight(squareHeight);

//        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void onSelect(String word) {
        // TODO: Display the word
        Gdx.app.log("PeakMain/onSelect", "word: " + word);
    }

    @Override
    public void onComplete(String word, boolean isWordValid) {
        // TODO: Display the word
        Gdx.app.log("PeakMain/onComplete", "word: " + word + " is valid: " + isWordValid);
    }
}
