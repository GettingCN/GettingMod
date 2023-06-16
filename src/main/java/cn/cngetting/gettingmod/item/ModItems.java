package cn.cngetting.gettingmod.item;

import cn.cngetting.gettingmod.item.tools.HoeItemPublic;
import cn.cngetting.gettingmod.util.ModId;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import cn.cngetting.gettingmod.item.tools.AxeItemPublic;
import cn.cngetting.gettingmod.item.tools.PickaxeItemPublic;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

public class ModItems {
    //普通物品创建
    public static final Item TIN_INGOT;
    public static final Item RAW_TIN;
    //特殊物品创建
    //盔甲
    public static final TinArmor TIN_ARMOR_MATERIAL;
    public static final Item TIN_MATERIAL_HEAD;
    public static final Item TIN_MATERIAL_CHEST;
    public static final Item TIN_MATERIAL_LEGS;
    public static final Item TIN_MATERIAL_FEET;
    //工具
    public static final ToolItem TIN_PICKAXE;
    public static final ToolItem TIN_AXE;
    public static final ToolItem TIN_SHOVEL;
    public static final ToolItem TIN_HOE;
    public static final ToolItem TIN_SWORD;

    static {
        TIN_INGOT = new ItemHasTooltip(new FabricItemSettings());
        RAW_TIN = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(2).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20*10, 3),0.99F).build()));

        TIN_ARMOR_MATERIAL = new TinArmor();
        TIN_MATERIAL_HEAD = new ArmorItem(TIN_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings());
        TIN_MATERIAL_CHEST = new ArmorItem(TIN_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings());
        TIN_MATERIAL_LEGS = new ArmorItem(TIN_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings());
        TIN_MATERIAL_FEET = new ArmorItem(TIN_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings());

        TIN_PICKAXE = new PickaxeItemPublic(TinTool.INSTANCE, 3, -2.8f, new Item.Settings());
        TIN_AXE = new AxeItemPublic(TinTool.INSTANCE, 7.5F, -3.0F, new Item.Settings());
        TIN_SHOVEL = new ShovelItem(TinTool.INSTANCE, 3.5F, -2.0F, new Item.Settings());
        TIN_HOE = new HoeItemPublic(TinTool.INSTANCE, 0, 0F, new Item.Settings());
        TIN_SWORD = new SwordItem(TinTool.INSTANCE,5,-2f,new Item.Settings());
    }

    public static void register() {


        register("tin_ingot", TIN_INGOT);
        register("raw_tin", RAW_TIN);

        register("tin_material_head", TIN_MATERIAL_HEAD);
        register("tin_material_chest", TIN_MATERIAL_CHEST);
        register("tin_material_legs", TIN_MATERIAL_LEGS);
        register("tin_material_feet", TIN_MATERIAL_FEET);

        register("tin_pickaxe", TIN_PICKAXE);
        register("tin_axe", TIN_AXE);
        register("tin_shovel", TIN_SHOVEL);
        register("tin_hoe", TIN_HOE);
        register("tin_sword",TIN_SWORD);
    }

    private static void register(String id, Item item){
        Registry.register(Registry.ITEM, ModId.of(id), item);
    }
}
