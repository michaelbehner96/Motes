package dev.sharpc.motes;

import dev.sharpc.motes.data.loader.TransmutationRecipeLoader;
import dev.sharpc.motes.registry.*;
import dev.sharpc.motes.data.loader.MoteDefinitionLoader;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class Motes implements ModInitializer
{
    public static final String MOD_ID = "motes";
    public static final String MOTE_PATH_PREFIX = "mote/";
    public static final String MATERIAL_PATH_PREFIX = "material/";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize()
    {
        ModBlocks.initialize();
        ModBlockEntities.initialize();
        ModItems.initialize();
        ModDataComponents.initialize();
        ModCreativeTabs.initialize();

        ResourceManagerHelper
                .get(PackType.SERVER_DATA)
                .registerReloadListener(new MoteDefinitionLoader());

        ResourceManagerHelper
                .get(PackType.SERVER_DATA)
                .registerReloadListener(new TransmutationRecipeLoader());
    }

    public static ResourceLocation createModdedResourceLocation(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation createMoteResourceLocation(String path)
    {
        return createModdedResourceLocation(MOTE_PATH_PREFIX + path);
    }

    public static ResourceLocation createMaterialResourceLocation(String path)
    {
        return createModdedResourceLocation(MATERIAL_PATH_PREFIX + path);
    }

    public static <T> ResourceKey<T> createModdedResourceKey(ResourceKey<? extends Registry<T>> resourceKey, String path)
    {
        return ResourceKey.create(resourceKey, createModdedResourceLocation(path));
    }
}