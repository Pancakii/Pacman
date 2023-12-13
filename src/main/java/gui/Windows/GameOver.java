package gui.Windows;

import gui.App;
import gui.ExitButon;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MazeState;

public class GameOver  {
        private static VBox parent = new VBox();
        private static Scene scene = new Scene(parent);
        private static Text score =  new Text();
        private static Text go = new Text();
        private static Text sb = new Text();

        private static boolean lancer = false;


        public static void affichageGameOver() {
                final int tw = 500;
                App.menu.setHeight(tw);
                App.menu.setWidth(tw);
                //Il y a du CSS dans le setStyke, lien de l'image, est ce que ca se repete, la taille et la position
                parent.setStyle("-fx-background-image: url('GOB.png'); -fx-background-repeat: no-repeat;-fx-background-size: 500 500;-fx-background-position: left top;");
                // Ajouter un nom a la page
                App.menu.setTitle("GameOver");
                // Ne plus pouvoir aggrandir/retrecir la page

                score.setText("Score: " + MazeState.score);
                if(!lancer) {
                        //affiche le score apres le game over
                        final int txScore = 125;
                        final int tyScore = 100;
                        final int fsScore = 20;


                        //La ou on place le score
                        score.setTranslateX(txScore);
                        score.setTranslateY(tyScore);
                        //La taille de l'affichage du score
                        score.setFont(Font.font(fsScore));
                        //La couleur
                        score.setFill(Color.WHITE);
                        parent.getChildren().add(score);

                        //affichage du mesage GameOver
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


                }

                App.menu.setX(500);
                App.menu.setY(200);
                App.menu.setScene(scene);
                App.menu.show();
                lancer = true;

    }


}
