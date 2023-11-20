package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MazeState;

public class GameOver  {

        public static void affichageGameOver(Stage m){
                App.menu = m;
                //Initialisation du nouvel ecran
                VBox parent = new VBox();
                Label lab = new Label();
                Scene scene = new Scene(lab);
                App.menu.setHeight(500);
                App.menu.setWidth(500);
                App.menu.setScene(scene);
                //Il y a du CSS dans le setStyke, lien de l'image, est ce que ca se repete, la taille et la position
                parent.setStyle("-fx-background-image: url('GOB.jpeg'); -fx-background-repeat: no-repeat;-fx-background-size: 500 500;-fx-background-position: center center;");
                // Ajouter un nom a la page
                App.menu.setTitle("GameOver");
                // Ne plus pouvoir aggrandir/retrecir la page
                App.menu.setResizable(false);


                //affiche le score apres le game over
                Text score = new Text();
                score.setText("Score: " + MazeState.score);
                score.setTranslateX(125);
                score.setTranslateY(100);//La ou on place le score
                score.setFont(Font.font(20)); //La taille de l'affichage du score
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
                menubouton.setOnAction(new MenuDemarage());
                parent.getChildren().add(menubouton);

                //bouton pour quitter tout lle programme
                Button exitbouton = new Button(" Exit  ");
                exitbouton.setTranslateX(210);
                exitbouton.setTranslateY(190);
                exitbouton.setFont(Font.font(20));
                exitbouton.setOnAction(new ExitButon());
                parent.getChildren().add(exitbouton);

                App.menu.setScene(new Scene(parent));
                App.menu.show();

    }


}
