package bojankosta.helicopter.controller;

import bojankosta.helicopter.domain.Player;
import bojankosta.helicopter.domain.Score;
import bojankosta.helicopter.domain.ScoreBoard;
import bojankosta.helicopter.service.PlayerService;
import bojankosta.helicopter.service.ScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping
public class UserController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ScoreBoardService scoreBoardService;

    @GetMapping("/user")
    public Player getUser(Principal principal) {
    	return playerService.getPlayer(principal.getName());
    }
    
    @PostMapping ("/player")
    public Player savePlayer (@RequestBody Player player) {
        return playerService.savePlayer(player);
    }

    @PostMapping("/add")
    public Player addNumber(Principal user) {
    	Player player = playerService.getPlayer(user.getName());
    	int numOfPlay = player.getNumberOfPlaying();
    	numOfPlay++;
    	player.setNumberOfPlaying(numOfPlay);
    	return playerService.savePlayer(player);
    }
    @PostMapping("/win")
    public Player addWin(Principal user) {
    	Player player = playerService.getPlayer(user.getName());
    	int tempWin = player.getWins();
    	tempWin++;
    	player.setWins(tempWin);
    	return playerService.savePlayer(player);
    }
    
    
    @PostMapping("/playing")
    public Player userName (@RequestBody Score score, Principal user) {

        Player player = playerService.getPlayer(user.getName());
        if(player.getBestScore() < score.getScore()){
            player.setBestScore(score.getScore());
            if(player.getBestScore() > 19 && player.getBestScore() < 35) {
            	player.setHighestLevel(2);
            }
            else  if(player.getBestScore() > 34 && player.getBestScore() < 55) {
            	player.setHighestLevel(3);
            }
            else  if(player.getBestScore() > 54 && player.getBestScore() < 75) {
            	player.setHighestLevel(4);
            }
            else  if(player.getBestScore() > 74 && player.getBestScore() < 95) {
            	player.setHighestLevel(5);
            }
            else  if(player.getBestScore() > 94 && player.getBestScore() < 115) {
            	player.setHighestLevel(6);
            }
            else  if(player.getBestScore() > 114) {
            	player.setHighestLevel(7);
            } else {
            	player.setHighestLevel(1);
            }
        }
        return playerService.savePlayer(player);

    }

    @PostMapping ("/score")
    public ScoreBoard saveScore(@RequestBody Score score, Principal user) {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setScore(score.getScore());
        scoreBoard.setName(user.getName());

        return scoreBoardService.save(scoreBoard);

    }

    @GetMapping("/getscore")
    public ArrayList<ScoreBoard> getScoreBoard () {

        return scoreBoardService.getScoreBoard();
    }
}
