package lt.swedbank.playground.library.visitors;

public class Ticket {
    private int vipCount;
    private Kid kid;

    public Ticket(int vipCount, Kid kid) {
        this.vipCount = vipCount;
        this.kid = kid;
    }

    public boolean useVip() {
        if (vipCount > 0) {
            vipCount--;
            return true;
        }
        return false;
    }

    public Kid getKid() {
        return kid;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "vipCount=" + vipCount +
                ", kid=" + kid.getName() +
                '}';
    }
}
