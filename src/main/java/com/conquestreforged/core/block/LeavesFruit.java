package com.conquestreforged.core.block;

import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LeavesFruit extends BlockCrops implements IShearable {

    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE_1_7;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    private final IItemProvider fruit;

    public LeavesFruit(Props props) {
        super(props.toProperties());
        this.fruit = props.get("fruit", IItemProvider.class);
        this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, 7).with(PERSISTENT, false).with(AGE, 0));
    }

    @Override
    public boolean getTickRandomly(BlockState state) {
        return state.get(DISTANCE) == 7 && !state.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (!state.get(PERSISTENT) && state.get(DISTANCE) == 7) {
            state.dropBlockAsItem(world, pos, 0);
            world.removeBlock(pos);
        }
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        world.setBlockState(pos, updateDistance(state, world, pos), 3);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
        int i = getDistance(state1) + 1;
        if (i != 1 || state.get(DISTANCE) != i) {
            world.getPendingBlockTicks().scheduleTick(pos, this, 1);
        }

        return state;
    }

    private static BlockState updateDistance(BlockState state, IWorld world, BlockPos pos) {
        int i = 7;
        BlockPos.PooledMutableBlockPos position = BlockPos.PooledMutableBlockPos.retain();
        Throwable var5 = null;

        try {
            Direction[] var6 = Direction.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction Direction = var6[var8];
                position.setPos(pos).move(Direction);
                i = Math.min(i, getDistance(world.getBlockState(position)) + 1);
                if (i == 1) {
                    break;
                }
            }
        } catch (Throwable var17) {
            var5 = var17;
            throw var17;
        } finally {
            if (position != null) {
                if (var5 != null) {
                    try {
                        position.close();
                    } catch (Throwable var16) {
                        var5.addSuppressed(var16);
                    }
                } else {
                    position.close();
                }
            }
        }
        return state.with(DISTANCE, i);
    }

    private static int getDistance(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        } else {
            return state.getBlock() instanceof BlockLeaves ? state.get(DISTANCE) : 7;
        }
    }

    @Override
    public void dropBlockAsItemWithChance(BlockState state, World world, BlockPos pos, float f, int n) {
        super.dropBlockAsItemWithChance(state, world, pos, f, n);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateList) {
        stateList.add(DISTANCE, PERSISTENT, AGE);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return updateDistance(this.getDefaultState().with(PERSISTENT, true), context.getWorld(), context.getPos());
    }

    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack itemStack, IWorld world, BlockPos pos, int num) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
        return Arrays.asList(new ItemStack(this));
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader reader, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.motionX *= 0.4D;
        entity.motionZ *= 0.4D;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReaderBase reader, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean isValidGround(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return true;
    }

    @Override
    public void getDrops(BlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int num) {
        super.getDrops(state, drops, world, pos, 0);
        drops.clear();
        drops.add(new ItemStack(this, 1));
    }

    @Override
    public IItemProvider getItemDropped(BlockState state, World world, BlockPos pos, int num) {
        return this;
    }

    @Override
    public ItemStack getItem(IBlockReader reader, BlockPos pos, BlockState state) {
        return new ItemStack(this,1);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        if (this.isMaxAge(state)) {
            if (worldIn.isRemote) {
                return true;
            } else {
                state = state.with(AGE, 0);
                worldIn.setBlockState(pos, state, 3);
                this.dropFruit(worldIn, pos, state);
                return true;

            }
        }
        return false;
    }

    private void dropFruit(World worldIn, BlockPos pos, BlockState state) {
        if (!worldIn.isRemote) {
            float f = 0.7F;
            double d0 = (double) (worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
            double d1 = (double) (worldIn.rand.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
            double d2 = (double) (worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
            ItemStack itemstack1 = new ItemStack(fruit, 1);
            EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, itemstack1);
            entityitem.setDefaultPickupDelay();
            worldIn.spawnEntity(entityitem);
        }
    }

    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XYZ;
    }
}
