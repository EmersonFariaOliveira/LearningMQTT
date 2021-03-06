/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mqttaplication;

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

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

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
            System.out.println(ANSI_GREEN + "Connected!!" + ANSI_RESET);
            System.out.println("---------------------------------------------");

            //Trata as callbacks
            sampleClient.setCallback(this);

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

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost: " + cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Receiving message");
        System.out.println("From topic: " + topic);
        System.out.println("Message: " + message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Message published");
    }

    public MqttClient getSampleClient() {
        return sampleClient;
    }

    public void setSampleClient(MqttClient sampleClient) {
        this.sampleClient = sampleClient;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }
}
