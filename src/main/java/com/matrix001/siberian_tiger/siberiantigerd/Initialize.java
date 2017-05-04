package com.matrix001.siberian_tiger.siberiantigerd;

import com.matrix001.siberian_tiger.proto.Siberiantiger;
import com.matrix001.siberian_tiger.siberiantigerd.handler.LoginHandler;
import io.netty.channel.ChannelInitializer;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by zhaoyn on 2017/4/28.
 */
public class Initialize extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
    
        p.addLast(new ProtobufVarint32FrameDecoder());
        p.addLast(new ProtobufDecoder( Siberiantiger.SiberianTigerMessage.getDefaultInstance() ));
        p.addLast(new ProtobufVarint32LengthFieldPrepender());
        p.addLast(new ProtobufEncoder());
        
        p.addLast( new LoginHandler() ); //用户登录
    }
}
