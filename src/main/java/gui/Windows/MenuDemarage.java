package gui.Windows;

import gui.App;
import gui.ExitButon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;

public class MenuDemarage implements EventHandler<ActionEvent> {
        private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        private static final double screenSizeWidth = screenSize.getWidth();
        private static final double screenSizeHeight = screenSize.getHeight();
        private static Jeu jeu = new Jeu();

        public static VBox parent = new VBox();
        public static Scene scene = new Scene(parent);
        private static boolean lancer = false;

        public static void affichageMenuDemarage()  {


                App.menu.setHeight(500);
                App.menu.setWidth(500);

                //Il y a du CSS dans le setStyke, lien de l'image, est ce que ca se repete, la taille et la position
                parent.setStyle("-fx-background-image: url('background.jpeg'); -fx-background-repeat: no-repeat;-fx-background-size: 500 500;-fx-background-position: left top;");
                // Ajouter un nom a la page
                App.menu.setTitle("Menu");



                if(!lancer){
                        //affichage du mesage Menu
                        Text menu = new Text();
                        menu.setText("Menu");
                        menu.setTranslateX(190);
                        menu.setTranslateY(40);
                        menu.setFont(Font.font(50));
                        menu.setFill(Color.YELLOW);
                        parent.getChildren().add(menu);



                        //bouton pour jouer
                        Button jouerbouton = new Button(" Play "); //Le nom du bouton
                        jouerbouton.setTranslateX(210);//Les coordonnées du bouton
                        jouerbouton.setTranslateY(170);
                        jouerbouton.setFont(Font.font(20));
                        jouerbouton.setOnAction(new AskName());
                        parent.getChildren().add(jouerbouton);

                        //bouton pour les modes
                        Button modebouton = new Button("Mode");
                        modebouton.setTranslateX(210);
                        modebouton.setTranslateY(190);
                        modebouton.setFont(Font.font(20));
                        //App.mebouton.setOnAction();
                        parent.getChildren().add(modebouton);

                        //bouton pour quitter tout lle programme
                        Button exitbouton = new Button(" Exit  ");
                        exitbouton.setTranslateX(210);
                        exitbouton.setTranslateY(210);
                        exitbouton.setFont(Font.font(20));
                        exitbouton.setOnAction(new ExitButon());
                        parent.getChildren().add(exitbouton);
                }


                App.menu.setX((screenSizeWidth - App.menu.getWidth())/2);
                App.menu.setY((screenSizeHeight -App.menu.getHeight())/2);
                App.menu.setScene(scene);
                App.menu.show();
                lancer = true;
        }

        @Override
        public void handle(ActionEvent event) {
                affichageMenuDemarage();
        }
}
