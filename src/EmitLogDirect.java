import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dao.Common;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String args[]) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.99.100");
//        try(Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel()) {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String severity = Common.getSeverity(args);
            String message = Common.getMessage(args);

            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println("[x] Sent '"+severity+ "':'"+message+"'");
        //}
        }

/*

private static String getMessage(String[] args) {
if(args.length < 2)
return "Hello World!";
return joinStrings(args, " ", 1);
}

private static String joinStrings(String[] args, String delimiter, int startIndex) {
int length = args.length;
if(length ==0)
return "";
if(length < startIndex)
return "";
//StringBuilder words = new StringBuilder(args[startIndex]);
StringJoiner words = new StringJoiner(delimiter);
for (int i = startIndex; i < length; i++) {
words.add(args[i]);
}
return words.toString();
}

private static String getSeverity(String[] args) {
if(args.length < 1)
return "info";
return args[0];
}
*/

}
