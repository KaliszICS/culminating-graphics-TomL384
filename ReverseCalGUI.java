import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReverseCalGUI extends Application {

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
                int rounds = Integer.parseInt(roundsText);
                double base = Double.parseDouble(baseText);
                int total = Integer.parseInt(totalText);

                if (rounds < 1) {
                    statusLabel.setText("Rounds must be at least 1.");
                    return;
                }

                if (total < 2) {
                    statusLabel.setText("Numbers used must be at least 2.");
                    return;
                }

                statusLabel.setText(
                        "Game started!\n" +
                        "Rounds: " + rounds +
                        "\nBase: " + base +
                        "\nNumbers: " + total +
                        "\nOperation: " + operationBox.getValue()
                );

            } catch (NumberFormatException ex) {
                statusLabel.setText("Please enter valid numbers.");
            }
        });

        submitButton.setOnAction(e -> {
            String answerText = answerField.getText().trim();

            if (answerText.isEmpty()) {
                statusLabel.setText("Please fill the answer box.");
                return;
            }
            try {
                double playerAnswer = Double.parseDouble(answerField.getText());

                if (playerAnswer == ){
                    score ++;
				    result.setText(("\nCongrats! This is the correct answer!\n"));
            }
			else{
				result.setText("\nThis is incorrect, the correct answer is " +  + "\n");
			}

                status1Label.setText(
                        "Result " + result +
                        "\n"
                );

            } catch (NumberFormatException ex) {
                statusLabel.setText("Please enter valid numbers.");
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
                submitButton
                
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