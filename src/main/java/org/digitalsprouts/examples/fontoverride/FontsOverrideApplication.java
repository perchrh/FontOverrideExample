package org.digitalsprouts.examples.fontoverride;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

public class FontsOverrideApplication extends Application {

    private static final String DEFAULT_BOLD_FONT_FILENAME = "Arimo-Bold.ttf";
    private static final String DEFAULT_BOLD_ITALIC_FONT_FILENAME = "Arimo-BoldItalic";
    private static final String DEFAULT_ITALIC_FONT_FILENAME = "Arimo-Italic.ttf";
    private static final String DEFAULT_NORMAL_FONT_FILENAME = "Arimo-Regular.ttf";

    @Override
    public void onCreate() {
        super.onCreate();

        setDefaultFont();
    }


    private void setDefaultFont() {

        try {
            final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_BOLD_FONT_FILENAME);
            final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_ITALIC_FONT_FILENAME);
            final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_BOLD_ITALIC_FONT_FILENAME);
            final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_FONT_FILENAME);

            Field DEFAULT = Typeface.class.getDeclaredField("DEFAULT");
            DEFAULT.setAccessible(true);
            DEFAULT.set(null, normal);

            Field DEFAULT_BOLD = Typeface.class.getDeclaredField("DEFAULT_BOLD");
            DEFAULT_BOLD.setAccessible(true);
            DEFAULT_BOLD.set(null, bold);

            Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
            sDefaults.setAccessible(true);
            sDefaults.set(null, new Typeface[]{normal, bold, italic, boldItalic});


        } catch (NoSuchFieldException e) {
            logFontError(e);
        } catch (IllegalAccessException e) {
            logFontError(e);
        } catch (Throwable e) {
            //cannot crash app if there is a failure with overriding the default font!
            logFontError(e);
        }
    }

    private void logFontError(Throwable e) {
        Log.e("font_override", "Error overriding font", e);
    }
}
