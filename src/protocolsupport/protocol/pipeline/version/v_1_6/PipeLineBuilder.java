package protocolsupport.protocol.pipeline.version.v_1_6;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.handler.common.LegacyHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.NoOpFrameDecoder;
import protocolsupport.protocol.pipeline.common.NoOpFrameEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		networkmanager.setPacketListener(new LegacyHandshakeListener(networkmanager));
		ChannelHandlers.getSplitter(pipeline).setRealSplitter(new NoOpFrameDecoder());
		ChannelHandlers.getPrepender(pipeline).setRealPrepender(new NoOpFrameEncoder());
		NetworkDataCache sharedstorage = new NetworkDataCache();
		ChannelHandlers.getDecoder(pipeline).setRealDecoder(new PacketDecoder(connection, sharedstorage));
		ChannelHandlers.getEncoder(pipeline).setRealEncoder(new PacketEncoder(connection, sharedstorage));
	}

}
