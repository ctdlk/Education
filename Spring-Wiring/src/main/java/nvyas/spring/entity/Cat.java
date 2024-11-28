package nvyas.spring.entity;

import org.springframework.stereotype.Component;

@Component("catBean")
public class Cat extends Animal{
    @Override
    public String toString(){
        return "I'm a cat!";
    }
}
