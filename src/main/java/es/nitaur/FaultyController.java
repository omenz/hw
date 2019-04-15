package es.nitaur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class FaultyController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @GetMapping("/api/faulty-api/{id}")
    public ResponseEntity<FaultyResponseModel> getQuizzes(@PathVariable("id") final Long id) {
        if (id % 2 == 0) {
            return new ResponseEntity<FaultyResponseModel>(new FaultyResponseModel("SUCCESS", "I'm good. Thanks"), HttpStatus.OK);
        }
        return new ResponseEntity<FaultyResponseModel>(new FaultyResponseModel("ERROR", "The error has occured but I'm normal error message"), HttpStatus.BAD_REQUEST);
    }

}
