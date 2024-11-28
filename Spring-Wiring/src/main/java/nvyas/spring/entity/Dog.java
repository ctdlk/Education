package nvyas.spring.entity;

import org.springframework.stereotype.Component;

@Component("dogBean")
public class Dog extends Animal{
    @Override
    public String toString(){
        return "I'm a dog!";
    }
}
