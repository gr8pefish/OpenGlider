package gr8pefish.openglider.common.network;

import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientGliding implements IMessage{

    //the data sent
    private byte isGliding;

    public final static byte IS_GLIDING = 0;
    public final static byte IS_NOT_GLIDING = 1;

    public PacketClientGliding() {} //default constructor is necessary

    public PacketClientGliding(byte gliding) {
        this.isGliding = gliding;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        isGliding = (byte) ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeVarShort(buf, isGliding);
    }

    public static class Handler implements IMessageHandler<PacketClientGliding, IMessage> {

        @Override
        public IMessage onMessage(PacketClientGliding message, MessageContext ctx) {

            //have to use threading system since 1.8
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = OpenGlider.proxy.getClientPlayer();
                if (player != null) {
                    OpenGliderCapabilities.setIsGliding(player, message.isGliding == IS_GLIDING);
                }
            });

            return null; //no return message
        }
    }
}
