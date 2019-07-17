package quiz;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import controllers.GameScreenPlayController;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    private int round = 1;
    private int score;
    private String correctAnswer;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private int rowsInBooks;
    private int rowsInPeople;

    public Game(){
        conn = DBConnection.connect();
        rowsInBooks = setRowsInBooks();
        rowsInPeople = setRowsInPeople();
        System.out.println(rowsInBooks);
        System.out.println(rowsInPeople);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getScore() {
        return score;
    }

    public int getRowsInBooks() {
        return rowsInBooks;
    }

    public int getRowsInPeople() {
        return rowsInPeople;
    }

    public int setRowsInBooks(){
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM books;" );
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int setRowsInPeople(){
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM people;" );
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // Metgod for getting random question and 4 unique answers from data base.
    public ArrayList<String> getRandomQuestion(String category, int rowsNumber, GameScreenPlayController controller) {

        System.out.println("start random question");

        Random random = new Random();
        ArrayList<String> quoteAnswers = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();

        int i = random.nextInt(rowsNumber) + 1;

        try {
            String sql = "SELECT quote FROM " + category + " WHERE id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, i);
            ResultSet rs = pstmt.executeQuery();
            quoteAnswers.add(rs.getString(1));

            sql = "SELECT answer FROM " + category + " WHERE id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, i);
            rs = pstmt.executeQuery();
            correctAnswer = rs.getString(1);
            answers.add(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (answers.size() < 4) {

            i = random.nextInt(rowsNumber) + 1;
            System.out.println("rozmiar answers: " + answers.size());
            System.out.println(i);

            try {
                String sql = "SELECT answer FROM " + category + " WHERE id = ?;";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, i);
                ResultSet rs = pstmt.executeQuery();

                if (!answers.contains(rs.getString(1))) {
                    answers.add(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        Collections.shuffle(answers);
        quoteAnswers.addAll(answers);

        System.out.println(quoteAnswers.toString());
        controller.showQuestion(quoteAnswers.get(0), quoteAnswers.get(1), quoteAnswers.get(2), quoteAnswers.get(3), quoteAnswers.get(4));
        return quoteAnswers;
    }


    public boolean checkAnswer(String answer, GameScreenPlayController controller){

        if(answer.equals(correctAnswer)){
            score += 1;
            controller.setQuestionLabelText("Dobra odpowiedź :)");
            controller.setScoreLabelText("Wynik: " + score);
            return true;
        }else{
            controller.setQuestionLabelText("Zła odpowiedź :( \n\nPrawidłowa to: " + correctAnswer);
            return false;
        }
    }

    public List<Score> saveScore(){

        // Create table for first score save
        try {
            String sql = "CREATE TABLE IF NOT EXISTS scores(" +
                    "id INT," +
                    "score INT," +
                    "player STRING)";
            stmt = conn.createStatement();
            stmt.execute(sql);

            sql = "SELECT id FROM scores";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.next()){
                sql = "INSERT INTO scores(id, score, player) VALUES(?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                for(int i = 1; i <=10; i++){
                    pstmt.setInt(1, i);
                    pstmt.setInt(2, 0);
                    pstmt.setString(3, "");
                    pstmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Add new score to score table
        List<Score> scores = new ArrayList<>();

        try {
            String sql = "SELECT score, player FROM scores";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Score sc = new Score(rs.getInt("score"), rs.getString("player"));
                scores.add(sc);
            }

            // If score is higher or equal to present best score
            if(score >= scores.get(0).getScore()){
                DialogWindows.dialogBestScore();
            }

            // Add new score to array
            scores.add(new Score(score, Player.name));

            // Sorting scores using Guava
            Ordering<Score> orderingByScore = new Ordering<>() {
                @Override
                public int compare(Score s1, Score s2) {
                    return Ints.compare(s1.getScore(), s2.getScore());
                }
            };

            scores.sort(orderingByScore);
            Collections.reverse(scores);

            sql = "UPDATE scores SET " +
                    "score = ?, " +
                    "player = ? " +
                    "WHERE id = ?";

            pstmt = conn.prepareStatement(sql);
            int i =1;
            for(Score score: scores){
                pstmt.setInt(1, score.getScore());
                pstmt.setString(2, score.getName());
                pstmt.setInt(3, i++);
                pstmt.executeUpdate();
            }

            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();

            return scores;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Score> showBestScores(){

        List<Score> scores = new ArrayList<>();
        DatabaseMetaData dbm = null;

        try {
            // Before add new score check if score table exists
            dbm = conn.getMetaData();
            ResultSet rs = dbm.getTables(null, null, "scores", null);
            if (rs.next()) {
                System.out.println("Table exists");

                String sql = "SELECT score, player FROM scores";
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(sql);
                while(rs1.next()){
                    Score sc = new Score(rs1.getInt("score"), rs1.getString("player"));
                    scores.add(sc);
                }

                rs1.close();

            } else {
                System.out.println("Table does not exist");
            }

                rs.close();
                stmt.close();
                conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }

}
