import com.rabbitmq.client.*;
import enums.Constants;

import java.io.IOException;

public class ReceiveLogsDirect {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.99.100");
//        try(Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel()){

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
            channel.exchangeDeclare(Constants.EXCHANGE_NAME_DIRECT, BuiltinExchangeType.DIRECT);
            String queueName = channel.queueDeclare().getQueue();

            if(args.length < 1){
                System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
                System.exit(1);
            }

            for(String severity : args){
                channel.queueBind(queueName, Constants.EXCHANGE_NAME_DIRECT, severity);
            }

            System.out.println(" [x] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel){
              @Override
              public void handleDelivery(String consumerTag, Envelope envelope,
                                         AMQP.BasicProperties properties, byte[] body) throws IOException {
                  String message = new String(body, "UTF-8");
                  System.out.println(" [x] Received '"+envelope.getRoutingKey()+ "':'"+message+"'");
              }
            };
            channel.basicConsume(queueName,true,consumer);
//        }
    }
}
