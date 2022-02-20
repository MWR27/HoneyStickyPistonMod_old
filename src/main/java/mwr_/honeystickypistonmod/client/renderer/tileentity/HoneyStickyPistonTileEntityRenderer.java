package mwr_.honeystickypistonmod.client.renderer.tileentity;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mwr_.honeystickypistonmod.block.HoneyStickyPistonBlock;
import mwr_.honeystickypistonmod.block.HoneyStickyPistonHeadBlock;
import mwr_.honeystickypistonmod.block.ModBlocks;
import mwr_.honeystickypistonmod.tileentity.HoneyStickyPistonTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.PistonType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HoneyStickyPistonTileEntityRenderer extends TileEntityRenderer<HoneyStickyPistonTileEntity> {
	   private BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
	   
	   public HoneyStickyPistonTileEntityRenderer(TileEntityRendererDispatcher p_i226012_1_) {
	      super(p_i226012_1_);
	   }

	   @Override
	   public void render(HoneyStickyPistonTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
	      World world = tileEntityIn.getLevel();
	      if (world != null) {
	         BlockPos blockpos = tileEntityIn.getBlockPos().relative(tileEntityIn.getMotionDirection().getOpposite());
	         BlockState blockstate = tileEntityIn.getPistonState();
	         if (!blockstate.isAir()) {
	            BlockModelRenderer.enableCaching();
	            matrixStackIn.pushPose();
	            matrixStackIn.translate((double)tileEntityIn.getOffsetX(partialTicks), (double)tileEntityIn.getOffsetY(partialTicks), (double)tileEntityIn.getOffsetZ(partialTicks));
	            if (blockstate.is(ModBlocks.HONEY_STICKY_PISTON_HEAD.get()) && tileEntityIn.getProgress(partialTicks) <= 4.0F) {
	               blockstate = blockstate.setValue(HoneyStickyPistonHeadBlock.SHORT, Boolean.valueOf(tileEntityIn.getProgress(partialTicks) <= 0.5F));
	               this.renderBlock(blockpos, blockstate, matrixStackIn, bufferIn, world, false, combinedOverlayIn);
	            } else if (tileEntityIn.shouldPistonHeadBeRendered() && !tileEntityIn.isExtending()) {
	               PistonType pistontype = PistonType.STICKY;
	               BlockState blockstate1 = ModBlocks.HONEY_STICKY_PISTON_HEAD.get().defaultBlockState().setValue(HoneyStickyPistonHeadBlock.TYPE, pistontype).setValue(HoneyStickyPistonHeadBlock.FACING, blockstate.getValue(HoneyStickyPistonBlock.FACING));
	               blockstate1 = blockstate1.setValue(HoneyStickyPistonHeadBlock.SHORT, Boolean.valueOf(tileEntityIn.getProgress(partialTicks) >= 0.5F));
	               this.renderBlock(blockpos, blockstate1, matrixStackIn, bufferIn, world, false, combinedOverlayIn);
	               BlockPos blockpos1 = blockpos.relative(tileEntityIn.getMotionDirection());
	               matrixStackIn.popPose();
	               matrixStackIn.pushPose();
	               blockstate = blockstate.setValue(HoneyStickyPistonBlock.EXTENDED, Boolean.valueOf(true));
	               this.renderBlock(blockpos1, blockstate, matrixStackIn, bufferIn, world, true, combinedOverlayIn);
	            } else {
	               this.renderBlock(blockpos, blockstate, matrixStackIn, bufferIn, world, false, combinedOverlayIn);
	            }

	            matrixStackIn.popPose();
	            BlockModelRenderer.clearCache();
	         }
	      }
	   }

	   private void renderBlock(BlockPos p_228876_1_, BlockState p_228876_2_, MatrixStack p_228876_3_, IRenderTypeBuffer p_228876_4_, World p_228876_5_, boolean p_228876_6_, int p_228876_7_) {
	      net.minecraftforge.client.ForgeHooksClient.renderPistonMovedBlocks(p_228876_1_, p_228876_2_, p_228876_3_, p_228876_4_, p_228876_5_, p_228876_6_, p_228876_7_, blockRenderer == null ? blockRenderer = Minecraft.getInstance().getBlockRenderer() : blockRenderer);
	      if(false) {
	      RenderType rendertype = RenderTypeLookup.getMovingBlockRenderType(p_228876_2_);
	      IVertexBuilder ivertexbuilder = p_228876_4_.getBuffer(rendertype);
	      this.blockRenderer.getModelRenderer().tesselateBlock(p_228876_5_, this.blockRenderer.getBlockModel(p_228876_2_), p_228876_2_, p_228876_1_, p_228876_3_, ivertexbuilder, p_228876_6_, new Random(), p_228876_2_.getSeed(p_228876_1_), p_228876_7_);
	      }
	   }
}
