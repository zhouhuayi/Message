package com.choose.Message.PrivateMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 定义LoginAuthRespHandler类，服务器端响应Login的业务ChannelHandler 
 * 
 * @author 周化益
 *
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {

//	@Override
//	public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("接受到客户端发送过来的消息为：" + msg);
//		NettyMessage message = (NettyMessage)msg;
//		if(message.getHeader() != null && message.getHeader().getType() == (byte)1){
//			System.out.println("Login is OK");
//			String body = (String)message.getBody();
//			System.out.println("Recevied message body from client is " + body);
//		}
//		ctx.writeAndFlush(buildLoginResponse((byte)3));
//	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("接受到客户端发送过来的消息为：" + msg);
		NettyMessage message = (NettyMessage)msg;
		if(message.getHeader() != null && message.getHeader().getType() == (byte)1){
			System.out.println("Login is OK");
			String body = (String)message.getBody();
			System.out.println("Recevied message body from client is " + body);
		}
		System.out.println(1111);
		ctx.writeAndFlush(buildLoginResponse((byte)3));
	};
	
	/**
     * 加入时的系统消息
     * 
     * @author 周化益
	 * @param ctx 渠道头部文本
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
    	System.out.println("连接ID为：" + ctx.channel().id() + " 连接服务端");
    }

	private NettyMessage buildLoginResponse(byte result) {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType((byte)2);
		message.setHeader(header);
		message.setBody(result);
		return message;
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}
}