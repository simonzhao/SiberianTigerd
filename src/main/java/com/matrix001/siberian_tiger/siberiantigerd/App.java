package com.matrix001.siberian_tiger.siberiantigerd;

import com.matrix.framework.pool.JobPool;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Hello world!
 *
 */
public class App {
    private int port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    
    public App(int port) {
        this.port = port;
    }
    
    public void init(){
        new JobPool().init( 5 );
        bossGroup = new NioEventLoopGroup( 1 );
        workerGroup = new NioEventLoopGroup();
    }
    
    public void start() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option( ChannelOption.SO_BACKLOG, 1024 );
            b.group( bossGroup, workerGroup )
                    .channel( NioServerSocketChannel.class )
                    .handler( new LoggingHandler(( LogLevel.INFO )))
                    .childHandler( new Initialize() );
            Channel ch = b.bind( this.port ).sync().channel();
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main( String[] args ) {
        App app = new App(40001);
        app.init();
        app.start();
    }
}
