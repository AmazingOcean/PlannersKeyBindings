package com.gitee.plannerskeybinding.common.handler;

import com.gitee.plannerskeybinding.common.network.KeyboardNetwork;
import com.gitee.plannerskeybinding.common.network.packet.KeybindingInputPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InputHandler {

    private static final List<KeyBinding> bindings = new ArrayList<>();

    @SubscribeEvent
    public void handleKeyPress(InputEvent.KeyInputEvent e) {
        if (Minecraft.getMinecraft().inGameHasFocus) {
            bindings.stream().filter(KeyBinding::isKeyDown).forEach(binding -> {
                KeybindingInputPacket packet = new KeybindingInputPacket(binding);
                KeyboardNetwork.CHANNEL.sendToServer(packet);
                System.out.println("send packet " + packet);
            });
        }

    }

    @SubscribeEvent
    public void handleChat(ClientChatEvent e) {
        String message = e.getMessage();
        if (message.startsWith("binding")) {
            int id = Keyboard.getKeyIndex(message.split(" ")[1]);
            registerBinding(new KeyBinding("key.test", id, "key.categories.multiplayer"));
            Minecraft.getMinecraft().player.sendChatMessage("binding " + id);
        }
        // un register
        else if (message.startsWith("unbinding")) {
            int id = Keyboard.getKeyIndex(message.split(" ")[1]);
            Optional<KeyBinding> binding = getKeyBinding(id);
            if (binding.isPresent()) {
                unregisterBinding(binding.get());
                Minecraft.getMinecraft().player.sendChatMessage("unbinding " + id);
            }
        }
    }

    public static void unregisterBindings() {

        for (KeyBinding binding : new ArrayList<>(bindings)) {
            unregisterBinding(binding);
        }
    }

    public static Optional<KeyBinding> getKeyBinding(int code) {
        return bindings.stream().filter(binding -> binding.getKeyCode() == code).findFirst();
    }

    public static void unregisterBinding(KeyBinding binding) {
        bindings.remove(binding);
        Minecraft.getMinecraft().gameSettings.keyBindings = Arrays
                .stream(Minecraft.getMinecraft().gameSettings.keyBindings)
                .filter(key -> key != binding)
                .toArray(KeyBinding[]::new);
    }

    public static void registerBinding(KeyBinding binding) {
        bindings.add(binding);
        ClientRegistry.registerKeyBinding(binding);
        System.out.println("register key binding " + binding);
    }

}
