package com.wellbeignatwork.backend.service.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.*;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.storage.GameStorage;
import com.wellbeignatwork.backend.exceptions.Evaluation.GameNotFound;
import com.wellbeignatwork.backend.exceptions.Evaluation.InvalidGameException;
import com.wellbeignatwork.backend.exceptions.Evaluation.InvalidParamException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
@AllArgsConstructor
public class GameService  {


    public Game createGame(User player) {
        Game game = new Game();
        game.setBoard(new int[3][3]);
        game.setGameId(UUID.randomUUID().toString());
        game.setPlayer1(player);
        game.setStatus(GameStatus.NEW);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    public Game connectToGame(User player2, String gameId) throws InvalidParamException, InvalidGameException
    {

        if(! GameStorage.getInstance().getGames().containsKey(gameId))
        {
            throw new InvalidParamException("sorry ! this GameId dosen't exist :( ");
        }
       Game game = GameStorage.getInstance().getGames().get(gameId);

        //if the Game is already contains two players so we throw an execption
        //we cann't add a third player because this game accept only 2 player
        if(game.getPlayer2()!=null)
        {
         throw new InvalidGameException("sorry ! we can not add a third player here,this game accept only 2 players thanks :)");
        }

        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    //player vs randomPlayer
    public Game connectToRandomGame(User player2) throws GameNotFound {
    Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(newGame -> newGame.getStatus().equals(GameStatus.NEW))
                .findFirst()
                .orElseThrow(()->new GameNotFound("Sorry,Game Not Found"));
    game.setPlayer2(player2);
    game.setStatus(GameStatus.IN_PROGRESS);
    GameStorage.getInstance().setGame(game);
    return game;

    }

    public Game GamePlay(GamePlay gamePlay) throws InvalidGameException, GameNotFound {
        if(!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId()))
        {
           throw  new GameNotFound("Game Not Found");
        }

        Game game=GameStorage.getInstance().getGames().get(gamePlay.getGameId());
        if(game.getStatus().equals(GameStatus.FINISHED))
        {
            throw new InvalidGameException("Game is already finished ");
        }
        int [][] board=game.getBoard();
        //the player choose his value 1=x/2=y
        board [gamePlay.getCoordinateX()][gamePlay.getCoordinateY()]=gamePlay.getType().getValue();
        checkWinner(game.getBoard(), TicToe.X);
        checkWinner(game.getBoard(),TicToe.O);
        Boolean xWinner = checkWinner(game.getBoard(), TicToe.X);
        Boolean oWinner = checkWinner(game.getBoard(), TicToe.O);

        if (xWinner) {
            game.setWinner(TicToe.X);
        } else if (oWinner) {
            game.setWinner(TicToe.O);
        }


        GameStorage.getInstance().setGame(game);


        return game;
    }

    private Boolean checkWinner(int[][] board, TicToe ticToe) {

        int [] boardArray= new int[9];
        int countIndex=0;
        //we loop the column and row
        for (int i=0;i<board.length;i++)
        {
            for (int j=0;j<board[i].length;j++)
            {
                //we gonna put the board into the boardArray
                //2 dimension vers 1 dimension
                boardArray[countIndex]=board[i][j];
                countIndex++;
            }
        }
        //the possible combinaition for the winner
        int[][] winCombinations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        for(int i=0;i<winCombinations.length;i++)
        { int counter=0;
            for(int j=0 ; j<winCombinations[i].length;j++)
            {
                if(boardArray[winCombinations[i][j]]==ticToe.getValue())
                {
                    counter++;
                    if (counter==3)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
