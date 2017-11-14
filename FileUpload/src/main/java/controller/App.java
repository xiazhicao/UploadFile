package controller;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App 
{
	public static void main(String[] args) {
		new File(FileController.uploadingdir).mkdirs();
        SpringApplication.run(App.class, args);
        
        
    }
}
