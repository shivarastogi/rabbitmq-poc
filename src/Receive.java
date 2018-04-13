import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive
{
    public final static String QUEUE_NAME = "hello";

    public static void main(String args[])
    {
        Connection connection = null;
        try{
            connection = MyConnection.getConnection();

            if(connection != null) {
                Channel channel = connection.createChannel();

                if (channel != null) {
                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    System.out.println("[*] Waiting for messages. To exit press CTRL+C");

                    Consumer consumer = new DefaultConsumer(channel){
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        {
                            String message = new String(body);
                            System.out.println("[*] Received '"+message+"'");
                        }
                    };
                    channel.basicConsume(QUEUE_NAME, true, consumer);
                }
            }
        }
        catch(IOException | TimeoutException e)
        {
            System.out.println("Error in Receive "+e);
        }
    }
}
