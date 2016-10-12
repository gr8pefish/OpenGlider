package gr8pefish.openglider.common.entity;

import com.google.common.collect.MapMaker;
import gr8pefish.openglider.common.item.ItemHangGlider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class EntityHangGlider {


//    public void onUpdate() {
//        if (!isGliderValid(player, this)) {
//            setDead();
//        }
//
//        if (isDead) {
////            gliderMap.remove(player);
//            return;
//        }
//
//        boolean isDeployed = player.onGround || player.isInWater();
//
//        if (!worldObj.isRemote) {
////            this.dataWatcher.updateObject(PROPERTY_DEPLOYED, (byte)(isDeployed? 1 : 0));
//            fixPositions(player, false);
//        }
//
//        if (!isDeployed && player.motionY < 0) {
//            final double horizontalSpeed;
//            final double verticalSpeed;
//
//            if (player.isSneaking()) {
//                horizontalSpeed = 0.1;
//                verticalSpeed = 0.7;
//            } else {
//                horizontalSpeed = 0.03;
//                verticalSpeed = 0.4;
//            }
//
//            player.motionY *= verticalSpeed;
//            motionY *= verticalSpeed;
//            double x = Math.cos(Math.toRadians(player.rotationYawHead + 90)) * horizontalSpeed;
//            double z = Math.sin(Math.toRadians(player.rotationYawHead + 90)) * horizontalSpeed;
//            player.motionX += x;
//            player.motionZ += z;
//            player.fallDistance = 0f; /* Don't like getting hurt :( */
//        }
//
//    }
//
//
//
//    private void fixPositions(EntityPlayer thePlayer, boolean localPlayer) {
//        this.lastTickPosX = prevPosX = player.prevPosX;
//        this.lastTickPosY = prevPosY = player.prevPosY;
//        this.lastTickPosZ = prevPosZ = player.prevPosZ;
//
//        this.posX = player.posX;
//        this.posY = player.posY;
//        this.posZ = player.posZ;
//
//        setPosition(posX, posY, posZ);
//        this.prevRotationYaw = player.prevRenderYawOffset;
//        this.rotationYaw = player.renderYawOffset;
//
//        this.prevRotationPitch = player.prevRotationPitch;
//        this.rotationPitch = player.rotationPitch;
//
//        if (!localPlayer) {
//            this.posY += 1.2;
//            this.prevPosY += 1.2;
//            this.lastTickPosY += 1.2;
//        }
//
//        this.motionX = this.posX - this.prevPosX;
//        this.motionY = this.posY - this.prevPosY;
//        this.motionZ = this.posZ - this.prevPosZ;
//    }


}
