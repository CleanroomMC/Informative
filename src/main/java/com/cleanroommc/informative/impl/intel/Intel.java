package com.cleanroommc.informative.impl.intel;

import com.cleanroommc.informative.api.IIntel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;

public class Intel implements IIntel {

    private Object object;
    private BlockPos pos;
    private Vec3d hitVector;
    private EnumFacing facing;
    private ItemStack pickBlock, rawState;
    private TileEntity tileEntity;

    public void set(Object object, BlockPos pos, Vec3d hitVector, EnumFacing facing, ItemStack pickBlock) {
        this.object = object;
        this.pos = pos;
        this.hitVector = hitVector;
        this.facing = facing;
        this.pickBlock = pickBlock;
        // Reset
        reset();
    }

    private void reset() {
        this.rawState = null;
        this.tileEntity = null;
    }

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }

    @Override
    public Vec3d getHitVector() {
        return hitVector;
    }

    @Override
    public EnumFacing getHitFace() {
        return facing;
    }

    @Override
    public ItemStack getPickBlock() {
        return pickBlock;
    }

    @Override
    public ItemStack getRawState() {
        if (this.rawState == null) {
            if (this.object instanceof IBlockState) {
                IBlockState state = (IBlockState) this.object;
                this.rawState = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
            }
        }
        return this.rawState;
    }

    @Override
    public TileEntity getTileEntity(IBlockAccess world) {
        if (this.tileEntity == null) {
            if (this.object instanceof IBlockState) {
                this.tileEntity = world.getTileEntity(pos);
            }
        }
        return this.tileEntity;
    }

}
