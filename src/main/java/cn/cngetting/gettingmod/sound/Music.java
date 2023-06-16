package cn.cngetting.gettingmod.sound;

import cn.cngetting.gettingmod.util.ModId;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Music {
    public static final SoundEvent I_GOT_SMOKE;
    public static final MusicDiscItem DISC_I_GOT_SMOKE;
    public static final SoundEvent BRIGHT_PIECE;
    public static final MusicDiscItem DISC_BRIGHT_PIECE;

    static {
        I_GOT_SMOKE = Sounds.register("i_got_smoke");
        DISC_I_GOT_SMOKE = new MusicDiscItem(
                15,
                I_GOT_SMOKE,
                new Item.Settings()
                        .maxCount(1)
                        .rarity(Rarity.EPIC),
                248);

        BRIGHT_PIECE = Sounds.register("bright_piece");
        DISC_BRIGHT_PIECE = new MusicDiscItem(
                15,
                BRIGHT_PIECE,
                new Item.Settings()
                        .maxCount(1).
                        rarity(Rarity.EPIC),
                283);
    }

    static void register(){
        register("i_got_smoke_disc", DISC_I_GOT_SMOKE);
        register("bright_piece_disc", DISC_BRIGHT_PIECE);
    }

    private static void register(String id, Item item){
        Registry.register(Registry.ITEM, ModId.of(id), item);
    }
}
