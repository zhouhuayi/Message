package com.choose.Message.PrivateMessage;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 定义NettyServer 
 * 
 * @author 周化益
 *
 */
public class NettyServer {
	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
//			.option(ChannelOption.SO_BACKLOG, 100)
			.option(ChannelOption.TCP_NODELAY, true)
			.childHandler(new ChildChannelHandler());
			
			ChannelFuture f = b.bind(port);
			System.out.println("Netty time Server started at port " + port);
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static class ChildChannelHandler extends ChannelInitializer<Channel> {

	
		@Override
		protected void initChannel(Channel ch) throws Exception {
			// TODO Auto-generated method stub
			ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
			ch.pipeline().addLast(new NettyMessageEncoder());
			ch.pipeline().addLast(new LoginAuthRespHandler());
		}

	}
	
	public static void main(String[] args){
		try {
			new NettyServer().bind(9080);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}