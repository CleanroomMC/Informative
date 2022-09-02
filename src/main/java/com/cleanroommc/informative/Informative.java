package com.cleanroommc.informative;

import com.cleanroommc.informative.api.IInformant;
import com.cleanroommc.informative.impl.BaseInformant;
import com.cleanroommc.informative.impl.Renderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod(modid = Informative.MODID, name = Informative.NAME, version = Informative.VERSION)
public class Informative {

    public static final String MODID = "informative";
    public static final String NAME = "Informative";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogManager.getLogger("Informative");

    @Instance
    public static Informative INSTANCE;

    private final List<IInformant> informants = new ArrayList<>();
    private final List<IInformant> unmodifiableInformants = Collections.unmodifiableList(informants);

    public Informative() {
        registerInformant(new BaseInformant());
    }

    public void registerInformant(IInformant informant) {
        this.informants.add(informant);
    }

    public List<IInformant> getInformants() {
        return unmodifiableInformants;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(Renderer.INSTANCE);
        }
    }

}
