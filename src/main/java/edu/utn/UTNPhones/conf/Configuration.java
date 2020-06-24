package edu.utn.UTNPhones.conf;

import edu.utn.UTNPhones.session.ClientSessionFilter;
import edu.utn.UTNPhones.session.EmployeeSessionFilter;
import edu.utn.UTNPhones.session.SysSessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
@EnableScheduling
@EnableCaching
public class Configuration {
    ClientSessionFilter clientSessionFilter;
    EmployeeSessionFilter employeeSessionFilter;
    SysSessionFilter sysSessionFilter;

    @Autowired
    public Configuration(ClientSessionFilter clientSessionFilter, EmployeeSessionFilter employeeSessionFilter, SysSessionFilter sysSessionFilter) {
        this.clientSessionFilter = clientSessionFilter;
        this.employeeSessionFilter = employeeSessionFilter;
        this.sysSessionFilter = sysSessionFilter;
    }

    @Bean
    public FilterRegistrationBean myFilterEmployee() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(employeeSessionFilter);
        registration.addUrlPatterns("/backoffice/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean myFilterClient() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(clientSessionFilter);
        registration.addUrlPatterns("/web/*");

        return registration;
    }

    @Bean
    public FilterRegistrationBean myFilterAdmin() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sysSessionFilter);
        registration.addUrlPatterns("/SYS/*");
        return registration;
    }
}
