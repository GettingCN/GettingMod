package cn.cngetting.gettingmod.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class TinTool implements ToolMaterial {
    public static final TinTool INSTANCE = new TinTool();

    @Override
    public int getDurability() {
        return 775;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 7.0f;
    }

    @Override
    public float getAttackDamage() {
        return 0.0f;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.TIN_INGOT);
    }
}
