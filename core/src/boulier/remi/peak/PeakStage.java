package boulier.remi.peak;

import com.badlogic.gdx.Gdx;
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

    public interface OnDragListener {
        void onSelect(String word);

        void onComplete(String word, boolean isWordValid);
    }

    public PeakStage(OnDragListener listener) {
        super();
        this.listener = listener;
        this.letterSquares = new ArrayList<LetterSquare>();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        super.touchDown(screenX, screenY, pointer, button);
        // The user can use only one finger.
        return pointer <= 0 && onTouch(screenX, screenY);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        super.touchDragged(screenX, screenY, pointer);
        Gdx.app.log("PeakStage/touchDragged", "screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer);

        // The user can use only one finger.
        return pointer <= 0 && onTouch(screenX, screenY);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        super.touchUp(screenX, screenY, pointer, button);

        if (letterSquares.isEmpty()) return false;

        String word = generateWord(true);
        boolean isWordValid = word.compareTo("ABC") == 0;

        Gdx.app.log("touchUp", "Result: " + word);
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
        LetterSquare actor = (LetterSquare) hit(stageCoord.x, stageCoord.y, true);
        if (actor != null && !letterSquares.contains(actor)) {
            Gdx.app.log("Actor", "Actor hits");
            actor.setSelected(true);
            letterSquares.add(actor);

            listener.onSelect(generateWord(false));

            return true;
        }

        return false;
    }

    private String generateWord(boolean unselectLetters) {
        StringBuilder builder = new StringBuilder();

        for (LetterSquare square : letterSquares) {
            Gdx.app.log("Actor", "Actor unselected");
            builder.append(square.getText());
            if (unselectLetters)
                square.setSelected(false);
        }

        return builder.toString();
    }
}
