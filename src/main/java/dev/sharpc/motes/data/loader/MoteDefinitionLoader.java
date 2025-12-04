package dev.sharpc.motes.data.loader;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.MoteDefinition;
import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.data.registry.MoteDefinitions;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

/**
 * Loads all {@link MoteDefinition} data files from data packs and registers them
 * into {@link MoteDefinitions}. This loader makes MoteDefinitions fully
 * data-driven and supports contributions from any namespace (including other mods).
 * <p>
 *
 * <h2>📁 Folder Structure</h2>
 * Mote definitions are discovered under the following path in any data pack:
 *
 * <pre>
 * data/&lt;namespace&gt;/motes/&lt;path&gt;.json
 * </pre>
 * <p>
 * For example, the file:
 *
 * <pre>
 * data/motes/motes/mote/fire.json
 * </pre>
 * <p>
 * produces the {@code ResourceLocation}:
 *
 * <pre>
 * motes:mote/fire
 * </pre>
 * <p>
 * Likewise, a third-party mod may contribute its own motes:
 *
 * <pre>
 * data/othermod/motes/mote/arcane.json
 * </pre>
 * <p>
 * which becomes:
 *
 * <pre>
 * othermod:mote/arcane
 * </pre>
 *
 * <p>
 *
 * <h2>🔗 How IDs Are Created</h2>
 * The {@link FileToIdConverter} used by this loader converts each JSON file into
 * a {@code ResourceLocation} whose:
 *
 * <ul>
 *   <li>namespace = the data folder's namespace (e.g., {@code motes}, {@code othermod})</li>
 *   <li>path      = the JSON file path relative to {@code /motes/}</li>
 * </ul>
 * <p>
 * In {@link #apply(Map, ResourceManager, ProfilerFiller)}, each file's
 * {@code ResourceLocation} is wrapped into a {@link MoteId}:
 *
 * <pre>
 * MoteId moteId = new MoteId(fileId);
 * MoteDefinitions.register(moteId, parsedDefinition);
 * </pre>
 * <p>
 * This means that the data file path fully determines the MoteId.
 *
 * <p>
 *
 * <h2>🧩 JSON Schema</h2>
 * Individual definition files contain only the properties of the mote:
 *
 * <pre>{@code
 * {
 *   "stability": 0.95,
 *   "grain": "motes:grain/fire"
 * }
 * }</pre>
 * <p>
 * Their ID is <b>implicitly</b> derived from their location on disk and is not
 * stored inside the JSON.
 * <p>
 *
 * <h2>🔄 Resource Reload Behavior</h>
 * Whenever resource packs are reloaded, this loader:
 *
 * <ol>
 *   <li>Clears the global {@link MoteDefinitions} registry</li>
 *   <li>Parses all JSON definition files under {@code /motes}</li>
 *   <li>Registers a {@link MoteDefinition} for each discovered {@link MoteId}</li>
 * </ol>
 * <p>
 * This design allows:
 * <ul>
 *   <li>Motes to be added by any mod</li>
 *   <li>Motes to be overridden or replaced by datapacks</li>
 *   <li>Mote behavior (tier, color, stability, grain output, etc.) to be fully modifiable without code changes</li>
 * </ul>
 *
 * <p>
 *
 * <h2>🧪 Example MoteId Mapping</h2>
 * Given the file:
 *
 * <pre>
 * data/motes/motes/mote/elemental/fire.json
 * </pre>
 * <p>
 * The loader will generate:
 *
 * <pre>
 * fileId = motes:mote/elemental/fire
 * moteId = new MoteId(fileId)
 * </pre>
 * <p>
 * And insert it into the registry:
 *
 * <pre>
 * MoteDefinitions.register( MoteId("motes:mote/elemental/fire"), parsedDefinition );
 * </pre>
 *
 * <p>
 *
 * <h2>🌐 Cross-Mod Compatibility</h2>
 * Because this loader does not force the {@code motes} namespace, it naturally
 * supports motes from other mods. Each mod simply places JSON files in its own
 * namespace under {@code definitions/motes}, and their MoteIds will be preserved
 * exactly as written.
 *
 * <p>
 *
 * @see MoteDefinitions
 * @see MoteDefinition
 * @see MoteId
 * @see FileToIdConverter
 * @see SimpleJsonResourceReloadListener
 */

@SuppressWarnings("deprecation")
public final class MoteDefinitionLoader
        extends SimpleJsonResourceReloadListener<MoteDefinition>
        implements IdentifiableResourceReloadListener
{
    public static final ResourceLocation ID = Motes.createModdedResourceLocation("motes");

    public MoteDefinitionLoader()
    {
        super(MoteDefinition.CODEC, FileToIdConverter.json(ID.getPath()));
    }

    @Override
    protected void apply(Map<ResourceLocation, MoteDefinition> object, ResourceManager resourceManager, ProfilerFiller profilerFiller)
    {
        MoteDefinitions.clear();

        for (var entry : object.entrySet())
        {
            Motes.LOGGER.info("Building definition for: {}", entry.getKey());
            MoteDefinitions.register(new MoteId(entry.getKey()), entry.getValue());
        }
    }

    @Override
    public ResourceLocation getFabricId()
    {
        return ID;
    }
}
