package nvyas.spring.fairytale.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ocean")
public class Ocean1 {

    @Autowired
    private Island2 island;

    @Override
    public String toString(){
        return "на океане остров " + island.toString();
    }
}
