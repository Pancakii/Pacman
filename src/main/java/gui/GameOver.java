package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MazeState;

public class GameOver  {

        public static void affichageGameOver(){
        //Initialisation du nouvel ecran
        VBox parent = new VBox();
        Stage ecran = new Stage();
        Label lab = new Label();
        Scene scene = new Scene(lab);
        ecran.setHeight(500);
        ecran.setWidth(500);
        ecran.setScene(scene);
        parent.setStyle("-fx-background-color: #000000");

        // Ajouter un nom a la page
        ecran.setTitle("GameOver");
        // Ajouter une icone a la page`
        Image icon = new Image("pacman.png");
        ecran.getIcons().add(icon);
        // Ne plus pouvoir aggrandir/retrecir la page
        ecran.setResizable(false);


        //affiche le score apres le game over
        Text score = new Text();
        score.setText("Score: " + MazeState.score);
        score.setTranslateX(125);
        score.setTranslateY(100);//La ou on place le score
        score.setFont(Font.font(20)); //La taille de l'affichage
        score.setFill(Color.WHITE); //La couleur
        parent.getChildren().add(score);

        //affichage du mesage GameOver
        Text go = new Text();
        go.setText("Game Over!");
        go.setTranslateX(125);
        go.setTranslateY(20);
        go.setFont(Font.font(50));
        go.setFill(Color.YELLOW);
        parent.getChildren().add(go);

        //bouton pour rejouer
        Button bouton = new Button("Retry"); //Le nom du bouton
        bouton.setTranslateX(210);//Les coordonnées du bouton
        bouton.setTranslateY(150);
        bouton.setFont(Font.font(20));
        bouton.setOnAction(new Jeu());
        parent.getChildren().add(bouton);

        //bouton pour retourner au menu
        Button menubouton = new Button("Menu");
        menubouton.setTranslateX(210);
        menubouton.setTranslateY(170);
        menubouton.setFont(Font.font(20));
        menubouton.setOnAction(new Menu());
        parent.getChildren().add(menubouton);

        //bouton pour quitter tout lle programme
        Button exitbouton = new Button(" Exit  ");
        exitbouton.setTranslateX(210);
        exitbouton.setTranslateY(190);
        exitbouton.setFont(Font.font(20));
        exitbouton.setOnAction(new ExitButon());
        parent.getChildren().add(exitbouton);

        ecran.setScene(new Scene(parent));
        ecran.show();

    }





}
