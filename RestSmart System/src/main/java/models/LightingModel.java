package models;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
public class LightingModel {

    public enum Action {
        lighten, darken, STATUS, lightOff, lightOn,
         blue, green, orange, purple ;
    }

    private Action action;
    private String message;
    private boolean value;

    public LightingModel() {
    }

    public LightingModel(Action action) {
        this.action = action;
    }

    public LightingModel(Action action, String message) {
        this.action = action;
        this.message = message;
    }

    public LightingModel(Action action, String message, boolean value) {
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
