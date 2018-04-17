import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send
{
    public final static String QUEUE_NAME = "FirstQueue";

    public static void main(String args[])
    {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = MyConnection.getConnection();
            if(connection != null)
            channel = connection.createChannel();

            if (channel != null) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String message = "Hello World!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.print("[x] Sent '" + message + "'");
            }
            channel.close();
            connection.close();
        }
        catch (IOException | TimeoutException e) {
            System.out.println("Error in Send : " + e.getMessage());
        }
    }
}
