package dev.sharpc.motes;

import dev.sharpc.motes.registry.*;
import dev.sharpc.motes.registry.mote.MoteDefinitions;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Motes implements ModInitializer
{
    public static final String MOD_ID = "motes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize()
    {
        ModBlocks.initialize();
        ModBlockEntities.initialize();
        ModItems.initialize();
        ModDataComponents.initialize();
        MoteDefinitions.bootstrap();
        ModCreativeTabs.initialize();
    }

    public static ResourceLocation createModdedResourceLocation(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static <T> ResourceKey<T> createModdedResourceKey(ResourceKey<? extends Registry<T>> resourceKey, String path)
    {
        return ResourceKey.create(resourceKey, createModdedResourceLocation(path));
    }
}