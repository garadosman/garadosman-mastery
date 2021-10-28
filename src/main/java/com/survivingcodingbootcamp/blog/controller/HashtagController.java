package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.storage.PostStorage;
import com.survivingcodingbootcamp.blog.storage.HashtagStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hashtags")
public class HashtagController {
    private HashtagStorage hashtagStorage;
    private PostStorage postStorage;
    @Autowired
    public HashtagController(HashtagStorage hashtagStorage, PostStorage postStorage){
        this.hashtagStorage = hashtagStorage;
        this.postStorage = postStorage;
    }
    @GetMapping("/{id}")
    public  String displayingSingleHashtag(@PathVariable Long id, Model model){
        model.addAttribute("hashtag", hashtagStorage.findById(id));
        return "single-hashtag-template";
    }
    @GetMapping("")
    public  String displayingAllHashtag( Model model){
        model.addAttribute("hashtags", hashtagStorage.retrieveAllHashtags());
        return "all-hashtags-template";
    }

    @PostMapping("/add/{id}")
    public String addHashtag(@PathVariable Long id, @RequestParam String hashtag){
        if(hashtag.charAt(0) != '#'){
            hashtag = "#" + hashtag;
        }
        Post postToGiveHashtag = postStorage.retrievePostById(id);
        Hashtag createdHashtag = hashtagStorage.createOrRetrieveHashtag(hashtag);
        if (!createdHashtag.getPosts().contains(postToGiveHashtag)){
            createdHashtag.addPost(postToGiveHashtag);
            hashtagStorage.save(createdHashtag);
            postToGiveHashtag.addHashtag(createdHashtag);
            postStorage.save(postToGiveHashtag);
        }
//        model.addAttribute("post", postToGiveHashtag);
        return "redirect:/posts/" + id;
    }


}