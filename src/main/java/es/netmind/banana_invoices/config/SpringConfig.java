package es.netmind.banana_invoices.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "es.netmind.banana_invoices")
//@Import({ReposConfig.class, ServicesConfig.class})
public class SpringConfig {


}
