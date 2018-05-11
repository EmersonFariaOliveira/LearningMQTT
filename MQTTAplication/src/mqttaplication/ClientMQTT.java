/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mqttaplication;

import java.io.IOException;
import static java.rmi.server.LogStream.log;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Emerson
 */
public class ClientMQTT implements MqttCallback {

    //Variáveis de configuração para conexão e cominicação com o broker
    private String content = "teste";
    private int qos = 2;
    private String broker;
    private String clientId;
    private String username;
    private String password;
    private MqttClient sampleClient;

    private MemoryPersistence persistence = new MemoryPersistence();

    public ClientMQTT(String broker, String clientId, String username, String password) {
        this.broker = broker;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
    }

    public void BrokerConnection() {
        try {
            //comunicação MQTT
            sampleClient = new MqttClient(broker, clientId, persistence);

            //Configurando a conexão com o broker
            MqttConnectOptions connOpts = new MqttConnectOptions();
            
            connOpts.setCleanSession(true);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            System.out.println("Connecting to broker: " + broker);

            //Conecta com o broker
            sampleClient.connect(connOpts);
            System.out.println("Connected");

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void TopicPublish(String topic, String messageMQTT) {
        try {
            //Publicando uma mensagem
            System.out.println("Publishing on topic: " + topic);
            System.out.println("Message: " + messageMQTT);
            //Configurando o conteudo da mensagem e convertendo em bytes
            MqttMessage message = new MqttMessage();
            message.setQos(qos);
            message.setPayload(messageMQTT.getBytes());
            //Publicação no topico especifico
            sampleClient.publish(topic, message);
            System.out.println("Message published");

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void BrokerDisconnection() {
        try {
            //Desconecta do broker
            sampleClient.disconnect();
            System.out.println("Disconnected");

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }

    }

    public void subscribe(String topicName, int qos) throws MqttException {

        
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

        // Disconnect the client from the server
        //sampleClient.disconnect();
        log("Disconnected");
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
