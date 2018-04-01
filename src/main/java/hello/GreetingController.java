package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
    
    @RequestMapping(value="/upload", method=POST)
    public String imageUpload(@RequestParam("file")MultipartFile file){
        if(!file.isEmpty()){
            try{
                    byte[] fileBytes = file.getBytes();
                    Storage.save(fileBytes);
                    return "File Upload Successfull";

            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return "ERROR";
    }


    @RequestMapping("/greeting")
    //public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    public @ResponseBody Greeting greeting(){
    	String status=readFile();
        return new Greeting(counter.incrementAndGet(),
                            status);
    }

    @RequestMapping("/video")
    public String openVid(){
        return "vid.html";
    }

    @RequestMapping("/toggle")
    public String toggleLock(){
    	String current=readFile();
    	if(current.equals("on")){
    		writeFile("off");
    	}
    	else{
    		writeFile("on");
    	}
    	return "redirect:index.html";
    }

    @RequestMapping(value="/motion",method=POST)
    public @ResponseBody String motion(@RequestParam(value="detected")String det, @RequestParam(value="faces")String faces){
        new FirebaseServe(det,faces);
        return "OK";
    }

    @RequestMapping("/")
    public String redirect(){
    	return "index.html";
    }


    
    
}