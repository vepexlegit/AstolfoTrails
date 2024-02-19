package de.vepexlegit.astolfotrailsdebug.utils;

import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import java.awt.*;

public class RenderUtils {
    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static Color injectAlpha(Color color, int alpha) {
        alpha = MathHelper.clamp_int(alpha, 0, 255);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
