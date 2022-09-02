package com.cleanroommc.informative.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Used as a parameter in {@link IIntel}
 *
 * TODO: Fluidlogged-API Support
 */
public interface IFluidIntelObject {

    IIntel<IFluidIntelObject> getIntel();

    boolean isForgeFluid();

    Fluid getFluid();

    FluidStack getFluidStack();

    ItemStack getBucket();

    IBlockState getState();
}
