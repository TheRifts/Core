package me.Lozke.data.ActionBarType;

public class ActionBarMessage implements Comparable< ActionBarMessage >{

    protected String id;
    protected String message;
    protected Integer weight;
    protected int time;

    public ActionBarMessage(String id, String message) {
        this(id, message, 0, 1);
    } //Default weight and time
    public ActionBarMessage(String id, String message, int time) {
        this(id, message, 0, time);
    } //Default weight
    public ActionBarMessage(String id, String message, int weight, int time) {
        this.id = id;
        this.message = message;
        this.weight = weight;
        this.time = (time * 20) + 20;
    }

    public String getID() {
        return id;
    }
    public void setID(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTime() {
        return time/20;
    }
    public void setTime(int time) {
        this.time = (time * 20) + 20;
    }

    public void tick(int amount) {
        time = time - amount;
    }

    public boolean isValid() {
        return time > 0;
    }

    @Override
    public int compareTo(ActionBarMessage message) {
        return this.weight.compareTo(message.getWeight());
    }

    public void overwrite(ActionBarMessage message) {
        this.id = message.id;
        this.message = message.message;
        this.weight = message.weight;
        this.time = message.time;
    }
}
