package es.nitaur.api;

public class FaultyResponseModel {

    private String status;

    private String explanation;

    public FaultyResponseModel() {
    }

    public FaultyResponseModel(String status, String explanation) {
        this.status = status;
        this.explanation = explanation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
