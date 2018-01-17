package com.yyq.domian;

public class Message {
    private int id;

    private String PlaneId;

    private int lastX;

    private int lastY;

    private int lastZ;

    private int offsetX;

    private int offsetY;

    private int offsetZ;

    private int presentX;

    private int presentY;

    private int presentZ;

    private boolean state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaneId() {
        return PlaneId;
    }

    public void setPlaneId(String planeId) {
        PlaneId = planeId;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public int getLastZ() {
        return lastZ;
    }

    public void setLastZ(int lastZ) {
        this.lastZ = lastZ;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetZ(int offsetZ) {
        this.offsetZ = offsetZ;
    }

    public int getPresentX() {
        return presentX;
    }

    public void setPresentX(int presentX) {
        this.presentX = presentX;
    }

    public int getPresentY() {
        return presentY;
    }

    public void setPresentY(int presentY) {
        this.presentY = presentY;
    }

    public int getPresentZ() {
        return presentZ;
    }

    public void setPresentZ(int presentZ) {
        this.presentZ = presentZ;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
