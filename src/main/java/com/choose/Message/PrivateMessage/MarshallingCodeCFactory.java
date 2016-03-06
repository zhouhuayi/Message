package com.choose.Message.PrivateMessage;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import com.choose.Message.PrivateMessage.NettyMarshallingDecoder;
import com.choose.Message.PrivateMessage.NettyMarshallingEncoder;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * 定义MarshallingCodeCFactory工厂类来获取JBoss Marshalling 类 
 * 
 * @author 周化益
 *
 */
public class MarshallingCodeCFactory {
	public static NettyMarshallingDecoder buildMarshallingDecoder(){
		MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		NettyMarshallingDecoder decoder = new NettyMarshallingDecoder(provider, 1024);
		return decoder;
	}
	
	public static NettyMarshallingEncoder buildMarshallingEncoder(){
		MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
		NettyMarshallingEncoder encoder = new NettyMarshallingEncoder(provider);
		return encoder;
	}
}