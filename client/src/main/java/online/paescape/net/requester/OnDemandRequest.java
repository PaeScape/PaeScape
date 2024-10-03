package online.paescape.net.requester;


import online.paescape.collection.QueueNode;

public final class OnDemandRequest extends QueueNode {

    public int dataType;
    public byte[] buffer;
    public int id;
    public boolean isNotExtraFile;
    public int loopCycle;

    public OnDemandRequest() {
        isNotExtraFile = true;
    }

    @Override
    public String toString() {
        return "OnDemandData [dataType=" + dataType + ", ID=" + id
                + ", incomplete=" + isNotExtraFile + ", loopCycle=" + loopCycle
                + "]";
    }

}
