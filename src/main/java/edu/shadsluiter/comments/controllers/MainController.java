package edu.shadsluiter.comments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.shadsluiter.comments.data.CommentsDAO;

@Controller
public class MainController {

    // Simple in-memory DAO instance for now
    private final CommentsDAO commentsDAO = new CommentsDAO();

    // Show the page with the list of comments
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("comments", commentsDAO.getComments());
        return "index"; // resolves to src/main/resources/templates/index.html
    }

    // Handle new comment submission
    @PostMapping("/submitComment")
    public String submitComment(String author, String content) {
        commentsDAO.addComment(author, content);

        // PRG pattern: redirect so refresh doesn’t resubmit the form
        return "redirect:/";
    }

    // Handle search request
    @GetMapping("/searchComments")
    public String searchComments(@RequestParam("searchTerm") String searchTerm, Model model) {
        model.addAttribute("comments", commentsDAO.searchComments(searchTerm));
        model.addAttribute("searchTerm", searchTerm); // optional
        return "index"; // <<— THIS was missing in your broken version
    }
}
