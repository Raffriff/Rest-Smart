package client;

import clientui.LightingUI;
import com.google.gson.Gson;
import models.LightingModel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
public class LightingClient extends Client {

    private final String lightenUP = "turn up the lighting";
    private final String lighenDown = "dim the lighting";
    private final String turnOff = "turn off";
    private final String turnOn = "Turn on";
    private boolean modify = false;
    
    

    public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";
    
    
    public static final String TOPIC_BRIGHTNESS = "bedroom/brightness";
    public static final String TOPIC_TEMPERATURE = "bedroom/temperature";
    String topic = "/house/black/world";
    String content = "SUNSET UPDATE: The lights will be turned on at 7:25 pm today.\n"
            + "WEATHER UPDATE: Today will be provede some sunny spells with minor clouds in the evening. ";
    int qos = 2;
    String broker = "tcp://iot.eclipse.org:1883";
    String clientId = "Publisher";
    MemoryPersistence persistence = new MemoryPersistence();

    /**
     * Bed Client Constructor.
     */
    public LightingClient() {
        super();
        serviceType = "_lighting._udp.local.";
        ui = new LightingUI(this);
        name = "Bedroom Lighting";

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setWill(sampleClient.getTopic("bedroom/LWT"), "I'm gone :(".getBytes(), 0, false);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            message.setRetained(false);
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

    /**
     * sends a message to increase the lighting
     */
    public void brightenLighting() {
        String json = new Gson().toJson(new LightingModel(LightingModel.Action.lighten));
        String a = sendMessage(json);
        LightingModel lightM = new Gson().fromJson(a, LightingModel.class);
        System.out.println("Client Received " + json);

        if (lightM.getAction() == LightingModel.Action.lighten) {
            modify = lightM.getValue();
            ui.updateArea(lightM.getMessage());
        }
    }

    //send a message to dim the lighting
    public void dimLighting() {
        String json = new Gson().toJson(new LightingModel(LightingModel.Action.darken));
        String a = sendMessage(json);
        LightingModel lightM = new Gson().fromJson(a, LightingModel.class);
        System.out.println("Client Received " + json);

        if (lightM.getAction() == LightingModel.Action.darken) {
            modify = lightM.getValue();
            ui.updateArea(lightM.getMessage());
        }
    }

    //turn off lighting message
    public void turnOff() {
        String json = new Gson().toJson(new LightingModel(LightingModel.Action.lightOff));
        String a = sendMessage(json);
        LightingModel lightM = new Gson().fromJson(a, LightingModel.class);
        System.out.println("Client Received " + json);

        if (lightM.getAction() == LightingModel.Action.lightOff) {
            modify = lightM.getValue();
            ui.updateArea(lightM.getMessage());
        }
    }

    //turn on lighting message
    public void turnOn() {
        String json = new Gson().toJson(new LightingModel(LightingModel.Action.lightOn));
        String a = sendMessage(json);
        LightingModel lightM = new Gson().fromJson(a, LightingModel.class);
        System.out.println("Client Received " + json);

        if (lightM.getAction() == LightingModel.Action.lightOn) {
            modify = lightM.getValue();
            ui.updateArea(lightM.getMessage());
        }
    }
 public void blueLight() {
         String json = new Gson().toJson(new LightingModel(LightingModel.Action.blue));
        String a = sendMessage(json);
        LightingModel lightM = new Gson().fromJson(a, LightingModel.class);
        System.out.println("Client Received " + json);

        if (lightM.getAction() == LightingModel.Action.blue) {
            modify = lightM.getValue();
            ui.updateArea(lightM.getMessage());
      }
     }
    public void greenLight() {
        String json = new Gson().toJson(new LightingModel(LightingModel.Action.green));
        String a = sendMessage(json);
        LightingModel lightM = new Gson().fromJson(a, LightingModel.class);
        System.out.println("Client Received " + json);

        if (lightM.getAction() == LightingModel.Action.green) {
            modify = lightM.getValue();
            ui.updateArea(lightM.getMessage());}
    }

    public void orangeLight() {
       String json = new Gson().toJson(new LightingModel(LightingModel.Action.orange));
        String a = sendMessage(json);
        LightingModel lightM = new Gson().fromJson(a, LightingModel.class);
        System.out.println("Client Received " + json);

        if (lightM.getAction() == LightingModel.Action.orange) {
            modify = lightM.getValue();
            ui.updateArea(lightM.getMessage());
        }

}
    public void purpleLight() {
     String json = new Gson().toJson(new LightingModel(LightingModel.Action.purple));
        String a = sendMessage(json);
        LightingModel lightM = new Gson().fromJson(a, LightingModel.class);
        System.out.println("Client Received " + json);

        if (lightM.getAction() == LightingModel.Action.purple) {
            modify = lightM.getValue();
            ui.updateArea(lightM.getMessage()); 
        }
}
    @Override
    public void updatePoll(String msg) {
        if (msg.equals("Lighting is at its brightest.")) {
            modify = false;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new LightingUI(this);
        modify = false;
    }

}