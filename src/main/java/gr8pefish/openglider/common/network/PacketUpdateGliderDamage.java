package gr8pefish.openglider.common.network;

import gr8pefish.openglider.api.item.IGlider;
import gr8pefish.openglider.api.helper.GliderHelper;
import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.helper.OpenGliderPlayerHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateGliderDamage implements IMessage{

    public PacketUpdateGliderDamage() {} //default constructor is necessary

    @Override
    public void fromBytes(ByteBuf buf){
    }

    @Override
    public void toBytes(ByteBuf buf){
    }

    public static class Handler implements IMessageHandler<PacketUpdateGliderDamage, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateGliderDamage message, MessageContext ctx) {

            //have to use threading system since 1.8
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = OpenGlider.proxy.getClientPlayer();
                if (player != null) {
                    ItemStack glider = OpenGliderPlayerHelper.getGlider(player);
                    if (glider != null) {
                        glider.damageItem(ConfigHandler.durabilityPerUse, player);
                        if (((IGlider)glider.getItem()).isBroken(glider)) { //broken item
                            GliderHelper.setIsGliderDeployed(player, false);
                        }
                    }
                }
            });
            //no return message
            return null;
        }

    }

}
