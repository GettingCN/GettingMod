package cn.cngetting.gettingmod;

import net.fabricmc.api.ModInitializer;
import cn.cngetting.gettingmod.block.ModBlocks;
import cn.cngetting.gettingmod.item.GettingModGroup;
import cn.cngetting.gettingmod.item.ModItems;
import cn.cngetting.gettingmod.sound.Sounds;
import cn.cngetting.gettingmod.block.ModOre;
import cn.cngetting.gettingmod.feature.ModFeature;
import net.minecraft.item.ItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
    public static final String ID;
    public static final String ID_;
    public static final Logger LOGGER;
    public static final ItemGroup GETTING_MOD_GROUP;

    static {
        ID = "gettingmod";
        ID_ = "gettingmod:";
        LOGGER = LoggerFactory.getLogger(ID);
        GETTING_MOD_GROUP = GettingModGroup.GETTING_MOD_GROUP;
    }

    @Override
    public void onInitialize() {
        long startInitTime = System.currentTimeMillis();
        LOGGER.info("Initialization is in progress");

        ModBlocks.register();
        ModFeature.register();
        ModItems.register();
        Sounds.register();

        startInitTime = System.currentTimeMillis() - startInitTime;
        LOGGER.info("Initialization complete (Took"+ startInitTime + "ms)");
    }
}
