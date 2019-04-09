package se.lexicon.emil.CompanyManager.repositoryTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entities.Client;
import se.lexicon.emil.CompanyManager.repositories.ClientRepository;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client testClient;

    @Before
    public void init(){

        Client client1 = new Client("client1");
        Client client2 = new Client("client2");

        testClient = clientRepository.save(client1);
        clientRepository.save(client2);
    }

    @Test
    public void test_GetByName(){
        List<Client> expected = Arrays.asList(testClient);
        List<Client> actual = clientRepository.getByName("client1");
        assertEquals(expected, actual);
    }
}
