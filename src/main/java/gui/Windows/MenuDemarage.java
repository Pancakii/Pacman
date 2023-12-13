package gui.Windows;

import gui.App;
import gui.ExitButon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MazeState;

public class MenuDemarage implements EventHandler<ActionEvent> {

        private static Jeu jeu = new Jeu();
        private static VBox parent = new VBox();
        private static Scene scene = new Scene(parent);
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

                        //La barre qui permet d'ajouter un nom

                        GridPane gridPane = new GridPane();
                        TextField txtName = new TextField("Random");
                        txtName.setPromptText("Nickname");
                        txtName.setFocusTraversable(false);
                        gridPane.add(txtName,1,0,1,1);
                        gridPane.setTranslateX(175);
                        gridPane.setTranslateY(150);

                        txtName.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                        MazeState.nickname = txtName.getText();
                                }
                        });
                        parent.getChildren().add(gridPane);

                        //bouton pour jouer
                        Button bouton = new Button(" Play "); //Le nom du bouton
                        bouton.setTranslateX(210);//Les coordonnées du bouton
                        bouton.setTranslateY(170);
                        bouton.setFont(Font.font(20));
                        bouton.setOnAction(jeu);
                        parent.getChildren().add(bouton);

                        //bouton pour les modes
                        Button menubouton = new Button("Mode");
                        menubouton.setTranslateX(210);
                        menubouton.setTranslateY(190);
                        menubouton.setFont(Font.font(20));
                        //App.mebouton.setOnAction();
                        parent.getChildren().add(menubouton);

                        //bouton pour quitter tout lle programme
                        Button exitbouton = new Button(" Exit  ");
                        exitbouton.setTranslateX(210);
                        exitbouton.setTranslateY(210);
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
