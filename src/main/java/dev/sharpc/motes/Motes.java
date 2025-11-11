package dev.sharpc.motes;

import dev.sharpc.motes.registry.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Motes implements ModInitializer
{
    public static final String MOD_ID = "motes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize()
    {
        LOGGER.info("Hello Fabric world!");
        ModItems.initialize();
    }
}