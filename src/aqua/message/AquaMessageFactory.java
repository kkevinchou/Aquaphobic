package aqua.message;

import java.net.DatagramPacket;

import knetwork.message.MessageBody;
import knetwork.message.MessageFactory;
import knetwork.message.messages.Message;

public class AquaMessageFactory extends MessageFactory {
	@Override
	protected Message buildMessage(DatagramPacket packet, int intMessageType, MessageBody body) {
		Message message = null;
		
		if (intMessageType == ServerUpdateMessage.MESSAGE_TYPE) {
			message = ServerUpdateMessage.constructFromMessageBody(body);
		} else if (intMessageType == PlayerActionMessage.MESSAGE_TYPE) {
			message = PlayerActionMessage.constructFromMessageBody(body);
		}
		
		return message;
	}
}
