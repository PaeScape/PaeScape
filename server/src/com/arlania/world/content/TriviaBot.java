package com.arlania.world.content;

import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

/*
 * @author Arlania - Arlania rsps
 */
public class TriviaBot {

    public static final int TIMER = 5400; //1800
    public static int botTimer = TIMER;

    public static int answerCount;
    public static String firstPlace;
    public static String secondPlace;
    public static String thirdPlace;

    //public static List<String> attempts = new ArrayList<>();

    public static void sequence() {

        if (botTimer > 0)
            botTimer--;
        if (botTimer <= 0) {
            botTimer = TIMER;
            didSend = false;
            askQuestion();
        }
    }

    public static void attemptAnswer(Player p, String attempt) {

        if (!currentQuestion.equals("") && attempt.replaceAll("_", " ").equalsIgnoreCase(currentAnswer)) {

            if (answerCount == 0) {
                answerCount++;
                p.getPacketSender().sendMessage("You Recieved 3 Vote Scrolls and Trivia Points for @red@1st Place.");
                p.trivia += 3;
                p.getPointsHandler().setTriviaPoints(3, true);
                firstPlace = p.getUsername();
                return;
            }
            if (answerCount == 1) {
                if (p.getUsername().equalsIgnoreCase(firstPlace)) {
                    p.getPacketSender().sendMessage("Already answered");
                    return;
                }
                answerCount++;
                //p.getPointsHandler().incrementTriviaPoints(6);
                //p.getPacketSender().sendMessage("You Recieved 6 trivia points for @red@2nd Place.");
                //p.getPointsHandler().refreshPanel();
                p.getPacketSender().sendMessage("You Recieved 2 Vote Scrolls and Trivia Points for @red@2nd Place.");
                p.trivia += 2;
                p.getPointsHandler().setTriviaPoints(2, true);
                secondPlace = p.getUsername();
                return;

            }
            if (answerCount == 2) {
                if (p.getUsername().equalsIgnoreCase(firstPlace) || p.getUsername().equalsIgnoreCase(secondPlace)) {
                    p.getPacketSender().sendMessage("Already answered");
                    return;
                }
                //p.getPointsHandler().incrementTriviaPoints(4);
                //p.getPacketSender().sendMessage("You Recieved 4 trivia points for @red@3rd Place.");
                //p.getPointsHandler().refreshPanel();
                p.getPacketSender().sendMessage("You Recieved 1 Vote Scrolls and Trivia Points for @red@3rd Place.");
                p.trivia += 1;
                p.getPointsHandler().setTriviaPoints(1, true);
                thirdPlace = p.getUsername();
                World.sendMessage("minigames", "@blu@[Trivia] @bla@[1st:@blu@" + firstPlace + "@red@ (3 scrolls)@bla@] @bla@[2nd:@blu@" + secondPlace + "@red@ (2 scrolls)@bla@] [3rd:@blu@" + thirdPlace + "@red@  (1 scroll)@bla@]");
                //String[] s = Arrays.asList(attempts);
                //World.sendMessage("@blu@[Trivia] @red@Failed attempts: "+s);
                currentQuestion = "";
                didSend = false;
                botTimer = TIMER;
                answerCount = 0;
            }
        } else {
            if (attempt.contains("question") || attempt.contains("repeat")) {
                p.getPacketSender().sendMessage("<col=800000>" + (currentQuestion));
                return;
            }

            //attempts.add(attempt); // need to add a filter for bad strings (advs, curses)
            p.getPacketSender().sendMessage("@blu@[Trivia]@red@ Sorry! Wrong answer! The current question is: ");
            p.getPacketSender().sendMessage("@blu@[Trivia]@red@ " + (currentQuestion));
        }

    }

    public static boolean acceptingQuestion() {
        return !currentQuestion.equals("");
    }

    private static void askQuestion() {
        for (int i = 0; i < Trivia_DATA.length; i++) {
            if (RandomUtility.inclusiveRandom(Trivia_DATA.length - 1) == i) {
                if (!didSend) {
                    didSend = true;
                    currentQuestion = Trivia_DATA[i][0];
                    currentAnswer = Trivia_DATA[i][1];
                    World.sendMessage("minigames", currentQuestion);
                    firstPlace = "";
                    secondPlace = "";
                    thirdPlace = "";
                    answerCount = 0;


                }
            }
        }
    }

    public static boolean didSend = false;

    private static final String[][] Trivia_DATA = {
            {"@blu@[Trivia]@red@ What rank has a silver crown on Runescape?", "Moderator"},
            {"@blu@[Trivia]@red@ What rank has a golden crown on Runescape?", "Administrator"},
            {"@blu@[Trivia]@red@ How much exp. do you need for level 99?", "13M"},
            {"@blu@[Trivia]@red@ How much exp. do you need for Dungeonering level 120 on Runescape?", "104M"},
            {"@blu@[Trivia]@red@ What is the largest state in the U.S.A?", "Alaska"},
            {"@blu@[Trivia]@red@ What city is the most populated city on earth?", "Tokyo"},
            {"@blu@[Trivia]@red@ What is the strongest CURSE on Runescape?", "Turmoil"},
            {"@blu@[Trivia]@red@ What Herblore level is required to make overloads on Runescape?", "96"},
            {"@blu@[Trivia]@red@ What attack level is required to wear Chaotic Melee weapons?", "80"},
            {"@blu@[Trivia]@red@ How many bones are there in an adult human body?", "206"},
            {"@blu@[Trivia]@red@ What is the deadliest insect on the planet?", "Mosquito"},
            {"@blu@[Trivia]@red@ What is the square root of 12 to the power of 2?", "12"},
            {"@blu@[Trivia]@red@ What is the color of a 10M money stack?", "Green"},
            {"@blu@[Trivia]@red@ What combat level is the almighty Jad?", "702"},
            {"@blu@[Trivia]@red@ How many brothers are there originally in Runescape?", "6"},
            {"@blu@[Trivia]@red@ Which NPC is wearing a 2H-Sword and a Dragon SQ Shield?", "Vannaka"},
            {"@blu@[Trivia]@red@ What is the baby spider called?", "Spiderling"},
            {"@blu@[Trivia]@red@ The more you take, the more you leave, who am I?", "Footsteps"},
            {"@blu@[Trivia]@red@ What is 9 + 10?", "19"},
            {"@blu@[Trivia]@red@ What magic level is required to cast fire bolt?", "35"}
    };

    public static String currentQuestion;
    private static String currentAnswer;
}