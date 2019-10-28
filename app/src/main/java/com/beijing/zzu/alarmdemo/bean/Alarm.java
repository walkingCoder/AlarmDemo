package com.beijing.zzu.alarmdemo.bean;

/**
 * @author jiayk
 * @date 2019/10/27
 */
public class Alarm {

    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_LOW = 0;

    private String id;
    private String name;
    private String priority;
    private int interval;
    private int Level;

    public int getLevel() {
        if (getPriority().equals("High")){
            return PRIORITY_HIGH;
        }else if (getPriority().equals("Normal")){
            return PRIORITY_NORMAL;
        }
        return PRIORITY_LOW;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
