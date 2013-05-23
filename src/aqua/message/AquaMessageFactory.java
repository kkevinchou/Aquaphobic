package aqua.message;

import java.net.DatagramPacket;

import knetwork.common.Logger;
import knetwork.message.MessageBody;
import knetwork.message.MessageFactory;
import knetwork.message.messages.Message;

public class AquaMessageFactory extends MessageFactory {
	@Override
	protected Message buildMessageBody(DatagramPacket packet, int intMessageType, MessageBody body) {
		Logger.log("Received Type : " + intMessageType);
		
		Message message = null;
		
		if (intMessageType == ServerUpdateMessage.MESSAGE_TYPE) {
			message = ServerUpdateMessage.constructFromMessageBody(body);
		}
		
		return message;
	}
}
