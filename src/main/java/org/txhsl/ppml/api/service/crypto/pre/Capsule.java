package org.txhsl.ppml.api.service.crypto.pre;

import java.util.Arrays;

public class Capsule
{

    private GroupElement E;
    private GroupElement V;
    private Scalar S;
    private GroupElement XG;
    private boolean isReEncrypted;


    public Capsule(GroupElement e, GroupElement v, Scalar s, GroupElement xg, boolean isRe)
    {
        E = e;
        V = v;
        S = s;
        XG = xg;
        isReEncrypted = isRe;
    }

    public GroupElement getE()
    {
        return E;
    }
    public GroupElement getV()
    {
        return V;
    }
    public Scalar getS()
    {
        return S;
    }
    public GroupElement getXG()
    {
        return XG;
    }
    public boolean getIsReEncrypted()
    {
        return isReEncrypted;
    }
    public void setIsReEncrypted(boolean isRe)
    {
        isReEncrypted = isRe;
    }

    public static Capsule fromBytes(byte[] data)
    {
        int scSize = 33;
        int geSize = 65;
        boolean isRe = false;

        if(data.length == 3*geSize + scSize){
            isRe = true;
        }
        else if (data.length == 2*geSize + scSize){
            isRe = false;
        }
        else {
            System.out.println("ERROR: INVALID LENGTH!");
        }

        byte[] e = Arrays.copyOfRange(data, 0, geSize);
        byte[] v = Arrays.copyOfRange(data, geSize, geSize * 2);
        byte[] s = Arrays.copyOfRange(data, geSize*2, geSize*2 + scSize);

        GroupElement xg = null;
        if(isRe)
        {
            byte[] x = Arrays.copyOfRange(data, geSize * 2 + scSize, data.length);
            xg = GroupElement.fromBytes(x);
        }
        return new Capsule(GroupElement.fromBytes(e), GroupElement.fromBytes(v), Scalar.fromBytes(s), xg, isRe);

    }    
    public byte[] toBytes()
    {
        byte[] e = E.toBytes();
        byte[] v = V.toBytes();
        byte[] s = S.toBytes();
        byte[] xg = new byte[0];
        if(isReEncrypted){
            byte[] x = XG.toBytes();
            xg =  Arrays.copyOf(x, x.length);
        }

        byte[] data = new byte[e.length + v.length + s.length + xg.length];
        System.arraycopy(e, 0, data, 0, e.length);
        System.arraycopy(v, 0, data, e.length, v.length);
        System.arraycopy(s, 0, data, e.length + v.length, s.length);
        System.arraycopy(xg, 0, data, e.length + v.length + s.length, xg.length);
        return data;
    }
}
