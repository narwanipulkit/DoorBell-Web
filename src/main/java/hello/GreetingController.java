package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import java.util.*;
import java.io.*;

@Controller
public class GreetingController {

    //private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public String readFile() {
    	String s="on";
    	try{
	    	FileReader reader=new FileReader("src/main/resources/static/toggle.txt");
	    	BufferedReader br=new BufferedReader(reader);
	    	s=br.readLine();
	    	br.close();
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
    	return s;
    }

    public void writeFile(String iotStatus){
    	try{
	    	FileWriter writer=new FileWriter("src/main/resources/static/toggle.txt");
	    	BufferedWriter bw=new BufferedWriter(writer);
	    	bw.write(iotStatus);
	    	bw.close();
	    }
	    catch(Exception e){
    		System.out.println(e);
    	}
    }
    
    @RequestMapping("/greeting")
    //public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    public @ResponseBody Greeting greeting(){
    	String status=readFile();
        return new Greeting(counter.incrementAndGet(),
                            status);
    }

    @RequestMapping("/toggle")
    public String ToggleLock(){
    	String current=readFile();
    	if(current.equals("on")){
    		writeFile("off");
    	}
    	else{
    		writeFile("on");
    	}
    	return "redirect:index.html";
    }

    @RequestMapping("/")
    public String redirect(){
    	return "index.html";
    }
    
}