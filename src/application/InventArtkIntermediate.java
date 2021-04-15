package application;

public class InventArtkIntermediate {
    int inventId;
    String invTyp;
    int invNumber;

    public InventArtkIntermediate(int inventId, String invTyp, int invNumber) {
        this.inventId = inventId;
        this.invTyp = invTyp;
        this.invNumber = invNumber;
    }

    public void setInventId(int inventId) {
        this.inventId = inventId;
    }

    public void setInvTyp(String invTyp) {
        this.invTyp = invTyp;
    }

    public void setInvNumber(int invNumber) {
        this.invNumber = invNumber;
    }

    public int getInventId() {
        return inventId;
    }

    public String getInvTyp() {
        return invTyp;
    }

    public int getInvNumber() {
        return invNumber;
    }
}
