package com.anthonywoodard.keyshed;

import com.anthonywoodard.keyshed.controller.KeyController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anthony Woodard
 *
 */
public class App {
    
    private static ApplicationContext ctx;
    private static KeyController kController;
    
    
    public static void main( String[] args ) {                        
      ctx = new ClassPathXmlApplicationContext("root-context.xml");
      kController = (KeyController) ctx.getBean("KeyController");
      kController.init();           
    }        
}
