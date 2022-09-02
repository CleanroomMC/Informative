package com.cleanroommc.informative.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;

public class ModUtil {

    private static final Map<String, String> modIdToName = new Object2ObjectOpenHashMap<>();

    public static String getModName(IForgeRegistryEntry<?> block) {
        ResourceLocation resourceLocation = block.getRegistryName();
        if (resourceLocation == null) {
            return "Minecraft";
        }
        final String modId = resourceLocation.getNamespace();
        return modIdToName.computeIfAbsent(modId, k -> {
            String name = Loader.instance().getIndexedModList().get(modId).getName();
            if (name == null) {
                name = WordUtils.capitalize(modId);
            }
            return name;
        });
    }

    public static String getModName(Entity entity) {
        EntityRegistration reg = EntityRegistry.instance().lookupModSpawn(entity.getClass(), true);
        if (reg == null) {
            return "Minecraft";
        }
        ModContainer container = reg.getContainer();
        if (container == null) {
            return "Minecraft";
        }
        return container.getName();
    }

    private ModUtil() { }

}
