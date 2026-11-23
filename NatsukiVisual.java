package natsuki;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class NatsukiVisual implements ClientModInitializer {
    public static final String ID = "natsuki_visual";
    public static KeyBinding menuKey;
    public static boolean watermark = true;
    public static boolean stats = true;
    public static boolean pinkBlockOutline = false; // placeholder, renderer not implemented

    @Override public void onInitializeClient() {
        KeyBinding.Category cat = KeyBinding.Category.create(Identifier.of(ID, "main"));
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.natsuki_visual.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, cat));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new NatsukiScreen());
                else if (client.currentScreen instanceof NatsukiScreen) client.setScreen(null);
            }
        });

        HudElementRegistry.attachElementAfter(VanillaHudElements.MISC_OVERLAYS,
            Identifier.of(ID, "hud"), (draw, tick) -> renderHud(draw));
    }

    private static void renderHud(DrawContext draw) {
        MinecraftClient c = MinecraftClient.getInstance();
        if (c.player == null || c.world == null || !watermark) return;
        draw.fill(8, 8, 178, 34, 0xAA281629);
        draw.fill(8, 8, 10, 34, 0xFFFF79C8);
        draw.drawText(c.textRenderer, Text.literal("Natsuki Visual ♡"), 16, 12, 0xFFFFB7E3, false);
        if (stats) {
            int ping = 0;
            if (c.getNetworkHandler() != null) {
                PlayerListEntry e = c.getNetworkHandler().getPlayerListEntry(c.player.getUuid());
                if (e != null) ping = e.getLatency();
            }
            draw.drawText(c.textRenderer, Text.literal("FPS " + c.getCurrentFps() + "  •  Ping " + ping + "ms"),
                16, 23, 0xFFFFD6EF, false);
        }
    }
}
