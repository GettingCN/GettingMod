package cn.cngetting.gettingmod.item;

import cn.cngetting.gettingmod.block.ModBlocks;
import cn.cngetting.gettingmod.sound.Music;
import cn.cngetting.gettingmod.util.ModId;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import cn.cngetting.gettingmod.sound.Sounds;
import cn.cngetting.gettingmod.block.ModOre;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class GettingModGroup {
    public static final ItemGroup GETTING_MOD_GROUP;

    static {
        GETTING_MOD_GROUP = FabricItemGroupBuilder.create(
                        ModId.of("getting_item_group"))
                .icon(() -> new ItemStack(ModItems.TIN_INGOT))
                .appendItems(stacks -> {
                    stacks.add(new ItemStack(ModItems.RAW_TIN));
                    stacks.add(new ItemStack(ModItems.TIN_INGOT));
                    stacks.add(new ItemStack(ModOre.TIN_BLOCK));
                    stacks.add(new ItemStack(ModOre.TIN_ORE));
                    stacks.add(new ItemStack(ModItems.TIN_MATERIAL_HEAD));
                    stacks.add(new ItemStack(ModItems.TIN_MATERIAL_CHEST));
                    stacks.add(new ItemStack(ModItems.TIN_MATERIAL_LEGS));
                    stacks.add(new ItemStack(ModItems.TIN_MATERIAL_FEET));
                    stacks.add(new ItemStack(ModItems.TIN_SWORD));
                    stacks.add(new ItemStack(ModItems.TIN_SHOVEL));
                    stacks.add(new ItemStack(ModItems.TIN_PICKAXE));
                    stacks.add(new ItemStack(ModItems.TIN_AXE));
                    stacks.add(new ItemStack(ModItems.TIN_HOE));
                    stacks.add(new ItemStack(ModBlocks.DECO_BLOCK));
                    stacks.add(new ItemStack(ModBlocks.RED_FRAME));
                    stacks.add(new ItemStack(ModBlocks.CONTAINER_BLOCK));
                    stacks.add(new ItemStack(ModBlocks.GOLD_BLOCK_DISGUISE));
                    stacks.add(new ItemStack(ModBlocks.BOX_BLOCK));
                    stacks.add(new ItemStack(Music.DISC_I_GOT_SMOKE));
                    stacks.add(new ItemStack(Music.DISC_BRIGHT_PIECE));
                    stacks.add(new ItemStack(ModBlocks.RICH_SAPLING));
                })
                .build();
    }
}
