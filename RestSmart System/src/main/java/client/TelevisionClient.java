package client;

import com.google.gson.Gson;
import clientui.TelevisionUI;
import models.TelevisionModel;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
public class TelevisionClient extends Client {

    private String pass = "pass";
    private final String On = "On";
    private final String Off = "Off";
    private boolean isOn = false;
    private boolean isOff = true;
    private boolean isSwitchingNext, isSwitchingPrevious, isIncreasingVolume;
    private boolean isDecreasingVolume, isConnected, isDisconnected;
    private boolean isConnecting, isDisconnecting, isTurningOn, isTurningOff;

    public TelevisionClient() {
        super();
        serviceType = "_television._udp.local.";
        ui = new TelevisionUI(this);
        name = "Television";
    }

    //Sends a message to turn-on the Television
    public void powerOn() { //power_on
        String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.ON));
        String a = sendMessage(json);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);
        System.out.println("Client Received " + json);

        if (teleM.getAction() == TelevisionModel.Action.ON) {
            isTurningOn = teleM.getValue();
            ui.updateArea(teleM.getMessage());
        }
    }

    //sends a message to turn-off the Television
    public void powerOff() { //power_off
        String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.OFF));
        String a = sendMessage(json);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);
        System.out.println("Client Received " + json);

        if (teleM.getAction() == TelevisionModel.Action.OFF) {
            isTurningOff = teleM.getValue();
            ui.updateArea(teleM.getMessage());
        }
    }

    //sends a message to switch to the next Television channel
    public void nextChannel() {
        String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.NEXT_CHANNEL));
        String a = sendMessage(json);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);
        System.out.println("Client Received " + json);

        if (teleM.getAction() == TelevisionModel.Action.NEXT_CHANNEL) {
            isSwitchingNext = teleM.getValue();
            ui.updateArea(teleM.getMessage());
        }
    }

    //sends a message to switch to the previous Television channel
    public void previousChannel() {
        String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.PREVIOUS_CHANNEL));
        String a = sendMessage(json);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);
        System.out.println("Client Received " + json);

        if (teleM.getAction() == TelevisionModel.Action.PREVIOUS_CHANNEL) {
            isSwitchingPrevious = teleM.getValue();
            ui.updateArea(teleM.getMessage());
        };
    }

    //sends a message to increase the volume (of the television)
    public void increaseVolume() {
        String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.INCREASE_VOLUME));
        String a = sendMessage(json);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);
        System.out.println("Client Received " + json);

        if (teleM.getAction() == TelevisionModel.Action.INCREASE_VOLUME) {
            isIncreasingVolume = teleM.getValue();
            ui.updateArea(teleM.getMessage());
        };
    }

    //sends a message to decrease the volume
    public void decreaseVolume() {
        String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.DECREASE_VOLUME));
        String a = sendMessage(json);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);
        System.out.println("Client Received " + json);

        if (teleM.getAction() == TelevisionModel.Action.DECREASE_VOLUME) {
            isDecreasingVolume = teleM.getValue();
            ui.updateArea(teleM.getMessage());
        };
    }

    //sends a message to connect to an audio 
    public void connectToAudio() {
        String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.CONNECT));
        String a = sendMessage(json);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);
        System.out.println("Client Received " + json);

        if (teleM.getAction() == TelevisionModel.Action.CONNECT) {
            isConnecting = teleM.getValue();
            ui.updateArea(teleM.getMessage());
        };
    }

    //sends a message to disconnect from an audio source
    public void disconnectFromAudio() {
        String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.DISCONNECT));
        String a = sendMessage(json);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);
        System.out.println("Client Received " + json);

        if (teleM.getAction() == TelevisionModel.Action.DISCONNECT) {
            isDisconnecting = teleM.getValue();
            ui.updateArea(teleM.getMessage());
        };
    }

    @Override
    public void updatePoll(String msg) {
        if (msg.equals("Audio source is not switched on. Press the 'on' button to turn on.")) {
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new TelevisionUI(this);
//        isWarming = false;
    }
}
