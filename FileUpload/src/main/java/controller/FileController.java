package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Exception.GlobalDefaultExceptionHandler;

@Controller
public class FileController {
	
	private String fileName = null;
	private FileInputStream input = null;
	public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";
    
	//access to the start page.
	@GetMapping("/")
	public String index(){
		return "uploadpage";
	}
	
	@GetMapping("/uploadpage")
    public String uploadpage() {
        return "uploadpage";
    }
	
	//select and upload file from client side.
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes model) throws IOException{
		if(file.isEmpty()){
			model.addFlashAttribute("message", "the file must not be empty");
			return "redirect:uploadpage";
		}
		
		fileName = file.getOriginalFilename();		
		File upload = new File(uploadingdir + file.getOriginalFilename());
		file.transferTo(upload);
		
		model.addFlashAttribute("message", "the file "+file.getOriginalFilename()+ " has been uploaded successfully ");
		model.addFlashAttribute("signal","successfully");
		return "redirect:uploadpage";
	}
	
	@GetMapping("/read")
	public String readFile(Model model) throws IOException{
		StringBuffer sb = new StringBuffer();
		try{
		input = new FileInputStream(new File(uploadingdir + fileName));
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        decoder.onMalformedInput(CodingErrorAction.IGNORE);
        InputStreamReader reader = new InputStreamReader(input,decoder);
        BufferedReader bufferedReader = new BufferedReader( reader );
        String line = bufferedReader.readLine();
        while( line != null ) {
            sb.append( line );
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
		}catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch( IOException e ) {
	        e.printStackTrace();
	    }		
		
        
		System.out.println(sb.toString());
		model.addAttribute("content", sb.toString());
		return "uploadpage";		
	}
	
	
}
