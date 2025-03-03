package gui.Windows;

import gui.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Regle implements EventHandler<ActionEvent> {

    private static VBox parent = new VBox();
    private static Scene scene = new Scene(parent);
    private static boolean lancer = false;


    /**
     * Produire une scene de regle et la mettre dans App.menu. Sets lancer true s'il est false.
     */
    public static void  rule () {
        App.menu.setHeight(500);
        App.menu.setWidth(500);
        parent.setStyle("-fx-background-color: #000000");
        App.menu.setTitle("Regle");

        if(!lancer){
            //bouton pour retourner au menu
            Button menubouton = new Button("Back");
            menubouton.setTranslateX(210);
            menubouton.setTranslateY(400);
            menubouton.setFont(Font.font(20));
            menubouton.setTextFill(javafx.scene.paint.Color.WHITE);
            menubouton.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");


            menubouton.setOnMousePressed(e -> menubouton.setScaleX(1.2));
            menubouton.setOnMouseReleased(e -> menubouton.setScaleX(1.0));
            menubouton.setOnAction(new MenuDemarage());
            parent.getChildren().add(menubouton);



            Text textTouche = new Text();
            String regles = "-Utilisez les fleches du clavier pour vous diriger.";
            regles += "\n\n-Mangez tous les petits points pour passer au niveau suivant.";
            regles += "\n\n-Chaque 70 petit points mangé, il y a un bonus";
            regles += "\nqui apparait pendant 10 seconds.";
            regles += "\n\n-Manger ce bonus donne des points en plus.";
            regles += "\n\n-Chaque 10 000 points, vous gagnez une vie en plus.";
            regles += "\n\n-Mangez des energizers pour manger les fantômes.";
            regles += "\n\n-Si les fantômes vous touchent pendant que vous n'êtes";
            regles += "\npas energized, vous perdez une vie.";
            regles += "\n\n-Vous avez 3 vies au début.";
            regles += "\n\n-Si votre vie est à 0, vous perdez.";
            regles += "\n\n-Have fun!";
            textTouche.setText(regles);
            textTouche.setTranslateX(90);
            textTouche.setTranslateY(0);
            textTouche.setFill(javafx.scene.paint.Color.WHITE);
            parent.getChildren().add(textTouche);

            lancer = true;
        }



        App.menu.setScene(scene);
        App.menu.show();

    }

    /**
     * Event handler which calls rule function
     * @param event the event which occurred
     */
    @Override
    public void handle(ActionEvent event) {
        rule();
    }
}



