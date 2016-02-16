package boulier.remi.peak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class PeakMain extends ApplicationAdapter implements PeakStage.OnDragListener {

    private Stage stage;
    private Table table;
    private Label wordLabel;

    @Override
    public void create() {
        int actorSize = Gdx.graphics.getWidth() / 4;

        final JsonContent jsonContent = getJSONContent();

        DictionaryGenerator generator = new DictionaryGenerator();
        char[] tempLetters = {'l', 'e', 'y', 'c', 'i', 'e', 'p', 'i', 'r', 't', 's', 'n', 'n', 'a', 'f', 'r'};
        generator.generateDictionary(tempLetters, 4, 4);


        stage = new PeakStage(this, jsonContent.dictionary);
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
            LetterSquare actor = new LetterSquare(jsonContent.letters.get(i), textures);
            table.add(actor).expandX().height(actorSize).fillX().pad(8);
            if ((i + 1) % 4 == 0) {
                table.row();
            }
        }
    }

    private JsonContent getJSONContent() {
        boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();
        Gdx.app.log("PeakMain/getJSONContent", "local storage available: " + isLocAvailable);

        JsonContent jsonContent = new JsonContent();
        if (isLocAvailable) {

            FileHandle file = Gdx.files.internal("sample.json");
            JsonValue content = new JsonReader().parse(file.readString());
            JsonValue rootJson = content.get("data");

            JsonValue gridJson = rootJson.get("grid");
            for (JsonValue gridItem : gridJson) {
                jsonContent.letters.add(gridItem.asString().toUpperCase());
            }
            JsonValue wordsJson = rootJson.get("words");
            for (JsonValue wordJson : wordsJson) {
                jsonContent.dictionary.add(wordJson.asString().toUpperCase());
            }
        }

        return jsonContent;
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
