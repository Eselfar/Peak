package boulier.remi.peak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.Random;

public class PeakMain extends ApplicationAdapter implements PeakStage.OnDragListener {

    private Stage stage;
    private Table table;
    private Label wordLabel;

    @Override
    public void create() {
        int actorSize = Gdx.graphics.getWidth() / 4;

        Random rand = new Random();
        char[] letters = new char[4 * 4];
        for (int i=0; i< 16; i++){
            letters[i] = (char) (rand.nextInt(26) + 'a');
        }

        DictionaryGenerator generator = new DictionaryGenerator();
        ArrayList<String> dictionary = generator.generateDictionary(letters, 4, 4);

        stage = new PeakStage(this, dictionary);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.bottom();
//        table.setDebug(true); // Uncomment the line to debug the table.

        stage.addActor(table);

        LetterSquareTextures textures = new LetterSquareTextures();

        /* Populate the table */

        wordLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        wordLabel.setFontScale(3f);

        table.add(wordLabel)
                .expandX()
                .padBottom(50).padLeft(10).padRight(10)
                .center().colspan(4);
        table.row();

        for (int i = 0; i < 4 * 4; i++) {
            LetterSquare actor = new LetterSquare(String.valueOf(letters[i]).toUpperCase(), textures);
            table.add(actor).expandX().height(actorSize).fillX().pad(8);
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
//        Gdx.app.log("PeakMain/onSelect", "word: " + word);
        wordLabel.setText(word);
    }

    @Override
    public void onComplete(String word, boolean isWordValid) {
//        Gdx.app.log("PeakMain/onComplete", "word: " + word + " is valid: " + isWordValid);
        if (!isWordValid) {
            wordLabel.setText("");
        }
    }
}
