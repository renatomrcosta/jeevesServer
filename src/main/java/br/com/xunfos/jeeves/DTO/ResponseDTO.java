package br.com.xunfos.jeeves.DTO;

/**
 * Created by rrc on 31/05/2017.
 */
public class ResponseDTO {
    private String color;
    private String message;
    private boolean notify;
    private String messageFormat;

    public ResponseDTO() {
        this.color = "green";
        this.notify = false;
        this.messageFormat = "text";
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(final boolean notify) {
        this.notify = notify;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(final String messageFormat) {
        this.messageFormat = messageFormat;
    }
}
