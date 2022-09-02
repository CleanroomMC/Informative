package com.cleanroommc.informative.impl;

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
    public void informFluid(ITooltip<?> tooltip, IIntel<IBlockState> intel, EntityPlayerSP player, WorldClient world, float partialTicks) {
        ItemStack bucket = intel.getPickBlock();
        if (!bucket.isEmpty()) {
            Fluid fluid = FluidRegistry.lookupFluidForBlock(intel.getObject().getBlock());
            tooltip.horizontal()
                    .item(bucket)
                    .vertical(4)
                    .text(I18n.format(fluid.getUnlocalizedName()))
                    .text(new ResourceLocation(FluidRegistry.getDefaultFluidName(fluid)).getNamespace());
        }
    }

}
