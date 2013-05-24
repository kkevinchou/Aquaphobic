package aqua.message;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import aqua.entity.BaseEntity;
import aqua.entity.PhysEntity;

import knetwork.message.MessageBody;
import knetwork.message.messages.Message;

public class ServerUpdateMessage extends Message {
	private List<BaseEntity> entities;
	private List<Shape> shapes;
	
	private static final int RECT_TYPE = 1;
	private static final int RECT_BYTE_SIZE = 5 * 4;
	private static final int CIRCLE_TYPE = 2;
	private static final int CIRCLE_BYTE_SIZE = 4 * 4;
	private static final int LINE_TYPE = 3;
	private static final int LINE_BYTE_SIZE = 5 * 4;
	
	public static final int MESSAGE_TYPE = 10;
	
	public ServerUpdateMessage(List<BaseEntity> entities) {
		super(MESSAGE_TYPE);
		this.entities = entities;
		shapes = new ArrayList<Shape>();
	}
	
	private ServerUpdateMessage() {
		super(MESSAGE_TYPE);
	}
	
	public List<Shape> getShapes() {
		return shapes;
	}
	
	public static Message constructFromMessageBody(MessageBody body) {
		ByteBuffer buffer = body.getByteBuffer();
		int numShapes = buffer.getInt();
		
		List<Shape> shapes = new ArrayList<Shape>();
		
		for (int i = 0; i < numShapes; i++) {
			Shape shape = constructShapeFromByteBuffer(buffer);
			shapes.add(shape);
		}
		
		ServerUpdateMessage message = new ServerUpdateMessage();
		message.shapes = shapes;
		return message;
	}
	
	private static Shape constructShapeFromByteBuffer(ByteBuffer buffer) {
		int type = buffer.getInt();
		
		if (type == RECT_TYPE) {
			float x = buffer.getFloat();
			float y = buffer.getFloat();
			float width = buffer.getFloat();
			float height = buffer.getFloat();
			
			return new Rectangle(x, y, width, height);
		} else if (type == CIRCLE_TYPE) {
			float x = buffer.getFloat();
			float y = buffer.getFloat();
			float radius = buffer.getFloat();
			
			return new Circle(x, y, radius);
		} else if (type == LINE_TYPE) {
			float x1 = buffer.getFloat();
			float y1 = buffer.getFloat();
			float x2 = buffer.getFloat();
			float y2 = buffer.getFloat();
			
			return new Line(x1, y1, x2, y2);
		}

		return null;
	}

	@Override
	protected byte[] generateMessageBodyBytes() {
		int totalBytes = 0;
		totalBytes += 4; // Bytes to store number of elemnts;
		
		for (BaseEntity entity : entities) {
			Shape shape = ((PhysEntity)entity).getCollisionShape();
			int numRequiredBytesForShape = calculateRequiredBytesForShape(shape);
			totalBytes += numRequiredBytesForShape;
		}
		
		ByteBuffer buffer = ByteBuffer.allocate(totalBytes).putInt(entities.size());
		
		for (BaseEntity entity : entities) {
			Shape shape = ((PhysEntity)entity).getCollisionShape();
			byte[] shapeBytes = convertShapeToBytes(shape);
			buffer.put(shapeBytes);
		}
		
		return buffer.array();
	}
	
	private int calculateRequiredBytesForShape(Shape shape) {
		if (shape instanceof Rectangle) {
			return RECT_BYTE_SIZE;
		} else if (shape instanceof Circle) {
			return CIRCLE_BYTE_SIZE;
		} else if (shape instanceof Line) {
			return LINE_BYTE_SIZE;
		}
		
		return 0;
	}
	
	private byte[] convertShapeToBytes(Shape shape) {
		byte[] result;
		
		if (shape instanceof Rectangle) {
			Rectangle rectangle = (Rectangle)shape;
			
			ByteBuffer buffer = ByteBuffer.allocate(RECT_BYTE_SIZE);
			buffer.putInt(RECT_TYPE);
			buffer.putFloat(rectangle.getX());
			buffer.putFloat(rectangle.getY());
			buffer.putFloat(rectangle.getWidth());
			buffer.putFloat(rectangle.getHeight());
			
			result = buffer.array();
		} else if (shape instanceof Circle) {
			Circle circle = (Circle)shape;
			
			ByteBuffer buffer = ByteBuffer.allocate(CIRCLE_BYTE_SIZE);
			buffer.putInt(CIRCLE_TYPE);
			buffer.putFloat(circle.getCenterX());
			buffer.putFloat(circle.getCenterY());
			buffer.putFloat(circle.getRadius());
			
			result = buffer.array();
		} else if (shape instanceof Line) {
			Line line = (Line)shape;
			
			ByteBuffer buffer = ByteBuffer.allocate(LINE_BYTE_SIZE);
			buffer.putInt(LINE_TYPE);
			buffer.putFloat(line.getX1());
			buffer.putFloat(line.getY1());
			buffer.putFloat(line.getX2());
			buffer.putFloat(line.getY2());
			
			result = buffer.array();
		} else {
			result = new byte[0];
		}
		
		return result;
	}
}
