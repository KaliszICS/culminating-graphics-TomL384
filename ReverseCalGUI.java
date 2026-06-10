import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReverseCalGUI extends Application {
    private int rounds;
    private int currentRound;
    private int total;
    private double base;
    private int operationtype;
    private double correctAnswer;
    private int score = 0;

    private String generateQuestion() {
            // my existing code here
            Random r = new Random();
            int mixed = 0;
			String questionString = "Question" + currentRound + ": " + base;
            ArrayDeque<Double> numbers = new ArrayDeque<>();
            ArrayDeque<Character> operators = new ArrayDeque<>();
            numbers.push(base);

			for (int i1 = 0; i1 < total - 1; i1++){
				double randomNum = -10000 + r.nextInt(20001);
				while(randomNum == 0){
					randomNum = -10000 + r.nextInt(20001);
				}

				numbers.push(randomNum);
				mixed = 1 + r.nextInt(4);
				if (operationtype != 5){
					mixed = 0;
				}

				if (operationtype == 1 || mixed == 1){
					operators.push('+');
					questionString = questionString + " + " + randomNum ;
				}
				if (operationtype == 2 || mixed == 2){
					operators.push('-');
					questionString = questionString + " - " + randomNum ;
				}

				double top1 = numbers.peek();
				if (operationtype == 3 || mixed == 3){
					questionString = questionString + " * " + randomNum ;
					numbers.pop();
					double top2 = numbers.peek();
					numbers.pop();
					numbers.push(top2*top1);
				}
				if (operationtype == 4 || mixed == 4){
					questionString = questionString + " / " + randomNum;
					numbers.pop();
					double top2 = numbers.peek();
					numbers.pop();
					numbers.push(top2/top1);
				}
			}	
			
			while(numbers.size()>1){
				Character operator = operators.peekLast();
				double top1 = numbers.peekLast();
				if (operator == '+'){
					numbers.removeLast();
					double top2 = numbers.peekLast();
					numbers.removeLast();
					numbers.addLast(top1 + top2);	
				}

				if (operator == '-'){
					numbers.removeLast();
					double top2 = numbers.peekLast();
					numbers.removeLast();
					numbers.addLast(top1 - top2);	
						
				}
				operators.removeLast();
			}
					
			questionString = questionString + " = ?";
			
			double answer = numbers.peek();
            answer = Math.round(answer * 1000.0) / 1000.0;
			String reversepolarity = "" ;
			if (answer > 0){
				reversepolarity = "-" ;
			}
			if (answer < 0){
				answer = answer * -1;
			}
            DecimalFormat df = new DecimalFormat("0.###");
            String ans = df.format(answer);

			if (ans.endsWith(".0")) {
				ans = ans.substring(0, ans.length() - 2);
			}
			
			String reverseans = "" + reversepolarity;
			for (int i2 = ans.length()-1 ; i2 >= 0 ; i2 --){
				reverseans = reverseans + ans.charAt(i2);
			}
			double reverseans1 = Double.parseDouble(reverseans);
            correctAnswer = reverseans1;
            
            return questionString;
        }

    @Override
    public void start(Stage stage) {

        Label title = new Label("Reverse-Cal Game");

        TextField roundsField = new TextField();
        roundsField.setPromptText("Number of rounds");

        TextField baseField = new TextField();
        baseField.setPromptText("Base number");

        TextField totalField = new TextField();
        totalField.setPromptText("Numbers used per question");

        ComboBox<String> operationBox = new ComboBox<>();
        operationBox.getItems().addAll(
                "Addition",
                "Subtraction",
                "Multiplication",
                "Division",
                "Mixed"
        );
        operationBox.setValue("Mixed");

        Button startButton = new Button("Start Game");

        Label statusLabel = new Label();

        Label question = new Label("");

        TextField answerField = new TextField();

        answerField.setPromptText("Please enter your answer");

        Button submitButton = new Button("Submit");

        Label status1Label = new Label();

        Label result = new Label("");

        Label scoreLabel = new Label("Score: 0");

        startButton.setOnAction(e -> {
            String roundsText = roundsField.getText().trim();
            String baseText = baseField.getText().trim();
            String totalText = totalField.getText().trim();
            

            if (roundsText.isEmpty() || baseText.isEmpty() || totalText.isEmpty()) {
                statusLabel.setText("Please fill in all fields.");
                return;
            }
            try {
                rounds = Integer.parseInt(roundsField.getText());
                base = Double.parseDouble(baseField.getText());
                total = Integer.parseInt(totalField.getText());
                operationtype = operationBox.getSelectionModel().getSelectedIndex() + 1;


                if (rounds < 1) {
                    statusLabel.setText("Rounds must be at least 1.");
                    return;
                }

                if (total < 2) {
                    statusLabel.setText("Numbers used must be at least 2.");
                    return;
                }

                currentRound = 1;
                score = 0;

                statusLabel.setText(
                        "Game started!\n" +
                        "Rounds: " + rounds +
                        "\nBase: " + base +
                        "\nNumbers: " + total +
                        "\nOperation: " + operationBox.getValue()
                );

                // generate first question
                question.setText(generateQuestion());

            } catch (NumberFormatException ex) {
                statusLabel.setText("Please enter valid numbers.");
            }
        });
        
        submitButton.setOnAction(e -> {
            String answerText = answerField.getText().trim();
            if (answerText.isEmpty()) {
                status1Label.setText("Please fill the answer box.");
                return;
            }
            try {
                double playerAnswer = Double.parseDouble(answerText);
                if (playerAnswer == correctAnswer) {
                    score++;
                    result.setText("Correct!");
                    scoreLabel.setText("Score: " + score);
                } 
                else {
                    result.setText("Incorrect! Correct answer: " + correctAnswer);
                }
                answerField.clear();

                currentRound++;

                if (currentRound <= rounds) {
                    question.setText(generateQuestion());
                } 
                else {
                    question.setText("Game finished!");
                }
 

            } catch (NumberFormatException ex) {
                status1Label.setText("Please enter a valid number.");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        root.getChildren().addAll(
                title,
                roundsField,
                baseField,
                totalField,
                operationBox,
                startButton,
                statusLabel,
                question,
                answerField,
                submitButton,
                status1Label,
                result,
                scoreLabel
                
        );

        Scene scene = new Scene(root, 400, 350);

        stage.setTitle("Reverse-Cal");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}