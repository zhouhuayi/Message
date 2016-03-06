package com.choose.Message.PrivateMessage.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
/**
 * 客户端 
 * @author xwalker
 *
 */
public class Client {
	/**
	 * 链接服务器
	 * @param port
	 * @param host
	 * @throws Exception
	 */
	public void connect(int port,String host)throws Exception{
		//网络事件处理线程组
		EventLoopGroup group=new NioEventLoopGroup();
		try{
		//配置客户端启动类
		Bootstrap b=new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)//设置封包 使用一次大数据的写操作，而不是多次小数据的写操作
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new FixedLengthFrameDecoder(30));//设置定长解码器
				ch.pipeline().addLast(new StringDecoder());//设置字符串解码器
				
//				ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
//				ch.pipeline().addLast(new NettyMessageEncoder());
				
				ch.pipeline().addLast(new ClientHandler());//设置客户端网络IO处理器
			}
		});
		//连接服务器 同步等待成功
		ChannelFuture f=b.connect(host,port).sync();
		//同步等待客户端通道关闭
		f.channel().closeFuture().sync();
		}finally{
			//释放线程组资源
			group.shutdownGracefully();
		}
	}
	public static void main(String[] args) throws Exception {
		int port=8000;
		new Client().connect(port, "127.0.0.1");

	}

}