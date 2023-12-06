package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuDemarage implements EventHandler<ActionEvent> {

        private static Jeu jeu = new Jeu();
        private static VBox parent = new VBox();
        private static Scene scene = new Scene(parent);
        private static boolean lancer = false;

        public static void affichageMenuDemarage()  {
                App.menu.setHeight(500);
                App.menu.setWidth(500);

                //Il y a du CSS dans le setStyke, lien de l'image, est ce que ca se repete, la taille et la position
                parent.setStyle("-fx-background-image: url('background.jpeg'); -fx-background-repeat: no-repeat;-fx-background-size: 500 500;-fx-background-position: center center;");
                // Ajouter un nom a la page
                App.menu.setTitle("Menu");
                // Ajouter une icone a la page`
                Image icon = new Image("pacman.png");
                App.menu.getIcons().add(icon);
                // Ne plus pouvoir aggrandir/retrecir la page
                App.menu.setResizable(false);

                if(!lancer){
                        //affichage du mesage Menu
                        Text go = new Text();
                        go.setText("Menu");
                        go.setTranslateX(190);
                        go.setTranslateY(40);
                        go.setFont(Font.font(50));
                        go.setFill(Color.YELLOW);
                        parent.getChildren().add(go);


                        //bouton pour jouer
                        Button bouton = new Button(" Play "); //Le nom du bouton
                        bouton.setTranslateX(210);//Les coordonnées du bouton
                        bouton.setTranslateY(150);
                        bouton.setFont(Font.font(20));
                        bouton.setOnAction(jeu);
                        parent.getChildren().add(bouton);

                        //bouton pour les modes
                        Button menubouton = new Button("Mode");
                        menubouton.setTranslateX(210);
                        menubouton.setTranslateY(170);
                        menubouton.setFont(Font.font(20));
                        //App.mebouton.setOnAction();
                        parent.getChildren().add(menubouton);

                        //bouton pour quitter tout lle programme
                        Button exitbouton = new Button(" Exit  ");
                        exitbouton.setTranslateX(210);
                        exitbouton.setTranslateY(190);
                        exitbouton.setFont(Font.font(20));
                        exitbouton.setOnAction(new ExitButon());
                        parent.getChildren().add(exitbouton);
                }



                App.menu.setScene(scene);
                App.menu.show();
                lancer = true;
        }

        @Override
        public void handle(ActionEvent event) {
                affichageMenuDemarage();
        }
}
