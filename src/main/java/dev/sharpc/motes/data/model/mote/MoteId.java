package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;
import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.loader.MoteDefinitionLoader;
import dev.sharpc.motes.data.registry.MoteDefinitions;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the unique identifier of a mote type in the Motes mod. A {@code MoteId}
 * is a value object that wraps a {@link ResourceLocation} and serves as the canonical,
 * data-driven identity for mote definitions, item instances, rendering, and behavior.
 * <p>
 *
 * <h2>📁 Origin of MoteIds (Data-Driven)</h2>
 * MoteIds are not written manually in code. Instead, they are created implicitly by
 * {@link MoteDefinitionLoader}, which derives IDs
 * directly from JSON file locations in data packs.
 * <p>
 * For a definition file located at:
 *
 * <pre>
 * data/motes/motes/mote/fire.json
 * </pre>
 * <p>
 * the resulting MoteId becomes:
 *
 * <pre>
 * motes:mote/fire
 * </pre>
 * <p>
 * Third-party mods may supply their own motes by using their own namespace:
 *
 * <pre>
 * data/othermod/motes/mote/arcane.json
 * </pre>
 * <p>
 * which produces:
 *
 * <pre>
 * othermod:mote/arcane
 * </pre>
 * <p>
 * This system preserves namespaces exactly as authored, enabling full cross-mod compatibility.
 * <p>
 *
 * <h2>🔗 Structural Rules</h2>
 * All MoteIds follow a consistent path structure:
 *
 * <pre>
 * &lt;namespace&gt;:mote/&lt;path&gt;
 * </pre>
 * <p>
 * Examples:
 *
 * <pre>
 * motes:mote/fire
 * motes:mote/elemental/water
 * othermod:mote/arcane
 * </pre>
 * <p>
 * The {@code "mote/"} prefix distinguishes mote identifiers from other data-driven
 * content types within the same namespace.
 * <p>
 *
 * <h2>🗣️ Translation Keys</h2>
 * The {@link #getTranslationKey()} method converts the {@link ResourceLocation}
 * into a stable, human-readable lang key:
 *
 * <pre>
 * mote.&lt;namespace&gt;.&lt;path-without-"mote/"-prefix&gt;
 * </pre>
 * <p>
 * For example:
 *
 * <pre>
 * new MoteId("motes:mote/fire").getTranslationKey()
 * → "mote.motes.fire"
 * </pre>
 * <p>
 * This translation pattern stays stable even if mote IDs use deeper category
 * hierarchies:
 *
 * <pre>
 * motes:mote/elemental/fire → "mote.motes.fire"
 * </pre>
 * <p>
 *
 * <h2>📦 Serialization</h2>
 * {@link #CODEC} wraps {@link ResourceLocation#CODEC}, allowing MoteIds to be used
 * directly in Data Components, JSON definitions, or network packets.
 * <p>
 * Example JSON usage:
 *
 * <pre>{@code
 * {
 *   "mote_id": "motes:mote/fire"
 * }
 * }</pre>
 * <p>
 *
 * <h2>❗ Validation</h2>
 * A {@code MoteId} cannot be created with a {@code null} ResourceLocation.
 * All other validation (such as ensuring appropriate prefixes) is handled by
 * the data loader layer to allow flexible datapack behavior.
 * <p>
 *
 * <h2>🛠️ Internal Helpers</h2>
 * {@link #of(String)} is an internal convenience method that creates a namespaced
 * MoteId within the Motes mod, automatically applying the required {@code "mote/"}
 * prefix.
 * <p>
 *
 * @see MoteDefinition
 * @see MoteDefinitionLoader
 * @see net.minecraft.resources.ResourceLocation
 */

public record MoteId(ResourceLocation id)
{
    public static final Codec<MoteId> CODEC =
            ResourceLocation.CODEC.xmap(MoteId::new, MoteId::id);

    private static final String ID_TYPE = "mote";
    private static final String ID_PREFIX = "mote/";

    public MoteId
    {
        if (id == null)
            throw new IllegalArgumentException("ResourceLocation of %s cannot be null.".formatted(ID_PREFIX));
    }

    public String getTranslationKey()
    {
        var pathWithoutPrefix = id().getPath().replaceFirst(ID_PREFIX, "");
        var nameStartIndex = pathWithoutPrefix.contains("/") ? id.getPath().lastIndexOf('/') + 1 : 0;
        var name = pathWithoutPrefix.substring(nameStartIndex);

        return "%s.%s.%s".formatted(ID_TYPE, id().getNamespace(), name);
    }

    public @Nullable MoteDefinition definition()
    {
        return MoteDefinitions.get(this);
    }

    private static MoteId of(String name)
    {
        return new MoteId(Motes.createModdedResourceLocation(name).withPrefix(ID_PREFIX));
    }
}
