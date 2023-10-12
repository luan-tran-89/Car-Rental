package rentalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rentalservice.domain.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    // Additional query methods (if required) can be added here.
}
