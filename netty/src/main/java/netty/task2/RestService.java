package netty.task2;


import io.netty.bootstrap.Bootstrap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class RestService {

    private final static String REQUEST_GET = "GET /posts HTTP/1.1\r\n" +
            "Host: http://localhost:8080/accounts\r\n" +
            "Accept: */*\r\n" +
            "Connection: close\r\n\r\n";


//    private final static String REQUEST_POST = "POST /api/accounts HTTP/1.1\r\n" +
//    "Content-Type: application/json\n" +
//    "User-Agent: PostmanRuntime/7.26.8\n" +
//    "Accept: */*\n" +
//    "Postman-Token: ac0058e2-1398-418e-a2c8-2164dad39a7d\n" +
//    "Host: localhost:8080\n" +
//    "Accept-Encoding: gzip, deflate, br\n" +
//    "Connection: keep-alive\n" +
//    "Content-Length: 171\n" +
//            "{\n" +
//            "    \"firstName\": \"имя\", \n" +
//            "    \"lastName\": \"фамилия\", \n" +
//            "    \"patronymic\": \"отчество\",\n" +
//            "    \"login\": \"login0004\", \n" +
//            "    \"password\": \"пароль123456\" \n" +
//            "}";

    private final static String REQUEST_POST =
            "GET /api/debug/settings HTTP/1.1 " +
            "Content-Type: application/json " +
            "User-Agent: PostmanRuntime/7.26.8 " +
            "Accept: */* " +
            "Postman-Token: 522f2032-8c58-46f0-9476-0884dc4ef6d1 " +
            "Host: localhost:8080 " +
            "Accept-Encoding: gzip, deflate, br " +
            "Connection: keep-alive " +
            "Content-Length: 0";

    /*
    POST /api/accounts HTTP/1.1
Content-Type: application/json
User-Agent: PostmanRuntime/7.26.8
Accept:
    Postman-Token: 5562d504-8c0e-459b-8188-8f9b8e698bb5
    Host: localhost:8080
    Accept-Encoding: gzip, deflate, br
    Connection: keep-alive
    Content-Length: 171

    {
        "firstName": "имя",
            "lastName": "фамилия",
            "patronymic": "отчество",
            "login": "login0004",
            "password": "пароль123456"
    }
     */


    public void process(ChannelHandlerContext ctx) throws Exception {
        Channel inboundChannel = ctx.channel();

        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop())
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("localhost", 8080))
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    public void initChannel(Channel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpResponseDecoder());
                        p.addLast(new HttpObjectAggregator(100 * 1024));
                        p.addLast(new ClientHandler(ctx));
                    }
                });
        b.connect();
    }


    private static class ClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

        private ChannelHandlerContext serverctx;

        public ClientHandler(ChannelHandlerContext serverctx) {
            this.serverctx = serverctx;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            final String text = "{\n" +
                    "        \"firstName\": \"имя\",\n" +
                    "            \"lastName\": \"фамилия\",\n" +
                    "            \"patronymic\": \"отчество\",\n" +
                    "            \"login\": \"login0004\",\n" +
                    "            \"password\": \"пароль123456\"\n" +
                    "    }";
            final ByteBuf content = Unpooled.copiedBuffer(text.getBytes());
            FullHttpRequest req = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, "/api/accounts", content, true
            );
            req.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
            req.headers().set(CONTENT_LENGTH, req.content().readableBytes());
            ctx.writeAndFlush(req);
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, FullHttpResponse in) {

            String result = in.content().toString(CharsetUtil.UTF_8);
            System.out.println(result);

            FullHttpResponse response = new DefaultFullHttpResponse(
                    HTTP_1_1,
                    OK,
                    Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
            response.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

            serverctx.writeAndFlush(response)
                    .addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }




}
