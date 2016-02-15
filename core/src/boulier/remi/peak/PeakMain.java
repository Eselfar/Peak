package boulier.remi.peak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Random;

public class PeakMain extends ApplicationAdapter implements PeakStage.OnDragListener {

    private Stage stage;
    private Table table;

    @Override
    public void create() {
        int actorSize = Gdx.graphics.getWidth() / 4;

        stage = new PeakStage(this);

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.bottom();
        table.setDebug(true);

        LetterSquareTextures textures = new LetterSquareTextures();


        Random rand = new Random();
        for (int i = 0; i < 4 * 4; i++) {
            char res = (char) (rand.nextInt(26) + 'A');

            LetterSquare actor = new LetterSquare(String.valueOf(res), textures);

            table.add(actor).expandX().height(actorSize).fillX().pad(5);

            if ((i + 1) % 4 == 0) {
                table.row();
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
