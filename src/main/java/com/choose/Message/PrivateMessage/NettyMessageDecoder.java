package com.choose.Message.PrivateMessage;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 定义NettyMessageDecoder类，注意设置LengthFieldBasedFrameDecoder的几个重要参数，直接影响到解码的结果 
 * 
 * @author 周化益
 *
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{

	private NettyMarshallingDecoder marshallingDecoder;
	
	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength,int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
		marshallingDecoder = MarshallingCodeCFactory.buildMarshallingDecoder();
	}
	
	public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf)super.decode(ctx, in);
		System.out.println("返回字符串：："+in);
		if(frame == null) {
			return null;
		}
		
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(frame.readInt());
		header.setLength(frame.readInt());
		header.setSessionID(frame.readLong());
		header.setType(frame.readByte());
		header.setPriority(frame.readByte());
		
		int size = frame.readInt();
		if(size > 0){
			Map<String, Object> attach = new HashMap<String, Object>(size);
			int keySize = 0;
			byte[] keyArray = null;
			String key = null;
			for(int i=0; i<size; i++){
				keySize = frame.readInt();
				keyArray = new byte[keySize];
				in.readBytes(keyArray);
				key = new String(keyArray, "UTF-8");
				attach.put(key, marshallingDecoder.decode(ctx, frame));
			}
			key = null;
			keyArray = null;
			header.setAttachment(attach);
		} 
		
		if(frame.readableBytes() > 0){
			message.setBody(marshallingDecoder.decode(ctx, frame));
		}
		message.setHeader(header);
		
		return message;
	}
}