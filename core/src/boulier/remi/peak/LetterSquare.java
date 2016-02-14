package boulier.remi.peak;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Remi BOULIER on 14/02/2016.
 * email: boulier.r+dev@gmail.com
 */
public class LetterSquare extends TextButton {

    private boolean isSelected = false;

    public LetterSquare(String text, TextButtonStyle style) {
        super(text, style);
        getLabel().setTouchable(Touchable.disabled); // Disabled the touchability on the Label.
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;

    }
}
