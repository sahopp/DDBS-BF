import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


public class RandomPrime
{
    public int generate(int min){
        Random ran = new SecureRandom();
        BigInteger a = BigInteger.probablePrime((int) (Math.log(min)/Math.log(2))+5, ran);
        return a.intValue();
    }
}