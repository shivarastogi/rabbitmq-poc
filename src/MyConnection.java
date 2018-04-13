import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MyConnection
{
    public static Connection connection = null;
    public static Connection getConnection() throws IOException, TimeoutException
    {
        ConnectionFactory factory =  null;
        try {
            factory = new ConnectionFactory();
            factory.setHost("192.168.99.100");
            connection = factory.newConnection();
        }
        catch(IOException | TimeoutException e)
        {
            System.err.print(e);
        }
        return connection;
    }
}
