package cn.cngetting.gettingmod.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class ItemHasTooltip extends Item {

    public ItemHasTooltip(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        // 默认为白色文本
        tooltip.add(Text.translatable(itemStack.getItem().getTranslationKey() + ".tooltip"));
    }
}
