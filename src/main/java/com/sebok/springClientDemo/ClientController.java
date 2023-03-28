
package com.sebok.springClientDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private LogEntryRepository logEntryRepository;

    public void saveClientWithLog(String name, LocalDate birthDate) {
        Client client = new Client(name, birthDate);
        LogEntry logEntry = new LogEntry("New cliend recorded: " + client.getName());
        if(clientRepository != null && logEntryRepository != null) {
            clientRepository.save(client);
            logEntryRepository.save(logEntry);
        }
        logger.info("Client added: {} with birthday {} ", client.getName(), client.getBirthDate());
    }

}
