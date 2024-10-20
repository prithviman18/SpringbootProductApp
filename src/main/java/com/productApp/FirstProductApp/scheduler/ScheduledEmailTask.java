package com.productApp.FirstProductApp.scheduler;

import com.productApp.FirstProductApp.entity.User;
import com.productApp.FirstProductApp.repository.UserRepository;
import com.productApp.FirstProductApp.service.MailService;
import com.productApp.FirstProductApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledEmailTask {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;


    // Scheduled to run on the 25th of every month at midnight (00:00)
    @Scheduled(cron = "0 0 0 25 * ?")
    public void sendEmailToCustomers(){
        List<User> customers = userRepository.findByRoles_Name("ROLE_CUSTOMER");

        for (User customer : customers) {
            String subject = "Exclusive Month-End Sale at Prithvi's Online Shop!";
            String body = "Dear " + customer.getFirstName() + ",\n\n" +
                    "Get ready for our exciting month-end sale! Shop now and enjoy exclusive discounts.\n\n" +
                    "Best Regards,\nPrithvi's Online Shop Team";

            // Send email to customer
            mailService.sendEmailToCustomers(subject, body);
        }
    }
}
