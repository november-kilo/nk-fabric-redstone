package net.fabricmc.nkredstone;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NkRedstoneMod implements ModInitializer {
    public static final String MODID = "nkredstone";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        NkRedstoneBlocks.init();
    }
}
