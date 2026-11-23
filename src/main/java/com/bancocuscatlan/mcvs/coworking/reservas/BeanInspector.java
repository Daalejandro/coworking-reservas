package com.bancocuscatlan.mcvs.coworking.reservas;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class BeanInspector implements CommandLineRunner {

    private final ConfigurableApplicationContext context;

    public BeanInspector(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {

        String[] names = context.getBeanNamesForType(
                PasswordEncoder.class
        );

        for (String name : names) {

            BeanDefinition definition = context
                    .getBeanFactory()
                    .getBeanDefinition(name);

            System.out.println("Bean: " + name);
            System.out.println("Clase: " + context.getType(name));
            System.out.println("Definido en: "
                    + definition.getResourceDescription());
            System.out.println("Método fábrica: "
                    + definition.getFactoryMethodName());
            System.out.println("Clase fábrica: "
                    + definition.getFactoryBeanName());
        }
    }
}
