package o2coin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<O2CoinTransaction, Long> {

    List<O2CoinTransaction> findByLastNameStartsWithIgnoreCase(String lastName);
}
