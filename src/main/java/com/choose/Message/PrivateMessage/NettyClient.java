package com.choose.Message.PrivateMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 定义NettyClient 
 * 
 * @author 周化益
 *
 */
public class NettyClient {
	public void connect(String remoteServer, int port) throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChildChannelHandler());
			ChannelFuture f = b.connect(remoteServer,port);
			System.out.println("Netty time Client connected at port " + port);
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public static class ChildChannelHandler extends ChannelInitializer<Channel> {
		@Override
		public void initChannel(Channel ch) throws Exception {
			// -8表示lengthAdjustment，让解码器从0开始截取字节，并且包含消息头
			ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
			ch.pipeline().addLast(new NettyMessageEncoder());
			ch.pipeline().addLast(new LoginAuthReqHandler());
			
		}

	}
	
	public static void main(String[] args){
		try {
			new NettyClient().connect("192.168.0.11", 9080);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}