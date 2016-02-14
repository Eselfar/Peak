package boulier.remi.peak;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;


/**
 * Created by Remi BOULIER on 14/02/2016.
 * email: boulier.r+dev@gmail.com
 */
public class LetterSquare extends TextButton {

    private final LetterSquareTextures textures;
    private boolean isSelected = false;

    private final Pool<ChangeBGColorAction> pool;

    public LetterSquare(String text, final LetterSquareTextures textures) {
        super(text, new TextButtonStyle(null, null, null, new BitmapFont()));
        this.textures = textures;
        getStyle().up = textures.getNormalTRD();
        getLabel().setTouchable(Touchable.disabled); // Disabled the touchability on the Label.

        pool = new Pool<ChangeBGColorAction>() {
            @Override
            protected ChangeBGColorAction newObject() {
                return new ChangeBGColorAction();
            }
        };
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        getStyle().up = isSelected ? textures.getTouchDraggedTRD() : textures.getNormalTRD();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void onValidWordAction() {
        ChangeBGColorAction  action = pool.obtain();
        action.setPool(pool);
        action.setTexture(textures.getValidTRD());
        addAction(action);
    }

    public void onInValidWordAction() {
        ChangeBGColorAction action = pool.obtain();
        action.setPool(pool);
        action.setTexture(textures.getInvalidTRD());
        addAction(action);
    }


    private class ChangeBGColorAction extends Action {
        private final float duration;
        private float time;
        private boolean complete;
        private TextureRegionDrawable texture;
        private boolean begin;

        public ChangeBGColorAction() {
            duration = 0.25f;
            this.texture = textures.getNormalTRD(); // Set a default texture.
            restart();
        }

        @Override
        public boolean act(float delta) {
            if (complete) return true;
            if (begin) begin();

            time += delta;
            complete = time >= duration;
            if (complete) {
                getStyle().up = textures.getNormalTRD();
            }

            return complete;
        }

        private void begin() {
            getStyle().up = texture;
            begin = false;
        }

        @Override
        public void restart() {
            time = 0;
            complete = false;
            begin = true;
        }

        public void setTexture(TextureRegionDrawable texture) {
            this.texture = texture;
        }
    }
}
