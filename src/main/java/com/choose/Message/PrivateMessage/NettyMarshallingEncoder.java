package com.choose.Message.PrivateMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

/**
 * 扩展MarshallingEncoder 和 MarshallingDecoder，将protected方法编程public可以调用 
 * 
 * @author 周化益
 *
 */
public class NettyMarshallingEncoder extends MarshallingEncoder {

	public NettyMarshallingEncoder(MarshallerProvider provider) {
		super(provider);
	}

	public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception{
		super.encode(ctx, msg, out);
	}
	
}