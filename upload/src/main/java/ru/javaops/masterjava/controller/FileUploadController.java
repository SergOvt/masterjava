package ru.javaops.masterjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javaops.masterjava.util.JaxbParser;
import ru.javaops.masterjava.util.StaxStreamProcessor;
import ru.javaops.masterjava.xml.schema.User;

import javax.xml.stream.events.XMLEvent;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


@Controller
public class FileUploadController {

    private final Set<User> users;

    public FileUploadController() {
        users = new TreeSet<>(Comparator.comparing(User::getValue).thenComparing(User::getEmail));
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) {
        model.addAttribute("users", users);
        return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
       try (StaxStreamProcessor processor = new StaxStreamProcessor(file.getInputStream())) {
           JaxbParser parser = new JaxbParser(User.class);
           while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
               users.add(parser.unmarshal(processor.getReader(), User.class));
           }
           redirectAttributes.addFlashAttribute("message",
                   "You successfully uploaded " + file.getOriginalFilename() + "!");
       } catch (Exception e) {
           throw new IllegalStateException(e);
       }
        return "redirect:/";
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleStorageFileNotFound(IllegalStateException exc) {
        return ResponseEntity.notFound().build();
    }
}
