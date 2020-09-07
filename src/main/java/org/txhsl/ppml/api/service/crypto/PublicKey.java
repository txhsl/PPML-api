package org.txhsl.ppml.api.service.crypto;

public class PublicKey
{

    private GroupElement pubKey;


    public PublicKey(GroupElement ge)
    {
        pubKey = ge;
    }

    public GroupElement getValue()
    {
        return pubKey;
    }

    public static PublicKey fromBytes(byte[] data)
    {
        return new PublicKey(GroupElement.fromBytes(data));
    }    
    public byte[] toBytes()
    {
        return pubKey.toBytes();
    }

}
