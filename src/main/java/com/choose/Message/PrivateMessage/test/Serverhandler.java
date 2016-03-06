package com.choose.Message.PrivateMessage.test;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * 服务器handler
 * @author xwalker
 */
public class Serverhandler extends ChannelHandlerAdapter {
	int counter=0;
	private static final String MESSAGE="It greatly simplifies and streamlines network programming such as TCP and UDP socket server.";
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("接收客户端msg:["+msg+"]");
		ByteBuf echo=Unpooled.copiedBuffer(MESSAGE.getBytes());
		ctx.writeAndFlush(echo);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}