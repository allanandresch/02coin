package o2coin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class O2CoinTransaction {

    BigInteger value;
    byte[] privKey;
    byte[] senderPrivKey;
    byte[] gasPrice;
    byte[] gas;
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    protected O2CoinTransaction() {
    }

    public O2CoinTransaction(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public O2CoinTransaction(BigInteger value, byte[] privKey, byte[] senderPrivKey, byte[] gasPrice, byte[] gas,  String firstName, String lastName) {
        this.value = value;
        this.privKey = privKey;
        this.senderPrivKey = senderPrivKey;
        this.gasPrice = gasPrice;
        this.gas = gas;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("O2CoinTransaction[id=%d, firstName='%s', lastName='%s']", id,
            firstName, lastName);
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public byte[] getPrivKey() {
        return privKey;
    }

    public void setPrivKey(byte[] privKey) {
        this.privKey = privKey;
    }

    public byte[] getSenderPrivKey() {
        return senderPrivKey;
    }

    public void setSenderPrivKey(byte[] senderPrivKey) {
        this.senderPrivKey = senderPrivKey;
    }

    public byte[] getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(byte[] gasPrice) {
        this.gasPrice = gasPrice;
    }

    public byte[] getGas() {
        return gas;
    }

    public void setGas(byte[] gas) {
        this.gas = gas;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
