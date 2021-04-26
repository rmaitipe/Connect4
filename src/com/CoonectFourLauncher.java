package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.bean.Game;

/*
 *   Concepts revisited
 *   Java is considered "row major", meaning that it does rows first. This is because a 2D array is an "array of arrays".
 *   Swing is heavily outdated in terms of decoupling application logic
 *   How to pass values from swing objects
 */
@SpringBootApplication
public class CoonectFourLauncher{
  
	
  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(CoonectFourLauncher.class, args);
    Game game = context.getBean(Game.class);
    game.start();
  }

}