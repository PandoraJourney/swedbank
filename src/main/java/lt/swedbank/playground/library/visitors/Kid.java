package lt.swedbank.playground.library.visitors;

public class Kid {
    private final int id;
    private final String name;
    private boolean acceptWaiting;
    private Ticket ticket;

    public Kid(int id, String name, boolean acceptWaiting, int vipCount) {
        this.id = id;
        this.name = name;
        this.acceptWaiting = acceptWaiting;
        this.ticket = new Ticket(vipCount, this);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAcceptWaiting() {
        return acceptWaiting;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setAcceptWaiting(boolean acceptWaiting) {
        this.acceptWaiting = acceptWaiting;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + id;
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if(!(o instanceof Kid)) {
            return false;
        }

        Kid kid = (Kid)o;

        return id == kid.id &&
                name.equals(kid.name);
    }

    @Override
    public String toString() {
        return "Kid{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", acceptWaiting=" + acceptWaiting +
                ", ticket=" + ticket +
                '}';
    }
}
