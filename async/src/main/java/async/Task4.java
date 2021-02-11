package async;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {

    private static final Gson gson = new Gson();
    private static final String HTTP_RESPONSE_BEGIN = "HTTP/1.1 200 OK\n" +
            "Content-Length: 50\n" +
            "Content-Type: text/html\n" +
            "Connection: Closed\n\n" +
            "<html>\n" +
            "<body>\n" +
            "<h1>Sum of two numbers = ";

    private static String value;

    private static final String HTTP_RESPONSE_END =
            "</h1>\n" +
            "</body>\n" +
            "</html>";

    private static Map<SelectionKey, String> keyMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 8080));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT, null);

        while (true) {

            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    log("Connection Accepted: " + clientChannel.getLocalAddress() + "\n");

                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int n = client.read(buffer);
                    if (n == -1) {
                        client.close();
                    }
                    String result = new String(buffer.array()).trim();
                    Matcher m = Pattern.compile("\\{[\\s\\S]+}").matcher(result);
                    if(m.find()) {
//                        JsonObject jsonObject = gson.fromJson(m.group(0), JsonObject.class);
//                        value = String.valueOf(jsonObject.getX() + jsonObject.getY());
                        log("Принял сообщение\n" + result);
//                        keyMap.put(key, value);
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                } else if (key.isWritable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    String answer = HTTP_RESPONSE_BEGIN + keyMap.remove(key) + HTTP_RESPONSE_END;
                    ByteBuffer writeBuffer = StandardCharsets.UTF_8.encode(answer);
                    client.write(writeBuffer);
                    log("Пишу сообщение\n" + answer);
                    if (!writeBuffer.hasRemaining()) {
                        writeBuffer.compact();
                        key.interestOps(SelectionKey.OP_READ);
                    }
                    client.close();
                }
                iterator.remove();
            }
        }
    }

    private static void log(String str) {
        System.out.println(str);
    }
}
