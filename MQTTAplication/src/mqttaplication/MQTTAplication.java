/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mqttaplication;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Emerson
 */
public class MQTTAplication {

    /**
     * @param args the command line arguments
     * @throws org.eclipse.paho.client.mqttv3.MqttException
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
        
        cliente.getSampleClient().subscribe(topic);
        cliente.TopicPublish(topic, menssage);

        //cliente.BrokerDisconnection();

    }

}
