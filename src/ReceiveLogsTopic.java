import com.rabbitmq.client.*;
import enums.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsTopic {
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = MyConnection.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constants.EXCHANGE_NAME_TOPIC, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
            System.exit(1);
        }

        for (String bindingKey : args) {
            channel.queueBind(queueName, Constants.EXCHANGE_NAME_TOPIC, bindingKey);
        }

        System.out.println("[x]Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String s, Envelope envelope,
                                       AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[x]Received '"+envelope.getRoutingKey()+ "'"+message+"'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
