package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

@Controller
public class HelloController {

	@Value(value = "${file.upload.stor-path}")
	private String dir;
	
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
		
		byte[] bytes = file.getBytes();
		Path path = Paths.get(dir+file.getOriginalFilename());
		Files.write(path, bytes);
		model.addFlashAttribute("message", "the file "+file.getOriginalFilename()+ " has been uploaded successfully ");
		return "redirect:uploadpage";
	}
}
