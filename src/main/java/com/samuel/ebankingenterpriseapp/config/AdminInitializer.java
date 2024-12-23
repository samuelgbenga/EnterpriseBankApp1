package com.samuel.ebankingenterpriseapp.config;


import com.samuel.ebankingenterpriseapp.entity.Bank;
import com.samuel.ebankingenterpriseapp.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AdminInitializer {
    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    private final BankRepository bankRepository;


    @Value("${Bank_NAME}")
    private String bankName;

    @Value("${BANK_ADDRESS}")
    private String bankAddress;

    @Value("${BANK_CONTACT}")
    private String contactDetails;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {


            if (bankRepository.findByName(bankName).isEmpty()) {
                Bank bank = new Bank();
                bank.setName(bankName);
                bank.setAddress(bankAddress);
                bank.setContactDetails(contactDetails);
                bankRepository.save(bank);


                logger.info("Bank As been Seeded.");
            } else {
                logger.info("Bank Already Exist.");
            }

        };
    }
}