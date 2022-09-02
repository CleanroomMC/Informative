package com.cleanroommc.informative.api;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;

public interface IIntel<T> {

    T getObject();

    BlockPos getPos();

    Vec3d getHitVector();

    EnumFacing getHitFace();

    ItemStack getPickBlock();

    ItemStack getRawState();

    TileEntity getTileEntity(IBlockAccess world);

}
