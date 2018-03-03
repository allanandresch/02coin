package o2coin;

import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(TransactionRepository repository) {
        return (args) -> {
            // save a couple of transactions
            repository.save(generate());
            repository.save(generate());
            repository.save(generate());
            repository.save(generate());
            repository.save(generate());

            // fetch all transactions
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (O2CoinTransaction o2CoinTransaction : repository.findAll()) {
                log.info(o2CoinTransaction.toString());
            }
            log.info("");

            // fetch an individual o2CoinTransaction by ID
            O2CoinTransaction o2CoinTransaction = repository.findOne(1L);
            log.info("O2CoinTransaction found with findOne(1L):");
            log.info("--------------------------------");
            log.info(o2CoinTransaction.toString());
            log.info("");

            // fetch transactions by last name
            log.info("O2CoinTransaction found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (O2CoinTransaction bauer : repository
                .findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }

    O2CoinTransaction generate(){
        BigInteger value = new BigInteger("100000");

        byte[] privKey = HashUtil.sha3("cat".getBytes());
        ECKey ecKey = ECKey.fromPrivate(privKey);

        byte[] senderPrivKey = HashUtil.sha3("cow".getBytes());

        byte[] gasPrice = Hex.decode("09184e72a000");
        byte[] gas = Hex.decode("4255");
        return new O2CoinTransaction(value,  privKey,  senderPrivKey,  gasPrice,  gas, "", "");
    }
}
