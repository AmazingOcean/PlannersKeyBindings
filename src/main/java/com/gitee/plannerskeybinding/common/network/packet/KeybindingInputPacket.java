package com.gitee.plannerskeybinding.common.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class KeybindingInputPacket implements IMessage {

    private int code;

    private boolean isKeyDown;

    private boolean isPressed;

    public KeybindingInputPacket() {
    }

    public KeybindingInputPacket(KeyBinding binding) {
        this.code = binding.getKeyCode();
        this.isKeyDown = binding.isKeyDown();
        this.isPressed = binding.isPressed();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        buf.writeInt(code);
        buf.writeBoolean(isKeyDown);
        buf.writeBoolean(isPressed);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.code = buf.readInt();
        this.isKeyDown = buf.readBoolean();
        this.isPressed = buf.readBoolean();
    }

    public static class Handler implements IMessageHandler<KeybindingInputPacket, IMessage> {

        @Override
        public KeybindingInputPacket onMessage(KeybindingInputPacket message, MessageContext ctx) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "KeybindingInputPacket{" +
                "code=" + code +
                ", isKeyDown=" + isKeyDown +
                ", isPressed=" + isPressed +
                '}';
    }
}
