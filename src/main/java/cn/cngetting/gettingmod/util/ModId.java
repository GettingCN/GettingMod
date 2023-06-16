package cn.cngetting.gettingmod.util;

import cn.cngetting.gettingmod.Main;
import net.minecraft.util.Identifier;

public class ModId {
    public static final String ID = "gettingmod";
    public static final String ID_ = "gettingmod:";

    public static Identifier of(String path){
        return Identifier.of(ID, path);
    }

    public static String set(String path){
        return (ID + ":" + path);
    }

    public static Identifier getModId(){
        return new Identifier(ID);
    }
}
