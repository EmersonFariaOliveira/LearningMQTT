/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mqttaplication;

import java.io.IOException;
import static java.rmi.server.LogStream.log;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Emerson
 */
public class Subscribe implements Runnable {

    private String broker;
    private String topicName;
    private int qos;
    private MqttClient sampleClient;

    public Subscribe(MqttClient sampleClient, String broker, String topicName) {
        this.sampleClient = sampleClient;
        this.broker = broker;
        this.topicName = topicName;
    }

    @Override
    public void run() {
        try {
            // Connect to the MQTT server
            log("Connected to " + broker + " with client ID " + sampleClient.getClientId());

            // Subscribe to the requested topic
            // The QoS specified is the maximum level that messages will be sent to the client at.
            // For instance if QoS 1 is specified, any messages originally published at QoS 2 will
            // be downgraded to 1 when delivering to the client but messages published at 1 and 0
            // will be received at the same level they were published at.
            log("Subscribing to topic \"" + topicName + "\" qos " + qos);
            sampleClient.subscribe(topicName, qos);

            // Continue waiting for messages until the Enter is pressed
            log("Press <Enter> to exit");
            try {
                System.in.read();
            } catch (IOException e) {
                //If we can't read we'll just exit
            }

            
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }

    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

}
