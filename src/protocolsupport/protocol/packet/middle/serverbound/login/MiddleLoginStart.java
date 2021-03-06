package protocolsupport.protocol.packet.middle.serverbound.login;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleLoginStart extends ServerBoundMiddlePacket {

	public MiddleLoginStart(ConnectionImpl connection) {
		super(connection);
	}

	protected String name;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_LOGIN_START);
		StringSerializer.writeVarIntUTF8String(creator, name);
		return RecyclableSingletonList.create(creator);
	}

}
