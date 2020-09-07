package org.txhsl.ppml.api.service.crypto;

import java.util.Arrays;

public class ReEncryptionKey
{
    private Scalar reKey;
    private GroupElement internalPublicKey;


    public ReEncryptionKey(Scalar rk, GroupElement ge)
    {
        reKey = rk;
        internalPublicKey = ge;
    }

    public Scalar getReKey()
    {
        return reKey;
    }
    public GroupElement getInternalPublicKey()
    {
        return internalPublicKey;
    }

    public static ReEncryptionKey fromBytes(byte[] data)
    {
        byte[] rk = Arrays.copyOfRange(data, 0, 33);
        byte[] ipk = Arrays.copyOfRange(data, 33, data.length);

        return new ReEncryptionKey(Scalar.fromBytes(rk), GroupElement.fromBytes(ipk));
    }
 
    public byte[] toBytes()
    {
        byte[] rk = reKey.toBytes();
        byte[] ipk = internalPublicKey.toBytes();

        byte[] data = new byte[rk.length + ipk.length];

        System.arraycopy(rk, 0, data, 0, rk.length);
        System.arraycopy(ipk, 0, data, rk.length, ipk.length);
        return data; 
    }

}
