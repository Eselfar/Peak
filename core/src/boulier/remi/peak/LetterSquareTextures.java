package boulier.remi.peak;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Remi BOULIER on 14/02/2016.
 * email: boulier.r+dev@gmail.com
 */
public class LetterSquareTextures {
    private final TextureRegionDrawable normalTRD;
    private final TextureRegionDrawable touchDraggedTRD;
    private final TextureRegionDrawable validTRD;
    private final TextureRegionDrawable invalidTRD;

    public LetterSquareTextures() {
        Pixmap pixmap = new Pixmap(4, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fillRectangle(0, 0, 1, 1);

        pixmap.setColor(Color.BLUE);
        pixmap.fillRectangle(1, 0, 1, 1);

        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(2, 0, 1, 1);

        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(3, 0, 1, 1);

        Texture texture = new Texture(pixmap);
        TextureRegion regionNormal = new TextureRegion(texture, 0, 0, 1, 1);
        TextureRegion regionDragged = new TextureRegion(texture, 1, 0, 1, 1);
        TextureRegion regionValid = new TextureRegion(texture, 2, 0, 1, 1);
        TextureRegion regionInvalid = new TextureRegion(texture, 3, 0, 1, 1);

        normalTRD = new TextureRegionDrawable(regionNormal);
        touchDraggedTRD = new TextureRegionDrawable(regionDragged);
        validTRD = new TextureRegionDrawable(regionValid);
        invalidTRD = new TextureRegionDrawable(regionInvalid);
    }

    public TextureRegionDrawable getNormalTRD() {
        return normalTRD;
    }

    public TextureRegionDrawable getTouchDraggedTRD() {
        return touchDraggedTRD;
    }

    public TextureRegionDrawable getValidTRD() {
        return validTRD;
    }

    public TextureRegionDrawable getInvalidTRD() {
        return invalidTRD;
    }
}
