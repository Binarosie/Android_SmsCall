package hcmute.edu.vn.tnquynh.model;

public class MessageModel {
    private String sender;
    private String body;

    public MessageModel(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }
}