package chess;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.domain.board.Board;
import chess.dto.LineDto;
import chess.service.ChessService;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class WebUIChessApplication {

    public static void main(String[] args) {

        Spark.staticFiles.location("/templates");

        ChessService chessService = new ChessService();

        get("/main", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Board board = chessService.createEmpty();
            List<LineDto> rows = board.getRows();
            model.put("rows", rows);
            return render(model, "main.html");
        });

        post("/start", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String firstUserName = req.queryParams("user1");
            String secondUserName = req.queryParams("user2");
            Board board = chessService.placeInitialPieces();
            List<LineDto> rows = board.getRows();
            model.put("firstUser", firstUserName);
            model.put("secondUser", secondUserName);
            model.put("rows", rows);
            return render(model, "board.html");
        });

        post("/status", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return render(model, "status.html");
        });

        post("/end", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Board board = chessService.createEmpty();
            List<LineDto> rows = board.getRows();
            model.put("rows", rows);
            return render(model, "main.html");
        });
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}