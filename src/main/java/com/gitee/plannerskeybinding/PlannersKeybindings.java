package com.gitee.plannerskeybinding;

import com.gitee.plannerskeybinding.common.handler.InputHandler;
import com.gitee.plannerskeybinding.common.network.KeyboardNetwork;
import com.gitee.plannerskeybinding.common.network.packet.KeybindingInputPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = PlannersKeybindings.MODID, name = PlannersKeybindings.NAME, version = PlannersKeybindings.VERSION)
public class PlannersKeybindings {
    public static final String MODID = "plannerskeybindings";
    public static final String NAME = "Planners Keybindings";
    public static final String VERSION = "1.0";

    private static Logger logger;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        KeyboardNetwork.CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("planners:keybindings");
        KeyboardNetwork.registerMessage(KeybindingInputPacket.class,KeybindingInputPacket.Handler.class);
        MinecraftForge.EVENT_BUS.register(new InputHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

    }


}
