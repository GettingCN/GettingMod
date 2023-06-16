package cn.cngetting.gettingmod.feature.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public record ExampleFeatureConfig(int number, Identifier blockID) implements FeatureConfig {
    public static Codec<ExampleFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance ->/*  you can add as many of these as you want, one for each parameter
                                        您可以根据需要添加任意数量的这些参数，每个参数一个*/
                    instance.group(
                                    Codecs.POSITIVE_INT.fieldOf("number").forGetter(ExampleFeatureConfig::number),
                                    Identifier.CODEC.fieldOf("blockID").forGetter(ExampleFeatureConfig::blockID))
                            .apply(instance, ExampleFeatureConfig::new));

    public int number() {
        return number;
    }
    public Identifier blockID() {
        return blockID;
    }
}