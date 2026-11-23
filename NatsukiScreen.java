package natsuki;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;

public class NatsukiScreen extends Screen {
    private int pulse = 0;
    public NatsukiScreen() { super(Text.literal("Natsuki Visual")); }
    @Override public void render(DrawContext d, int mouseX, int mouseY, float delta) {
        pulse = (pulse + 1) % 120;
        int w = 320, h = 220, x = (width-w)/2, y = (height-h)/2;
        d.fill(0,0,width,height,0x660B0610);
        d.fill(x,y,x+w,y+h,0xEE211323);
        d.fill(x,y,x+w,y+2,0xFFFF83D2);
        d.fill(x,y,x+80,y+h,0xAA351C3A);
        d.drawText(textRenderer, Text.literal("Natsuki Visual ♡"), x+14,y+14,0xFFFFA9DC,false);
        d.drawText(textRenderer, Text.literal("VISUAL SETTINGS"), x+14,y+40,0xFFFFD6EF,false);
        d.drawText(textRenderer, Text.literal("Watermark: " + (NatsukiVisual.watermark ? "ON" : "OFF")), x+98,y+66,0xFFFFB7E3,false);
        d.drawText(textRenderer, Text.literal("FPS / Ping: " + (NatsukiVisual.stats ? "ON" : "OFF")), x+98,y+88,0xFFFFB7E3,false);
        d.drawText(textRenderer, Text.literal("Block outline: " + (NatsukiVisual.pinkBlockOutline ? "ON" : "OFF")), x+98,y+110,0xFFFFB7E3,false);
        d.drawText(textRenderer, Text.literal("[1] Toggle watermark"), x+98,y+142,0xFFFFD6EF,false);
        d.drawText(textRenderer, Text.literal("[2] Toggle stats"), x+98,y+158,0xFFFFD6EF,false);
        d.drawText(textRenderer, Text.literal("[3] Block outline option"), x+98,y+174,0xFFFFD6EF,false);
        d.drawText(textRenderer, Text.literal("Right Shift / Esc - close"), x+14,y+h-16,0xFFFF83D2,false);
        super.render(d,mouseX,mouseY,delta);
    }
    @Override public boolean keyPressed(KeyInput input) {
        int k = input.key();
        if (k == GLFW_KEY_1) { NatsukiVisual.watermark = !NatsukiVisual.watermark; return true; }
        if (k == GLFW_KEY_2) { NatsukiVisual.stats = !NatsukiVisual.stats; return true; }
        if (k == GLFW_KEY_3) { NatsukiVisual.pinkBlockOutline = !NatsukiVisual.pinkBlockOutline; return true; }
        if (k == 256) { close(); return true; }
        return super.keyPressed(input);
    }
    private static final int GLFW_KEY_1 = 49, GLFW_KEY_2 = 50, GLFW_KEY_3 = 51;
    @Override public void close() { MinecraftClient.getInstance().setScreen(null); }
    @Override public boolean shouldPause() { return false; }
}
