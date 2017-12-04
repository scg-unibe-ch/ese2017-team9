package hello;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/customer")
    public String customer(Model model){
        model.addAttribute("customers", customerRepository.findAll());

        for(Customer c:customerRepository.findAll()){
            List<String> deliveries=customerRepository.customerListErasable(c.getCustomerId());
            if(deliveries.isEmpty()){
                c.setIsCustomerErasable(true);
            }else{
                c.setIsCustomerErasable(false);
            }
        }

        return "customer";
    }

    @Transactional
    @PostMapping("/customer/remove")
    public String removeCustomer(@RequestParam("customerId") long customerId){
        customerRepository.removeByCustomerId(customerId);
        return "redirect:/customer";
    }

    @Modifying
    @PostMapping("/editCustomer")
    public String saveCustomer(@Param("customer") Customer customer){
        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @RequestMapping("/editCustomer")
    public String editCustomer(@RequestParam(value = "customerId", defaultValue = "-1") long customerId, Model model){
        if(customerId != -1){
            model.addAttribute("currentCustomer", customerRepository.findByCustomerId(customerId));

        }
        else{
            model.addAttribute("currentCustomer", new Customer());
        }
        return "editCustomer";
    }


}
