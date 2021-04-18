package control;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import sprites.Colision;
import sprites.Globo;
import sprites.JugarBoton;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    private Scene scene;
    private GraphicsContext gc;
    private Image fons;
    Colision colision;
    JugarBoton jugarBoton;
    double cuantos = 0.005;
    private int contadorGlobosReventados;
    private Random random = new Random();
    private int numRandom;
    private double FPS = 0.005;
    private int vidas = 3;
    private int nivel = 1;
    private boolean empezar = false;
    private int contadorAmarillos, contadorMorados, contadorRojos, contadorVerdes;
    private String colorAReventar;
    private int numeroRandonXD;
    private double posX;
    private boolean alreadyExecuted1 = false;
    private boolean alreadyExecuted2 = false;
    private boolean alreadyExecuted3 = false;
    private boolean alreadyExecuted4 = false;
    private boolean alreadyExecuted5 = false;
    private boolean alreadyExecuted6 = false;
    private boolean alreadyExecuted7 = false;
    private boolean alreadyExecuted8 = false;
    private boolean alreadyExecuted9 = false;
    private boolean alreadyExecuted10 = false;
    private boolean alreadyExecuted11 = false;
    private boolean alreadyExecuted12 = false;


    List<String> colorDeGloboActual = new ArrayList<>();
    String rojo = "rojo"; String verde = "verde"; String morado = "morado"; String amarillo = "amarillo";
    List<String> tipoDeGlobo = new ArrayList<>();
    private String stringGlobosRojos = "rojo";
    private String stringGlobosAmarillos = "amarillo";
    private String stringGlobosVerdes = "verde";
    private String stringGlobosMorados = "morado";


    ArrayList<Globo> globos = new ArrayList<>();

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(FPS), new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {

            //Fondo
            gc.drawImage(fons, 0,0,1400,800);

            if (!empezar) {
                jugarBoton.render(gc);
                colision.render(gc);
                gc.drawImage(new Image("images/titulo.png"), 270, 160);

                //redirigir todos los globos abajo, solucion para cuando se tiene que volver a jugar despues de una partida, ya que los globos de la anterior se quedan flotando
                for (int i = 0; i < globos.size(); i++) {
                            globos.get(i).setPosY(800);
                            globos.get(i).setPosX(Math.random()*1300);
                }
            } else {
                tipoDeGlobo.add(stringGlobosAmarillos); tipoDeGlobo.add(stringGlobosMorados); tipoDeGlobo.add(stringGlobosRojos); tipoDeGlobo.add(stringGlobosVerdes);
                colorDeGloboActual.add(amarillo); colorDeGloboActual.add(morado); colorDeGloboActual.add(rojo); colorDeGloboActual.add(verde);

                numRandom = random.nextInt(4) + 1;
                if (Math.random() < cuantos)  {

                    if (numRandom == 1) {
                        Globo globo = new Globo(new Image("images/morado.png", 88, 149, false, false), "morado");
                        globos.add(globo);
                    } else if (numRandom == 2){
                        Globo globo = new Globo(new Image("images/amarillo.png", 88, 149, false, false), "amarillo");
                        globos.add(globo);
                    } else if (numRandom == 3){
                        Globo globo = new Globo(new Image("images/verde.png", 88, 149, false, false), "verde");
                        globos.add(globo);
                    } else {
                        Globo globo = new Globo(new Image("images/rojo.png", 88, 149, false, false), "rojo");
                        globos.add(globo);
                    }
                }

                for (int i = 0; i < globos.size(); i++) {
                    globos.get(i).move();
                    globos.get(i).render(gc);

                    //Al tocar con la nube el globo se dirige abajo en una posicion random, ya que el *clear no lo borra y hace que colisione
                    // todo el rato causando la perdida de todas las vidas en milesimas de segundo
                    if (globos.get(i).getColor().equals(colorAReventar)) {
                        if (globos.get(i).getBoundary().intersects(colision.getBoundary())) {

                            //Este if es para saber desde la terminal que globo ha colisionado
                            if (globos.get(i).getColor().equals("morado")) {
                                System.out.println("Ha colisionado el globo de color " + globos.get(i).getColor());
                            } else if (globos.get(i).getColor().equals("rojo")) {
                                System.out.println("Ha colisionado el globo de color " + globos.get(i).getColor());
                            } else if (globos.get(i).getColor().equals("verde")) {
                                System.out.println("Ha colisionado el globo de color " + globos.get(i).getColor());
                            } else if (globos.get(i).getColor().equals("amarillo")) {
                                System.out.println("Ha colisionado el globo de color " + globos.get(i).getColor());
                            }

                            /*System.out.println(globos);*/
                            vidas--;
                            globos.get(i).setPosY(800);
                            globos.get(i).setPosX(Math.random()*1300);
                        }
                    }

                    //Evitar colisiones entre globos
                    for (int j = 0; j < globos.size() ; j++) {
                     if (globos.get(j).getBoundary().intersects(globos.get(i).getBoundary())) {
                         System.out.println("globos colisionados");
                         while (!globos.get(j).getBoundary().intersects(globos.get(i).getBoundary())) {
                             System.out.println("mover un 1px globo por colison");
                             posX =  globos.get(i).getPosX();
                             globos.get(i).setPosX(posX++);
                         }
                     }
                    }
                }

                //Vidas
                if (vidas == 3) {
                    gc.drawImage(new Image("images/3vidas.png"), 45, 200);
                } else if (vidas == 2 ) {
                    gc.drawImage(new Image("images/2vidas.png"), 45, 200);
                } else  if (vidas == 1) {
                    gc.drawImage(new Image("images/1vidas.png"), 45, 200);
                }

                colision.render(gc);

                contadores();
                prints();

            }



        }
    })
    );

    @FXML
    Label lblInfo;
    @FXML
    Canvas mainCanvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(url);
        System.out.println(resourceBundle.getString("key2"));

        /*globo = new Globo(new Image("images/globo.png"));*/
        colision = new Colision(new Image("images/nubes.png"));
        jugarBoton = new JugarBoton(new Image("images/boton-jugar.png"));
        fons = new Image("images/fondo.png");
        gc = mainCanvas.getGraphicsContext2D();
        //gc.drawImage(fons, 1200,800);

        // Opció 1
        //animationTimer.start();
        // Opció 2
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void setScene(Scene sc) {
        scene = sc;

        scene.setOnMouseClicked(mouseEvent -> {
            Point2D point = new Point2D(mouseEvent.getX(),mouseEvent.getY());

            if (jugarBoton.isClicked(point)) empezar = true;

            for (int i = 0; i < globos.size() ; i++) {

                if (globos.get(i).isClicked(point)) {

                    if (!globos.get(i).getColor().equals(colorAReventar)) {
                        System.out.print("Has perdido");
                        vidas--;
                    } else {
                        if (colorAReventar.equals("morado")) {
                            contadorMorados++;
                            contadorGlobosReventados++;
                        } else if (colorAReventar.equals("verde")) {
                            contadorVerdes++;
                            contadorGlobosReventados++;
                        } else if (colorAReventar.equals("rojo")) {
                            contadorRojos++;
                            contadorGlobosReventados++;
                        } else if (colorAReventar.equals("amarillo")) {
                            contadorAmarillos++;
                            contadorGlobosReventados++;
                        }
                    }
                }
            }
            globos.removeIf(globo -> globo.isClicked(point));
        });
    }

    private void contadores() {

        if (contadorGlobosReventados == 0) {
            cuantos = 0.005;
            nivel = 1;

            if (!alreadyExecuted1) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);
                System.out.println(numeroRandonXD);

                alreadyExecuted1 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 10) {
            cuantos = 0.006;
            nivel = 2;

            if (!alreadyExecuted2) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted2 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 20) {
            cuantos = 0.009;
            nivel = 3;

            if (!alreadyExecuted3) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted3 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 30) {
            cuantos = 0.012;
            nivel = 4;

            if (!alreadyExecuted4) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted4 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 40) {
            cuantos = 0.015;
            nivel = 5;

            if (!alreadyExecuted5) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted5 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 50) {
            cuantos = 0.016;
            nivel = 6;

            if (!alreadyExecuted6) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted6 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 60) {
            cuantos = 0.018;
            nivel = 7;

            if (!alreadyExecuted7) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted7 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 70) {
            cuantos = 0.019;
            nivel = 8;

            if (!alreadyExecuted8) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted8 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 80) {
            cuantos = 0.020;
            nivel = 9;

            if (!alreadyExecuted9) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted9 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 90) {
            cuantos = 0.021;
            nivel = 10;

            if (!alreadyExecuted10) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted10 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 100) {
            cuantos = 0.022;
            nivel = 11;

            if (!alreadyExecuted11) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted11 = true;
                globos.clear();
            }

        } else if (contadorGlobosReventados == 70) {
            cuantos = 0.023;
            nivel = 12;

            if (!alreadyExecuted12) {
                numeroRandonXD = random.nextInt(4) + 1;
                colorAReventar = tipoDeGlobo.get(numeroRandonXD);

                alreadyExecuted12 = true;
                globos.clear();
            }

        }


        if (vidas == 0) {
            contadorAmarillos = 0;
            contadorMorados = 0;
            contadorRojos = 0;
            contadorVerdes = 0;
            cuantos = 0.005;
            contadorGlobosReventados = 0;
            FPS = 0.005;
            vidas = 3;
            empezar = false;

            //Para que se reinicien los booleans que generan los colores de los globos
            alreadyExecuted1 = false;
            alreadyExecuted2 = false;
            alreadyExecuted3 = false;
            alreadyExecuted4 = false;
            alreadyExecuted5 = false;
            alreadyExecuted6 = false;
            alreadyExecuted7 = false;
            alreadyExecuted8 = false;
            alreadyExecuted9 = false;
            alreadyExecuted10 = false;
            alreadyExecuted11 = false;
            alreadyExecuted12 = false;

            globos.clear();

        }
    }

    private void prints() {

        //globos al lado de la puntuacion
        gc.drawImage(new Image("images/globos-puntuacion.png"), 1290, 130);

        //Morado
        gc.setFill(Color.PURPLE);
        gc.setFont(new Font("Arial", 35));
        gc.fillText(String.valueOf(contadorMorados), 1320, 155);

        //Amarillo
        gc.setFill(Color.YELLOW);
        gc.setFont(new Font("Arial", 35));
        gc.fillText(String.valueOf(contadorAmarillos), 1320, 190);

        //Rojo
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 35));
        gc.fillText(String.valueOf(contadorRojos), 1320, 225);

        //Verde
        gc.setFill(Color.GREEN);
        gc.setFont(new Font("Arial", 35));
        gc.fillText(String.valueOf(contadorVerdes), 1320, 260);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 35));
        gc.fillText(String.valueOf(contadorGlobosReventados), 1320, 295);


        //Niveles
        gc.drawImage(new Image("images/input.png"), 40, 130);

        gc.setFont(new Font("Arial", 30));

        //sombra
        gc.setFill(Color.BLACK);
        gc.fillText("Nivel: " + nivel, 58, 168);

        //texto principal
        gc.setFill(Color.WHITE);
        gc.fillText("Nivel: " + nivel, 60, 170);




        if (colorAReventar.equals("morado")) {
            gc.setFill(Color.MEDIUMPURPLE);
        } else if (colorAReventar.equals("verde")) {
            gc.setFill(Color.LIGHTGREEN);
        } else if (colorAReventar.equals("rojo")) {
            gc.setFill(Color.RED);
        } else if (colorAReventar.equals("amarillo")) {
            gc.setFill(Color.YELLOW);
        }

//        gc.setFont(new Font("Arial", 20));
//        gc.fillText("Explota los globos de color: ", 600, 160);
        gc.drawImage(new Image("images/explota.png"), 470, 140);
        gc.setFont(new Font("Arial", 40));
        gc.fillText(colorAReventar, 660, 200);

    }

}
