package it.unipi.enPassant.controller.mongoController;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unipi.enPassant.service.AuthenticationService;
import it.unipi.enPassant.service.mongoService.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// Controller for the Player entity
@RestController
@RequestMapping("api/player")
@Tag(name = "Player", description = "Player operations")
public class PlayerController extends GenericUserController {

  // Constructor for the PlayerController
  @Autowired
  public PlayerController(AuthenticationService authservice, DataService dataservice) {
    super(authservice, dataservice);
  }

}
