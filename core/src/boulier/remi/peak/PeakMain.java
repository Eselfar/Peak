package boulier.remi.peak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PeakMain extends ApplicationAdapter {
//    SpriteBatch batch;

    private int FRAME_COLS = 5;
    private int FRAME_ROWS = 3;

    private Stage stage;

    @Override
    public void create() {
//        batch = new SpriteBatch();

//        Table table = new Table();

        stage = new Stage() {
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                super.touchDragged(screenX, screenY, pointer);
                Gdx.app.log("PeakMain/touchDragged", "screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer);
                Vector2 stageCoord = screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor actor = hit(stageCoord.x, stageCoord.y, false);
                if (actor != null) {
                    Gdx.app.log("Actor", "Actor hits");
                }
                return false;
            }
        };

        int actorSize = Gdx.graphics.getWidth() / 4;

        int xPos = 0;

        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE); // add your 1 color here
        pixmap.fillRectangle(1, 1, 31, 31);

        Pixmap pixmap2 = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.RED); // add your 1 color here
        pixmap2.fillRectangle(1, 1, 31, 31);


        // the outcome is an texture with an blue left square and an red right square
        Texture t = new Texture(pixmap);
        TextureRegion reg1 = new TextureRegion(t, 0, 0, 32, 32);
        Texture t2 = new Texture(pixmap2);
        TextureRegion reg2 = new TextureRegion(t2, 0, 0, 32, 32);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.up = new TextureRegionDrawable(reg1);
        style.checked = new TextureRegionDrawable(reg2);

        for (int i = 0; i < 4; i++) {
            TextButton actor = new TextButton("A", style);
//            actor.setColor(colors[i]);
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
}
