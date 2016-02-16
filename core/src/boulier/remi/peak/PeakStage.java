package boulier.remi.peak;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by Remi BOULIER on 14/02/2016.
 * email: boulier.r+dev@gmail.com
 */
public class PeakStage extends Stage {

    private final ArrayList<LetterSquare> letterSquares;
    private final OnDragListener listener;
    private final StringBuilder builder;
    private final ArrayList<String> dictionary;

    public PeakStage(OnDragListener listener, ArrayList<String> dictionary) {
        super();
        this.listener = listener;
        this.letterSquares = new ArrayList<LetterSquare>();
        this.builder = new StringBuilder();
        this.dictionary = dictionary != null ? dictionary : new ArrayList<String>();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        Gdx.app.log("PeakStage/touchDown", "screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer);

        // The user can use only one finger.
        if (pointer > 0) return false;

        builder.setLength(0);
        return onTouch(screenX, screenY);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        Gdx.app.log("PeakStage/touchDragged", "screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer);

        // The user can use only one finger.
        return pointer <= 0 && onTouch(screenX, screenY);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (letterSquares.isEmpty()) return false;

        String word = builder.toString().toLowerCase();
        boolean isWordValid = dictionary.contains(word);

        for (LetterSquare square : letterSquares) {
            if (isWordValid)
                square.onValidWordAction();
            else
                square.onInValidWordAction();
        }

        listener.onComplete(word, isWordValid);
        letterSquares.clear();

        return true;
    }

    private boolean onTouch(int screenX, int screenY) {
        Vector2 stageCoord = screenToStageCoordinates(new Vector2(screenX, screenY));
        LetterSquare square = (LetterSquare) hit(stageCoord.x, stageCoord.y, true);
        if (square != null && !letterSquares.contains(square)) {
            square.setSelected();
            letterSquares.add(square);
            builder.append(square.getText());
            listener.onSelect(builder.toString());

            return true;
        }

        return false;
    }

    /* Interface */

    public interface OnDragListener {
        void onSelect(String word);

        void onComplete(String word, boolean isWordValid);
    }
}
