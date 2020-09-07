package org.txhsl.ppml.api.service.crypto;

import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;

import java.security.SecureRandom;

public class PrivateKey
{

    private Scalar scalar;
    private PublicKey pubKey;

    public PrivateKey(Scalar sc, PublicKey pk)
    {
        scalar = sc;
        if (pk == null) {
            pubKey = generatePublicKey();
        }
        else {
            pubKey = pk;
        }
    }

    public static PrivateKey generate(Curve curve)
    {
        ECKeyPairGenerator gen = new ECKeyPairGenerator();
        SecureRandom secureRandom = new SecureRandom();
        X9ECParameters secnamecurves = SECNamedCurves.getByName(curve.getName());
        ECDomainParameters ecParams = new ECDomainParameters(secnamecurves.getCurve(), secnamecurves.getG(), secnamecurves.getN(), secnamecurves.getH());
	ECKeyGenerationParameters keyGenParam = new ECKeyGenerationParameters(ecParams, secureRandom);
	gen.init(keyGenParam);
	AsymmetricCipherKeyPair kp = gen.generateKeyPair();
        ECPrivateKeyParameters privatekey = (ECPrivateKeyParameters)kp.getPrivate();
        ECPublicKeyParameters publickey = (ECPublicKeyParameters)kp.getPublic();
        return new PrivateKey(new Scalar(privatekey.getD(), curve), new PublicKey(new GroupElement(curve, publickey.getQ())));

    }

    public Scalar getValue()
    {
        return scalar;
    }

    public PublicKey generatePublicKey()
    {
       Curve curve = new Curve("secp256k1");
       return new PublicKey(new GroupElement(curve, curve.getGenerator().multiply(scalar.getValue())));
    }
    public PublicKey getPublicKey()
    {
        return pubKey;
    }

    public static PrivateKey fromBytes(byte[] data)
    {
        return new PrivateKey(Scalar.fromBytes(data), null);
    }

    public byte[] toBytes()
    {
        return scalar.toBytes();
    }

}
