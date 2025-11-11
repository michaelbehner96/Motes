package dev.sharpc.motes.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record MoteData(ResourceKey<Item> product)
{
    public static final MoteData DEFAULT = new MoteData(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("iron_ingot")));
    public static final Codec<MoteData> CODEC = RecordCodecBuilder.create(inst -> inst.group(ResourceKey.codec(Registries.ITEM).fieldOf("product").forGetter(MoteData::product)).apply(inst, MoteData::new));
}
