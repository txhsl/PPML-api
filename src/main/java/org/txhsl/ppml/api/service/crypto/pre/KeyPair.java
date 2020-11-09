package org.txhsl.ppml.api.service.crypto.pre;

public class KeyPair
{
    private PrivateKey privateKey;
    private PublicKey publicKey;


    public KeyPair(PrivateKey sk, PublicKey pk)
    {
        privateKey = sk;
        publicKey = pk;
    }


    public static KeyPair generateKeyPair()
    {
        PrivateKey privateKey = PrivateKey.generate(new Curve("secp256k1"));
        return new KeyPair(privateKey, privateKey.getPublicKey());
    }

    public PublicKey getPublicKey()
    {
        return publicKey;
    }
    public PrivateKey getPrivateKey()
    {
        return privateKey;
    }


}
