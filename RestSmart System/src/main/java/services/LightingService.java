package services;

import com.google.gson.Gson;
import models.LightingModel;
import clientui.LightingUI;

import serviceui.ServiceUI;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
public class LightingService extends Service {

    private int lighest;
    private int darkest;
    private int currentLight;
    private static boolean isLightened, isDarken, off, on;
    
     private static boolean blue,green,orange,purple;

    String broker = "tcp://iot.eclipse.org:1883";
    String clientId = "Subscriber";
    MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient mqttClient;

    public LightingService(String name) {
        super(name, "_lighting._udp.local.");
        lighest = 100; //maximum brightness of the room
        darkest = 30; //lowest dimming settings
        currentLight = 0; //default standard room brightness with lighting
        isLightened = false;
        isDarken = false;
        off = true;
        on = false;
        ui = new ServiceUI(this, name);

        try {
            MqttClient SubscriberClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            SubscriberClient.setCallback(new SampleSubscriber());
            System.out.println("Connecting to broker: " + broker);
            SubscriberClient.connect(connOpts);
            System.out.println("Connected");
            SubscriberClient.subscribe("/bedroom/#");

            //Subscribe to all subtopics of bedroom
            final String topic = "bedroom/#";

            System.out.println("Subscriber is now listening to " + topic);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
            System.exit(1);
        }

    }

    class SampleSubscriber implements MqttCallback {

        public SampleSubscriber() {

        }

        @Override
        public void connectionLost(Throwable thrwbl) {
        }

        @Override
        public void messageArrived(String string, MqttMessage mm) throws Exception {
            System.out.println(mm + " arrived from topic " + string);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken imdt) {
        }

    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " + a);
        LightingModel lighting = new Gson().fromJson(a, LightingModel.class);

        if (lighting.getAction() == LightingModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.STATUS, msg));
            sendBack(json);
            
//lighten
        }  else if (lighting.getAction() == LightingModel.Action.lighten) {
            brightenLighting();
            String msg = (isLightened) ? "The Room is brightening by 10%" : "The room cant get any brighter";
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.lighten, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isLightened) ? "The lighting brightened!" : "The lighting cant get any brighter..";
            ui.updateArea(serviceMessage);
        }
//dim lighting
        else if (lighting.getAction() == LightingModel.Action.darken) {
            dimLighting();
            String msg = (isDarken) ? "The Room is dimming by 10%" : "The lighting cant be dimmed any lower";
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.darken, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isDarken) ? "The Room is dimming!" : "Sorry the room cant dim any more..";
            ui.updateArea(serviceMessage);
        }
//power off
        else if (lighting.getAction() == LightingModel.Action.lightOff) {
            turnOffLighting();
            String msg = (off) ? "The Lighting has been turned off" : "Lights are already off";
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.lightOff, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (off) ? "Lighting turned off" : "Lights are off";
            ui.updateArea(serviceMessage);
        }
//power on
        else if (lighting.getAction() == LightingModel.Action.lightOn) {
            turnOnLighting();
            String msg = (on) ? "The Lighting has been turned on" : "Lights have been turned on";
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.lightOn, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (on) ? "Lighting turned on" : "Lights are on";
            ui.updateArea(serviceMessage);
            
            } 
//Switch colour
//blue
        else if (lighting.getAction() == LightingModel.Action.blue) {
            turnOnLighting();
            String msg =( blue) ? "The Lighting has been switched to Blue colour" : "Lights have been switched to blue";
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.blue, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (blue) ? "The Lighting has been switched to Blue colour" : "Lights have been switched to blue";
            ui.updateArea(serviceMessage);
            
            }
//Switch colour
//green
        else if (lighting.getAction() == LightingModel.Action.green) {
            turnOnLighting();
            String msg = (green) ? "The Lights have been switched to Green colour" : "Lights have been switched to Green";
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.green, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (green) ? "The Lights have been switched to Green colour" : "Lights have been switched to Green";
            ui.updateArea(serviceMessage);
        } 
//Switch colour
//orange
        else if (lighting.getAction() == LightingModel.Action.orange) {
            turnOnLighting();
            String msg = (orange) ? "The Lights have been switched to Orange colour" : "Lights have been switched to Orange";
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.orange, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (orange) ? "The Lights have been switched to Orange colour" : "Lights have been switched to Orange";
            ui.updateArea(serviceMessage);
//Switch colour
//purple
        }else if (lighting.getAction() == LightingModel.Action.purple) {
            turnOnLighting();
            String msg = (purple) ? "The Lights have been switched to Purple colour" : "Lights have been switched to Purple";
            String json = new Gson().toJson(new LightingModel(LightingModel.Action.purple, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (purple) ? "The Lights have been switched to Purple colour" : "Lights have been switched to Purple";
            ui.updateArea(serviceMessage);
            
            
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    public void turnOffLighting() {
        if (currentLight >= 0) {
            currentLight = 0;
            System.out.println("Lights Turned off");
        }
    }

    public void turnOnLighting() {
        if (currentLight <= 0) {
            currentLight += 100;
            System.out.println("Room is" + currentLight + "% bright");
        }
    }

    public void brightenLighting() {
        if (currentLight != lighest) {
            isLightened = true;
            currentLight += 10;
        } else {
            isLightened = false;
        }
    }

    public void dimLighting() {
        if (currentLight != darkest) {
            isDarken = true;
            currentLight -= 10;
        } else {
            isDarken = false;
        }
    }

    @Override
    public String getStatus() {
        return "Room is " + currentLight + "% bright";
    }

    public static void main(String[] args) {
        new LightingService("Bedroom");

    }
}
