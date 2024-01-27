package com.gitee.plannerskeybinding.common.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.nio.channels.NetworkChannel;

public class KeyboardNetwork {

    public static SimpleNetworkWrapper CHANNEL;

    private static int discriminant = 0;

    public static <M extends IMessage,H extends IMessageHandler<M,IMessage>> void registerMessage(Class<M> message,Class<H> handler) {
        CHANNEL.registerMessage(handler,message,discriminant++, Side.CLIENT);
    }

}
