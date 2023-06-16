package cn.cngetting.gettingmod;

import cn.cngetting.gettingmod.block.ModBlocks;
import cn.cngetting.gettingmod.block.entities.ContainerEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import cn.cngetting.gettingmod.block.model.FourSidedFurnaceModel;
import cn.cngetting.gettingmod.screen.BoxBlockScreen;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_FRAME, RenderLayer.getCutout());
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new FourSidedFurnaceModel.ModelProvider());

        BlockEntityRendererRegistry.register(ModBlocks.CONTAINER_ENTITY, ContainerEntity.ContainerBlockEntityRenderer::new);

        ScreenRegistry.register(ModBlocks.BOX_BLOCK_SCREEN_HANDLER, BoxBlockScreen::new);
    }
}
