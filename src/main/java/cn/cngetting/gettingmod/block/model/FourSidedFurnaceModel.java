package cn.cngetting.gettingmod.block.model;

import cn.cngetting.gettingmod.util.ModId;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class FourSidedFurnaceModel implements UnbakedModel,BakedModel, FabricBakedModel {
    private static final SpriteIdentifier[] SPRITE_IDS = new SpriteIdentifier[]{
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("minecraft:block/furnace_front_on")),
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("minecraft:block/furnace_top"))
    };
    private static final Identifier DEFAULT_BLOCK_MODEL = new Identifier("minecraft:block/block");
    private ModelTransformation transformation;
    private Sprite[] SPRITES = new Sprite[2];
    private Mesh mesh;

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Arrays.asList(DEFAULT_BLOCK_MODEL);
        /*return Collections.emptyList(); // 模型不依赖于其他模型。*/
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Arrays.asList(SPRITE_IDS); // 本模型（以及其模型依赖，依赖的依赖，等）依赖的纹理。
    }

    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        // 加载默认方块模型
        JsonUnbakedModel defaultBlockModel = (JsonUnbakedModel) loader.getOrLoadModel(DEFAULT_BLOCK_MODEL);
        // 获取 ModelTransformation
        transformation = defaultBlockModel.getTransformations();
        // 获得sprites
        for(int i = 0; i < SPRITE_IDS.length; ++i) {
            SPRITES[i] = textureGetter.apply(SPRITE_IDS[i]);
        }
        // 用Renderer API构建mesh
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        for(Direction direction : Direction.values()) {
            int spriteIdx = direction == Direction.UP || direction == Direction.DOWN ? 1 : 0;
            // 将新的面（face）添加到mesh
            emitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
            // 设置面的sprite，必须在.square()之后调用
            // 我们还没有指定任何uv坐标，所以我们使用整个纹理，BAKE_LOCK_UV恰好就这么做。
            emitter.spriteBake(0, SPRITES[spriteIdx], MutableQuadView.BAKE_LOCK_UV);
            // 启用纹理使用
            emitter.spriteColor(0, -1, -1, -1, -1);
            // 将quad添加到mesh
            emitter.emit();
        }
        mesh = builder.build();

        return this;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
        return Collections.emptyList(); // 不需要，因为我们使用的是 FabricBakedModel
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true; // 环境光遮蔽：我们希望方块在有临近方块时显示阴影
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return true;
    }

    @Override
    public Sprite getParticleSprite() {
        return SPRITES[1]; // 方块被破坏时产生的颗粒，使用furnace_top
    }

    @Override
    public ModelTransformation getTransformation() {
        return transformation;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false; // false 以触发 FabricBakedModel 渲染
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        // 渲染函数

        // 我们仅渲染 mesh
        renderContext.meshConsumer().accept(mesh);
    }

    @Override
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext renderContext) {
        renderContext.meshConsumer().accept(mesh);
    }

    @Environment(EnvType.CLIENT)
    public static class ModelProvider implements ModelResourceProvider {
        public static final FourSidedFurnaceModel FOUR_SIDED_FURNACE_MODEL = new FourSidedFurnaceModel();
        public static final Identifier FOUR_SIDED_FURNACE_MODEL_BLOCK = ModId.of("block/four_sided_furnace");
        public static final Identifier FOUR_SIDED_FURNACE_MODEL_ITEM = ModId.of("item/four_sided_furnace");

        @Override
        public UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext) {
            if(identifier.equals(FOUR_SIDED_FURNACE_MODEL_BLOCK) || identifier.equals(FOUR_SIDED_FURNACE_MODEL_ITEM)) {
                return FOUR_SIDED_FURNACE_MODEL;
            } else {
                return null;
            }
        }

        /*public static final Identifier FOUR_SIDED_FURNACE_MODEL = new Identifier(Main.ID_ + "block/four_sided_furnace");
        @Override
        public UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext){
            if(identifier.equals(FOUR_SIDED_FURNACE_MODEL)) {
                return new FourSidedFurnaceModel();
            } else {
                return null;
            }
        }*/
    }
}
