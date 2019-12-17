package rabbimq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class SendMessageJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri("amqp://guest:guest@localhost");
            factory.setConnectionTimeout(300000);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("my-queue", true, false, false, null);

            String message = "Hello bad boy";

            channel.basicPublish("", "my-queue", null, message.getBytes());
            System.out.println("Published message: " + message);
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException | IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}