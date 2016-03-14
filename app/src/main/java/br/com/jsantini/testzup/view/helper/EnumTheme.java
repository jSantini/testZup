package br.com.jsantini.testzup.view.helper;

import android.support.annotation.ColorRes;

import br.com.jsantini.testzup.R;

/**
 * Created by jsantini on 13/03/16.
 */
public enum EnumTheme {

    main(R.color.colorPrimary, R.color.colorPrimaryDark,
            R.color.backgroundDark, R.color.colorText),
    comedy(R.color.colorComedyPrimary, R.color.colorComedyPrimaryDark,
            R.color.colorComedybackgroundDark, R.color.colorComedyText),
    drama(R.color.colorDramaPrimary, R.color.colorDramaPrimaryDark,
            R.color.colorDramabackgroundDark, R.color.colorDramaText),
    action(R.color.colorActionPrimary, R.color.colorActionPrimaryDark,
            R.color.colorActionbackgroundDark, R.color.colorActionText),
    horror(R.color.colorHorrorPrimary, R.color.colorHorrorPrimaryDark,
            R.color.colorHorrorbackgroundDark, R.color.colorHorrorText);

    private final int mColorPrimaryId;
    private final int mWindowBackgroundColorId;
    private final int mColorPrimaryDarkId;
    private final int mColorText;

    EnumTheme(final int colorPrimaryId, final int colorPrimaryDarkId,
              final int windowBackgroundColorId, final int textColorWhiteId) {
        mColorPrimaryId = colorPrimaryId;
        mWindowBackgroundColorId = windowBackgroundColorId;
        mColorPrimaryDarkId = colorPrimaryDarkId;
        mColorText = textColorWhiteId;
    }


    @ColorRes
    public int getWindowBackgroundColor() {
        return mWindowBackgroundColorId;
    }

    public int getmColorPrimaryId() {
        return mColorPrimaryId;
    }

    public int getmWindowBackgroundColorId() {
        return mWindowBackgroundColorId;
    }

    public int getmColorPrimaryDarkId() {
        return mColorPrimaryDarkId;
    }

    public int getmColorText() {
        return mColorText;
    }

}
