package com.cleanroommc.informative.impl;

import com.cleanroommc.informative.Informative;
import com.cleanroommc.informative.api.IInformant;
import com.cleanroommc.informative.impl.intel.FluidIntelObject;
import com.cleanroommc.informative.impl.intel.Intel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public enum Renderer {

    INSTANCE;

    private final Tooltip tooltip = new Tooltip();
    private final Intel intel = new Intel();
    private final FluidIntelObject fluidIntelObject = new FluidIntelObject(intel);

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != ElementType.TEXT) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.objectMouseOver == null) {
            return;
        }
        tooltip.clear();
        render(mc, mc.player, mc.world, mc.objectMouseOver, event.getPartialTicks());
    }

    private void render(Minecraft mc, EntityPlayerSP player, WorldClient world, RayTraceResult mouseOver, float partialTicks) {
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        double scaledWidth = scaledresolution.getScaledWidth_double();
        double scaledHeight = scaledresolution.getScaledHeight_double();
        double scale = 1.0F; // TODO: configurable
        boolean render = false;
        switch (mouseOver.typeOfHit) {
            case BLOCK:
                render = informBlock(player, world, mouseOver, partialTicks);
                break;
            case ENTITY:
                render = informEntity(player, world, mouseOver, partialTicks);
                break;
            case MISS:
                Vec3d start = player.getPositionEyes(partialTicks);
                Vec3d end = player.getLook(partialTicks);
                double distance = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
                end = start.add(end.x * distance, end.y * distance, end.z * distance);
                mouseOver = world.rayTraceBlocks(start, end, true);
                if (mouseOver != null && mouseOver.typeOfHit == Type.BLOCK) {
                    render = informFluid(player, world, mouseOver, partialTicks);
                }
        }
        if (render) {
            setupOverlayRendering(scaledWidth, scaledHeight, scale);
            renderElements((int) (scaledWidth * scale), (int) (scaledHeight * scale));
            setupOverlayRendering(scaledWidth, scaledHeight, 1.0F);
        }
    }

    private boolean informBlock(EntityPlayerSP player, WorldClient world, RayTraceResult mouseOver, float partialTicks) {
        BlockPos pos = mouseOver.getBlockPos();
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock().isAir(state, world, pos)) {
            return false;
        }
        Tooltip tooltip = this.tooltip;
        Intel intel = this.intel;
        intel.set(state, pos, mouseOver.hitVec, mouseOver.sideHit, state.getBlock().getPickBlock(state, mouseOver, world, pos, player));
        List<IInformant> informants = Informative.INSTANCE.getInformants();
        for (int i = 0; i < informants.size(); i++) {
            informants.get(i).informBlock(tooltip, intel, player, world, partialTicks);
        }
        return true;
    }

    private boolean informFluid(EntityPlayerSP player, WorldClient world, RayTraceResult mouseOver, float partialTicks) {
        BlockPos pos = mouseOver.getBlockPos();
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock().isAir(state, world, pos)) {
            return false;
        }
        Fluid fluid = FluidRegistry.lookupFluidForBlock(state.getBlock());
        if (fluid != null) {
            Tooltip tooltip = this.tooltip;
            Intel intel = this.intel;
            intel.set(fluidIntelObject, pos, mouseOver.hitVec, mouseOver.sideHit, ItemStack.EMPTY);
            fluidIntelObject.set(fluid, state);
            List<IInformant> informants = Informative.INSTANCE.getInformants();
            for (int i = 0; i < informants.size(); i++) {
                informants.get(i).informFluid(tooltip, intel, player, world, partialTicks);
            }
            return true;
        }
        return false;
    }

    private boolean informEntity(EntityPlayerSP player, WorldClient world, RayTraceResult mouseOver, float partialTicks) {
        Entity entity = mouseOver.entityHit;
        if (entity == null) {
            return false;
        }
        if (entity instanceof MultiPartEntityPart) {
            IEntityMultiPart part = ((MultiPartEntityPart) entity).parent;
            if (part instanceof Entity) {
                entity = (Entity) part;
            }
        }
        intel.set(entity, mouseOver.getBlockPos(), mouseOver.hitVec, mouseOver.sideHit, null);
        List<IInformant> informants = Informative.INSTANCE.getInformants();
        for (int i = 0; i < informants.size(); i++) {
            informants.get(i).informEntity(tooltip, intel, player, world, partialTicks);
        }
        return true;
    }

    private void setupOverlayRendering(double scaledWidth, double scaledHeight, double scale) {
        GlStateManager.clear(256);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, scaledWidth * scale, scaledHeight * scale, 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }

    private void renderElements(int scaledWidth, int scaledHeight) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        Tooltip tooltip = this.tooltip;
        int x = (scaledWidth - tooltip.getWidth()) / 2;
        int y = 5;
        tooltip.render(x, y, scaledWidth, scaledHeight);
    }

}
