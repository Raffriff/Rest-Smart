package models;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
public class TelevisionModel {

    public enum Action {
        STATUS, ON, OFF, CONNECT, DISCONNECT, INCREASE_VOLUME, DECREASE_VOLUME, NEXT_CHANNEL, PREVIOUS_CHANNEL;
    }
    private Action action;
    private String message;
    private boolean value;

    public TelevisionModel(Action action) {
        this.action = action;
    }

    public TelevisionModel(Action action, String message) {
        this.action = action;
        this.message = message;
    }

    public TelevisionModel(Action action, String message, boolean value) {
        this.action = action;
        this.message = message;
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

}
