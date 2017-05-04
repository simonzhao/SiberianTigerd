package com.matrix001.siberian_tiger.siberiantigerd.handler;

import com.matrix001.siberian_tiger.proto.Siberiantiger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by zhaoyn on 2017/4/28.
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LogManager.getFormatterLogger();
    
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        Siberiantiger.SiberianTigerMessage req = (Siberiantiger.SiberianTigerMessage) msg;
        
        if ( req.hasLoginRQ() ){
            Long start = System.currentTimeMillis();
    
            String requestId = req.getRequestId();
            Siberiantiger.LoginMsgRequest message = req.getLoginRQ();
            
            String loginName = message.getLoginName();
            String passWd = message.getPassWord();
            String appId = message.getAppId();
            
            logger.debug( "RequestId:[%s] LoginName:[%s] PassWord:[%s] AppId:[%s]", requestId, loginName, passWd, appId );
    
            String userId = "60164e75-e884-4907-bfd5-245ee92afbd1";
            String accessToken = "4e44afd2-fbe0-4838-866b-7686dd9fecbf";
    
            Siberiantiger.LoginMsgResponse.Builder params = Siberiantiger.LoginMsgResponse.newBuilder();
            params.setUserId( userId );
            params.setAccessToken( accessToken );
            
            int paytime = (int)(System.currentTimeMillis() - start);
            
            Siberiantiger.SiberianTigerMessage.Builder builder = Siberiantiger.SiberianTigerMessage.newBuilder();
            builder.setRequestId( requestId );
            builder.setPayTime( paytime );
            builder.setLoginRS( params );
            
            ctx.writeAndFlush( builder );
            logger.info( "RequestId:[%s] UserId:[%s] AccessToken:[%s] PayTime:[%d ms]"
                    , requestId
                    , userId
                    , accessToken
                    , paytime
            );
        } else {
          ctx.fireChannelRead( msg );
        }
    }
}
