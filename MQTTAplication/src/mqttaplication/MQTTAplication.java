/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mqttaplication;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Emerson
 */
public class MQTTAplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MqttException {

        String broker = "tcp://m13.cloudmqtt.com:18617";
        String clientId = "device1";
        String userName = "erarxfqi";
        String password = "tGirIYP7ESyW";

        String topic = "MQTT/Teste";
        String menssage = "Ola Teste de Publicacao";

        ClientMQTT cliente = new ClientMQTT(broker, clientId, userName, password);
        cliente.BrokerConnection();
        
        Subscribe subscribeTopic = new Subscribe(cliente.getSampleClient(), cliente.getBroker(), topic);
        Thread t1 = new Thread(subscribeTopic);
        
        t1.start();
        cliente.TopicPublish(topic, menssage);
        //cliente.BrokerDisconnection();

    }

}
