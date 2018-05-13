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
public class ClientMQTT {

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

            sampleClient.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {
                }
                
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("topico: "+topic);
                    System.out.println("Message: " + message.toString());
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            //Conecta com o broker
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            //sampleClient.subscribe("MQTT/Teste2");

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
