package com.sebok.springClientDemo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = {"delete from client", "delete from log_entry"})
public class ClientTest {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LogEntryRepository logEntryRepository;
    @Autowired
    private  ClientController clientController;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSaveThenFind() {
        Client client = new Client("Jane Doe");
        clientRepository.save(client);
        long id = client.getId();
        assertEquals("Jane Doe",  clientRepository.findById(id).get().getName());
    }

    @Test
    public void testListByName() {
        Client client = new Client("Jack Smith");
        clientRepository.save(client);
        long id = client.getId();
        String name = client.getName().toString();
        assertEquals("Jack Smith",  clientRepository.list(id, name).get(0).getName());
    }

    @Test
    public void testBirthday() {
        Client client = new Client("John Wick", LocalDate.of(1989, 9, 19));
        clientRepository.save(client);
        long id = client.getId();
        assertEquals(LocalDate.of(1989, 9, 19),  clientRepository.findById(id).get().getBirthDate());
    }

    @Test
    @Transactional
    public void testSaveWithEmptyName() {
        expectedException.expect(IllegalArgumentException.class);
        Client client = new Client("");
        logEntryRepository.save(new LogEntry("Create client: " + client.getName()));
        if (client.getName().length() < 3) {
            throw new IllegalArgumentException("Name must be longer then 3 characters");
        }
        clientRepository.save(client);
    }

    @Test
    public void testSaveLog() {
        LogEntry logEntry = new LogEntry("Begin");
        logEntryRepository.save(logEntry);
        assertEquals(1, logEntryRepository.count());
    }

    @Test
    public void testSaveThenList() {
        clientRepository.save(new Client("Kati Kovacs"));
        clientRepository.save(new Client("Pista Eros"));
        clientRepository.save(new Client("Levente Magyar"));
        List<String> names = StreamSupport.stream(clientRepository.findAll().spliterator(), false).map(Client::getName).collect(Collectors.toList());
        assertTrue(names.contains("Pista Eros"));
    }


    @Test
    public void testSaveClientWithLog() {
        Client client = new Client("Nelly Test", LocalDate.of(1995, 5, 6));
        LogEntry logEntry = new LogEntry("New client recorded: " + client.getName());
        clientRepository.save(client);
        logEntryRepository.save(logEntry);
        assertEquals("Nelly Test", clientRepository.findById(client.getId()).get().getName());
    }

    @Test
    public void testConroller() {
        clientController.saveClientWithLog("Natalia Controller", LocalDate.of(2003, 1, 4));
        assertEquals("Natalia Controller", clientRepository.findClientByClientname("Natalia Controller").get().getName());
    }
}
