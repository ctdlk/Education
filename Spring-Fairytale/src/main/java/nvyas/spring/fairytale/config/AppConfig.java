package nvyas.spring.fairytale.config;

import nvyas.spring.fairytale.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "nvyas.spring.fairytale")
public class AppConfig {

    @Bean(name = "island")
    public static Island2 getIsland(Wood3 wood){
        return new Island2(wood);
    }

    @Bean(name = "duck")
    public static Duck5 getDuck(Egg6 egg){
        return new Duck5(egg);
    }

    @Bean(name = "needle")
    public static Needle7 getNeedle(Death8 death){
        return new Needle7(death);
    }
}
