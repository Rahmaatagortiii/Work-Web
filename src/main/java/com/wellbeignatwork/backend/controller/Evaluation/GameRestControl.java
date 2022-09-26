package com.wellbeignatwork.backend.controller.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Game;
import com.wellbeignatwork.backend.entity.Evaluation.GamePlay;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.service.Evaluation.GameService;
import com.wellbeignatwork.backend.exceptions.Evaluation.GameNotFound;
import com.wellbeignatwork.backend.exceptions.Evaluation.InvalidGameException;
import com.wellbeignatwork.backend.exceptions.Evaluation.InvalidParamException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Game")
@Slf4j
@AllArgsConstructor

public class GameRestControl {
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;



    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody User player) {
        log.info("start game request: {}", player);
        return ResponseEntity.ok(gameService.createGame(player));
    }

    @PostMapping("/connect")
    public ResponseEntity<Game> connectToGame(@RequestBody ConnectRequest request) throws InvalidParamException, InvalidGameException {
        log.info("connect request{}",request);
        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(),request.getGameId()));
    }

    @PostMapping("/connectInRandomMode/random")
    public  ResponseEntity<Game> connectToRandomGame(@RequestBody User player) throws GameNotFound {
        log.info("connect random {}", player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));

    }
    @PostMapping("/GamePlay")
    public ResponseEntity<Game> GamePlay(@RequestBody GamePlay request) throws GameNotFound, InvalidGameException {
        log.info("gameplay {}",request);
        Game game=gameService.GamePlay(request);
        simpMessagingTemplate.convertAndSend("/topic/game-progress"+game.getGameId(),game);
        return ResponseEntity.ok(game);
    }




}
