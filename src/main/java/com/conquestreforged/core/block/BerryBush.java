package com.conquestreforged.core.block;

import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BerryBush extends BlockCrops {

    private final IItemProvider fruit;

    public BerryBush(Props props) {
        super(props.toProperties());
        this.fruit = props.get("fruit", IItemProvider.class);
    }

    @Override
    public VoxelShape getRenderShape(IBlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isValidPosition(IBlockState state, IWorldReaderBase reader, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean isValidGround(IBlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return true;
    }

    @Override
    public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int num) {
        super.getDrops(state, drops, world, pos, 0);
        drops.clear();
        drops.add(new ItemStack(this, 1));
    }

    @Override
    public IItemProvider getItemDropped(IBlockState state, World world, BlockPos pos, int num) {
        return this;
    }

    @Override
    public ItemStack getItem(IBlockReader reader, BlockPos pos, IBlockState state) {
        return new ItemStack(this,1);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
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

    private void dropFruit(World worldIn, BlockPos pos, IBlockState state) {
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
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XYZ;
    }
}
