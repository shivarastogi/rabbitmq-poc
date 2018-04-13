import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String args[]) throws IOException, TimeoutException {
        //        try (Connection connection = MyConnection.getConnection();
//             Channel channel = connection.createChannel()) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.99.100");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "" );

            System.out.println(" [x] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelop,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException
                {

                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };

            channel.basicConsume(queueName, true, consumer);
        }

   // }
}
