package de.vepexlegit.astolfotrailsdebug;

import de.vepexlegit.astolfotrailsdebug.commands.*;
import de.vepexlegit.astolfotrailsdebug.utils.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import java.util.*;

@Mod(modid = "astolfotrailsdebug", version = "1.0")
public class AstolfoTrailsDebug {
    private static final Minecraft mc = Minecraft.getMinecraft();
    ArrayList<Point> points = new ArrayList<Point>();

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new AstolfoTrailsCommand());
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (AstolfoTrails.INSTANCE.isEnabled()) {
            if ((mc.gameSettings.thirdPersonView == 1 || mc.gameSettings.thirdPersonView == 2)) {
                points.removeIf(p -> p.age >= 100);
                float x = (float)(mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * event.partialTicks);
                float y = (float)(mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * event.partialTicks);
                float z = (float)(mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * event.partialTicks);
                points.add(new Point(x, y, z));
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(GL11.GL_CULL_FACE);
                for (final Point t : points) {
                    if (points.indexOf(t) >= points.size() - 1) continue;
                    Point temp = points.get(points.indexOf(t) + 1);
                    float a = 255.0F;
                    Color color = Color.WHITE;
                    color = ColorUtils.astolfo(t.age - t.age + 1, t.age, 0.5F, 16);
                    Color c = RenderUtils.injectAlpha(color, (int) a);
                    GL11.glBegin(GL11.GL_QUAD_STRIP);
                    final double x2 = t.x - mc.thePlayer.posX;
                    final double y2 = t.y - mc.thePlayer.posY;
                    final double z2 = t.z - mc.thePlayer.posZ;
                    final double x1 = temp.x - mc.thePlayer.posX;
                    final double y1 = temp.y - mc.thePlayer.posY;
                    final double z1 = temp.z - mc.thePlayer.posZ;
                    RenderUtils.glColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0).getRGB());
                    GL11.glVertex3d(x2, y2 + mc.thePlayer.height - 0.1, z2);
                    RenderUtils.glColor(c.getRGB());
                    GL11.glVertex3d(x2, y2 + 0.2, z2);
                    GL11.glVertex3d(x1, y1 + mc.thePlayer.height - 0.1, z1);
                    GL11.glVertex3d(x1, y1 + 0.2, z1);
                    GL11.glEnd();
                    ++t.age;
                }
                GlStateManager.resetColor();
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
        if (mc.gameSettings.thirdPersonView == 0 || !AstolfoTrails.INSTANCE.isEnabled()) {
            points.clear();
        }
    }

    class Point {
        public final float x, y, z;
        public float age = 0;
        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
