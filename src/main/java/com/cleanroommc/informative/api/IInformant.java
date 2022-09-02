package com.cleanroommc.informative.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;

public interface IInformant {

    default void informBlock(ITooltip<?> tooltip, IIntel<IBlockState> intel, EntityPlayerSP player, WorldClient world, float partialTicks) {

    }

    // TODO: Fluidlogged-API Support
    default void informFluid(ITooltip<?> tooltip, IIntel<IBlockState> intel, EntityPlayerSP player, WorldClient world, float partialTicks) {

    }

    default void informEntity(ITooltip<?> tooltip, IIntel<Entity> intel, EntityPlayerSP player, WorldClient world, float partialTicks) {

    }

}
