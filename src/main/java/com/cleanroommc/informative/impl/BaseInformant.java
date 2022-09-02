package com.cleanroommc.informative.impl;

import com.cleanroommc.informative.api.IFluidIntelObject;
import com.cleanroommc.informative.api.IInformant;
import com.cleanroommc.informative.api.IIntel;
import com.cleanroommc.informative.api.ITooltip;
import com.cleanroommc.informative.util.ModUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BaseInformant implements IInformant {

    @Override
    public void informBlock(ITooltip<?> tooltip, IIntel<IBlockState> intel, EntityPlayerSP player, WorldClient world, float partialTicks) {
        ItemStack block = intel.getPickBlock();
        if (!block.isEmpty()) {
            tooltip.horizontal()
                    .item(block)
                    .vertical()
                    .itemLabel(block)
                    .text(ModUtil.getModName(block.getItem()));
        }
    }

    @Override
    public void informFluid(ITooltip<?> tooltip, IIntel<IFluidIntelObject> intel, EntityPlayerSP player, WorldClient world, float partialTicks) {
        IFluidIntelObject fluidObj = intel.getObject();
        tooltip.horizontal()
                .item(fluidObj.getBucket())
                .vertical(0)
                .text(fluidObj.getFluidStack().getLocalizedName())
                .text(ModUtil.getModName(FluidRegistry.getModId(fluidObj.getFluidStack())));
    }

}
