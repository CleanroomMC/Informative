package com.cleanroommc.informative.impl.intel;

import com.cleanroommc.informative.api.IFluidIntelObject;
import com.cleanroommc.informative.api.IIntel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;

public class FluidIntelObject implements IFluidIntelObject {

    private IIntel<IFluidIntelObject> intel;
    private boolean isForgeFluid = false;
    private Fluid fluid;
    private FluidStack fluidStack;
    private ItemStack bucket;
    private IBlockState state;

    public FluidIntelObject(IIntel<IFluidIntelObject> intel) {
        this.intel = intel;
    }

    public void setIntel(IIntel<IFluidIntelObject> intel) {
        this.intel = intel;
        this.isForgeFluid = false;
    }

    public void set(Fluid fluid, IBlockState state) {
        this.isForgeFluid = state.getBlock() instanceof IFluidBlock;
        this.fluid = fluid;
        this.fluidStack = new FluidStack(fluid, 1000);
        this.bucket = FluidUtil.getFilledBucket(this.fluidStack);
        this.state = state;
    }

    @Override
    public IIntel<IFluidIntelObject> getIntel() {
        return intel;
    }

    @Override
    public boolean isForgeFluid() {
        return isForgeFluid;
    }

    @Override
    public Fluid getFluid() {
        return fluid;
    }

    @Override
    public FluidStack getFluidStack() {
        return fluidStack;
    }

    @Override
    public ItemStack getBucket() {
        return bucket;
    }

    @Override
    public IBlockState getState() {
        return state;
    }

}
