import java.math.BigInteger;

public class Fraction {
    private  BigInteger numerator;
    private  BigInteger denominator;

    public Fraction(int numerator) {
        this.numerator = BigInteger.valueOf(numerator);
        denominator = BigInteger.valueOf(1);
    }

    public Fraction(BigInteger n, BigInteger d) {
        this.numerator = n;
        this.denominator = d;
        BigInteger g = numerator.gcd(denominator);
        numerator = numerator.divide(g);
        denominator = denominator.divide(g);
    }

    public Fraction add(Fraction other) {
        BigInteger n = numerator.multiply(other.denominator).add(other.numerator.multiply(denominator));
        BigInteger d = denominator.multiply(other.denominator);
        return new Fraction(n, d);
    }

    public Fraction subtract(Fraction other) {
        BigInteger n = numerator.multiply(other.denominator).subtract(other.numerator.multiply(denominator));
        BigInteger d = denominator.multiply(other.denominator);
        return new Fraction(n, d);
    }

    public Fraction multiply(Fraction other) {
        BigInteger n = numerator.multiply(other.numerator);
        BigInteger d = denominator.multiply(other.denominator);
        return new Fraction(n, d);
    }

    public Fraction divide(Fraction other) {
        BigInteger n = numerator.multiply(other.denominator);
        BigInteger d = denominator.multiply(other.numerator);
        return new Fraction(n, d);
    }

    public String toString() {
        if (denominator.equals(BigInteger.valueOf(1))) {
            return String.valueOf(numerator);
        } else {
            return numerator + "/" + denominator;
        }
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.valueOf(0))) {
            return a.abs();
        } else {
            return gcd(b, a.mod(b));
        }
    }

    public BigInteger getNumerator() {
        return numerator;
    }
}
