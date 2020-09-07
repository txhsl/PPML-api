package org.txhsl.ppml.api.service.crypto;

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class GroupElement
{

    private Curve curve;
    private ECPoint ecPoint;

    public GroupElement(Curve crv, ECPoint point)
    {
        curve = crv;
        ecPoint = point;
    }

    public static GroupElement fromBytes(byte[] data)
    {
        Curve crv = new Curve("secp256k1");
        ECCurve curve = crv.getCurve();
        ECPoint point = curve.decodePoint(data);
        return new GroupElement(crv, point);
    }

    public byte[] toBytes()
    {
        return ecPoint.getEncoded();
    }
   
    public ECPoint getValue()
    {
        return ecPoint;
    }

    public GroupElement mul(Scalar sc)
    {
        return new GroupElement(curve, ecPoint.multiply(sc.getValue()));
    }
    public GroupElement add(GroupElement other)
    {
        return new GroupElement(curve, ecPoint.add(other.getValue()));
    }

}
