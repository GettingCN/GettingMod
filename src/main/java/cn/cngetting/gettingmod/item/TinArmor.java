package cn.cngetting.gettingmod.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class TinArmor implements ArmorMaterial {
    private static final int[] BASE_DURABILITY;
    private static final int[] PROTECTION_VALUES;

    static {
        BASE_DURABILITY = new int[] {13, 15, 16, 11};
        PROTECTION_VALUES = new int[] {2, 5, 6, 3};
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * 23;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.TIN_INGOT);
    }

    @Override
    public String getName() {
        return "tin";
    }

    @Override
    public float getToughness() {
        return 1f;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1f;
    }
}
