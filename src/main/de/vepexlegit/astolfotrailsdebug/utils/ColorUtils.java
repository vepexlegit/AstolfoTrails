package de.vepexlegit.astolfotrailsdebug.utils;

import java.awt.*;

public class ColorUtils {
    public static Color astolfo(float yDist, float yTotal, float saturation, float speedt) {
        float speed = 1800F;
        float hue = (System.currentTimeMillis() % (int)speed) + (yTotal - yDist) * speedt;
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5F) {
            hue = 0.5F - (hue - 0.5F);
        }
        hue += 0.5F;
        return Color.getHSBColor(hue, saturation, 1F);
    }
}
