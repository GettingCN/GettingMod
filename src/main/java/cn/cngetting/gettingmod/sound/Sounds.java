package cn.cngetting.gettingmod.sound;

import cn.cngetting.gettingmod.util.ModId;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Sounds {

    public static void register() {
        Music.register();
    }

    static SoundEvent register(String name){
        Identifier ID = ModId.of( name);
        return Registry.register(Registry.SOUND_EVENT, ID,new SoundEvent(ID));
    }
}