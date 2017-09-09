package com.myorg.template.templatedemo.controller;

import com.myorg.template.templatedemo.domain.Server;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    Configuration configuration;



    //url sample http://localhost:8080/api/sample?key=45
    @GetMapping("/sample")
    public ResponseEntity<String> sendMail(@RequestParam(value = "key") String key) throws IOException, TemplateException, URISyntaxException {



        Map<String, Object> data = new HashMap<>();


        Server server = new Server();
        server.setName("Freemaker");
        server.setDescription("This is a freemaker server with key " + key);
        server.setImageName("server.png");


        data.put("server", server);
        data.put("title","Hello Server");
        data.put("imagePath",linkTo(Object.class).withSelfRel().getHref() + "/images/" + server.getImageName());



        Template t = configuration.getTemplate("mytemplate.html");

        String readyParsedTemplate = FreeMarkerTemplateUtils
                .processTemplateIntoString(t, data);


        return new ResponseEntity<String>(readyParsedTemplate,HttpStatus.OK);
    }







}
