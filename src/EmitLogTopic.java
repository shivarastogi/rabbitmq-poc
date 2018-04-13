import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import dao.Common;
import enums.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLogTopic {
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = null;
        //Channel channel = null;
        try {
            connection = MyConnection.getConnection();
            try(Channel channel = connection.createChannel();) {

                channel.exchangeDeclare(Constants.EXCHANGE_NAME_TOPIC, BuiltinExchangeType.TOPIC);

                String routingKey = Common.getRouting(args);
                String message = Common.getMessage(args);

                channel.basicPublish(Constants.EXCHANGE_NAME_TOPIC, routingKey, null, message.getBytes("UTF-8"));
                System.out.println("[x]Sent '" + routingKey + "':'" + message + "'");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(connection != null)
                try{
                    connection.close();
                }
                catch(Exception ignore){}
        }
    }
}
