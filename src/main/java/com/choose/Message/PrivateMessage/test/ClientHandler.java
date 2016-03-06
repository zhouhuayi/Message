package com.choose.Message.PrivateMessage.test;
import com.choose.Message.PrivateMessage.Header;
import com.choose.Message.PrivateMessage.NettyMessage;
import com.choose.Message.util.SerializeUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * 客户端处理器
 * @author xwalker
 *
 */
public class ClientHandler extends ChannelHandlerAdapter {
	private static final String MESSAGE="Netty is a NIO client server framework which enables quick and easy development of network applications such as protocol servers and clients.";
	public ClientHandler(){}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//			ctx.writeAndFlush(Unpooled.copiedBuffer(MESSAGE.getBytes()));
//		ByteBuf message = Unpooled.copiedBuffer(SerializeUtil.serialize(buildLoginReq()));
		ctx.writeAndFlush(buildLoginReq());
	}
	
	private NettyMessage buildLoginReq() {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType((byte)1);
		message.setHeader(header);
		message.setBody("It is request");
		return message;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("接收服务器响应msg:["+msg+"]");
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}