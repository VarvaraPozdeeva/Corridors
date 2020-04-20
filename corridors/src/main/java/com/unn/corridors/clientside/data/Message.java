package com.unn.corridors.clientside.data;

public enum Message {

    YOUR_MOVE("Move"),
    OTHER_MOVE("Please wait, other moving");
    private String message;

    Message(String message) {
        this.message = message;
    }
    public String valueOf(){
        return message;
    }

}
