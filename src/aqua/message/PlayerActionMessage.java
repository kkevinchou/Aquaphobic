package aqua.message;

import java.nio.ByteBuffer;

import knetwork.message.MessageBody;
import knetwork.message.messages.Message;

public class PlayerActionMessage extends Message {
	public static final int MESSAGE_TYPE = 11;
	
	int left;
	int right;
	int jump;
	int launch;
	int mouseX;
	int mouseY;
	
	public PlayerActionMessage(boolean left, boolean right, boolean jump, boolean launch, int mouseX, int mouseY) {
		super(MESSAGE_TYPE);
		
		this.left = this.right = this.jump = this.launch = 0;
		
		if (left) {
			this.left = 1;
		}
		
		if (right) {
			this.right = 1;
		}
		
		if (jump) {
			this.jump = 1;
		}
		
		if (launch) {
			this.launch = 1;
		}

		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	private PlayerActionMessage(int left, int right, int jump, int launch, int mouseX, int mouseY) {
		super(MESSAGE_TYPE);
		this.left = left;
		this.right = right;
		this.jump = jump;
		this.launch = launch;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	public boolean isLeft() {
		return left == 1;
	}
	
	public boolean isRight() {
		return right == 1;
	}
	
	public boolean isJump() {
		return jump == 1;
	}
	
	public boolean isLaunch() {
		return launch == 1;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}

	@Override
	protected byte[] generateMessageBodyBytes() {
		int totalBytes = 6 * 4;
		ByteBuffer buffer = ByteBuffer.allocate(totalBytes);
		
		buffer.putInt(left);
		buffer.putInt(right);
		buffer.putInt(jump);
		buffer.putInt(launch);
		buffer.putInt(mouseX);
		buffer.putInt(mouseY);
		
		return buffer.array();
	}
	
	public static Message constructFromMessageBody(MessageBody body) {
		ByteBuffer buffer = body.getByteBuffer();
	
		int left = buffer.getInt();
		int right = buffer.getInt();
		int jump = buffer.getInt();
		int launch = buffer.getInt();
		int mouseX = buffer.getInt();
		int mouseY = buffer.getInt();
		
		return new PlayerActionMessage(left, right, jump, launch, mouseX, mouseY);
	}
	
}
