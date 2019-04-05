package se.lexicon.emil.CompanyManager.repositories;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.emil.CompanyManager.entity.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    List<Client> getByName(String name);
}
