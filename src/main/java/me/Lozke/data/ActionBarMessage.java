package me.Lozke.data;

public class ActionBarMessage implements Comparable< ActionBarMessage >{

    private String message;
    private Integer weight;
    private int time;
    private boolean showTime;


    public ActionBarMessage(String message, int time, boolean showTime) {
        this(message, 0, time, showTime);
    } //Default weight
    public ActionBarMessage(String message, int weight) {
        this(message, weight, 1, false);
    } //Default time
    public ActionBarMessage(String message) {
        this(message, 0, 1, false);
    } //Default weight and time
    public ActionBarMessage(String message, int weight, int time, boolean showTime) {
        this.message = message;
        this.weight = weight;
        this.time = time;
        this.showTime = showTime;
    }


    public String getMessage() {
        if(showTime) {
            return message + " [" + time + "]";
        }
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
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public boolean isShowTime() {
        return showTime;
    }
    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }


    @Override
    public int compareTo(ActionBarMessage message) {
        return this.weight.compareTo(message.getWeight());
    }
}
