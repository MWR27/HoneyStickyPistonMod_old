package mwr_.honeystickypistonmod.block;

import java.util.Arrays;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.PistonType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class HoneyStickyPistonHeadBlock extends PistonHeadBlock{
   private static final VoxelShape[] EXTENDED_SHAPES = getShapesForExtension(true);
   private static final VoxelShape[] UNEXTENDED_SHAPES = getShapesForExtension(false);

   private static VoxelShape[] getShapesForExtension(boolean extended) {
      return Arrays.stream(Direction.values()).map((direction) -> {
         return getShapeForDirection(direction, extended);
      }).toArray((id) -> {
         return new VoxelShape[id];
      });
   }

   private static VoxelShape getShapeForDirection(Direction direction, boolean shortArm) {
      switch(direction) {
      case DOWN:
      default:
         return VoxelShapes.or(DOWN_AABB, shortArm ? SHORT_DOWN_ARM_AABB : DOWN_ARM_AABB);
      case UP:
         return VoxelShapes.or(UP_AABB, shortArm ? SHORT_UP_ARM_AABB : UP_ARM_AABB);
      case NORTH:
         return VoxelShapes.or(NORTH_AABB, shortArm ? SHORT_NORTH_ARM_AABB : NORTH_ARM_AABB);
      case SOUTH:
         return VoxelShapes.or(SOUTH_AABB, shortArm ? SHORT_SOUTH_ARM_AABB : SOUTH_ARM_AABB);
      case WEST:
         return VoxelShapes.or(WEST_AABB, shortArm ? SHORT_WEST_ARM_AABB : WEST_ARM_AABB);
      case EAST:
         return VoxelShapes.or(EAST_AABB, shortArm ? SHORT_EAST_ARM_AABB : EAST_ARM_AABB);
      }
   }

   public HoneyStickyPistonHeadBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, PistonType.STICKY).setValue(SHORT, Boolean.valueOf(false)));

   }
   @Override //Nothing changed, may not be necessary
   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return (state.getValue(SHORT) ? EXTENDED_SHAPES : UNEXTENDED_SHAPES)[state.getValue(FACING).ordinal()];
   }
   
   private boolean isExtended(BlockState baseState, BlockState extendedState) { 
      return extendedState.is(ModBlocks.HONEY_STICKY_PISTON.get()) && extendedState.getValue(HoneyStickyPistonBlock.EXTENDED) && extendedState.getValue(FACING) == baseState.getValue(FACING);
   }

   @Override
   public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
       Direction direction = state.getValue(FACING);
       BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
       boolean dropBlock = !player.isCreative();

       if (direction.compareTo(Direction.UP) == 0) {
           worldIn.destroyBlock(blockPos.below(), dropBlock);
       }
       else if(direction.compareTo(Direction.DOWN) == 0) {
           worldIn.destroyBlock(blockPos.above(), dropBlock);
       }
       else if(direction.compareTo(Direction.EAST) == 0) {
           worldIn.destroyBlock(blockPos.west(), dropBlock);
       }
       else if(direction.compareTo(Direction.WEST) == 0) {
           worldIn.destroyBlock(blockPos.east(), dropBlock);
       }
       else if(direction.compareTo(Direction.NORTH) == 0) {
           worldIn.destroyBlock(blockPos.south(), dropBlock);
       }
       else if(direction.compareTo(Direction.SOUTH) == 0) {
           worldIn.destroyBlock(blockPos.north(), dropBlock);
       }

       super.playerWillDestroy(worldIn, pos, state, player);
   }


   @Override  //Nothing changed, may not be necessary
   public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
      BlockState blockstate = worldIn.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
      return this.isExtended(state, blockstate) || blockstate.is(Blocks.MOVING_PISTON) && blockstate.getValue(FACING) == state.getValue(FACING);
   }
   
   @Override
   public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
      return new ItemStack(ModBlocks.HONEY_STICKY_PISTON.get());
   }


}
