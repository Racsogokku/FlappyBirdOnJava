package osquitarINC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
class Coso  {
    protected int x;
    protected int y;
    protected int ancho,altura;
    Coso() {
        this(0, 0, 0, 0);
    }

    Coso(int x, int y, int ancho, int altura) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.altura = altura;
    }
    public void setY (int y) {
        this.y = y;
    }
    public void setX(int x){
        this.x=x;
    }
    public Rectangle getHitbox(){
        return new Rectangle(x,y,ancho, altura);
    }
    public int getY () {
        return y;
    }
    public int getX () {
        return x;
    }
}
class Pajaro extends Coso {
    private final int impulsoSalto=-12;
    private final int GRAVEDAD = 1;
    private int velocidadCaida=GRAVEDAD;
    Pajaro(){
        this(100, 250, 30, 30);
    }
    Pajaro(int x,int y,int ancho,int altura){
        super(x,y,ancho,altura);
    }
    public void aumentarY(int cant,int maxY){
        y+=cant;
        if(y+altura>maxY) y= maxY-altura;
        else if(y<0) y=0;
    }
    public void caer(int maxY){
        velocidadCaida+=GRAVEDAD;
        aumentarY(velocidadCaida,maxY);
    }
    public void saltar(){
        velocidadCaida=impulsoSalto;
    }

}
class Tubo extends Coso{

    Tubo(int x,int y,int ancho, int altura){
        super(x,y,ancho,altura);
    }
    Tubo(){
        this(0,0,0,0);
    }
    public void aumentarX(int cant){
        x+=cant;
    }


}
public class Juego extends JPanel implements ActionListener{
    private Pajaro pajaro;
    private List<Tubo> listaTubos = new ArrayList<>();
    private int velocidadLateral = -4;
    public Juego(int fps){
        Timer timer = new Timer(Math.floorDiv(1000,fps),this);
        timer.start();
        pajaro = new Pajaro(getWidth()/2,getHeight()/2,30,30);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped (KeyEvent e) {}

            @Override
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_SPACE){
                    System.out.println("Salto :)");
                    pajaro.saltar();
                }
            }

            @Override
            public void keyReleased (KeyEvent e) {}
        });
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    private void actualizarCosas(){
        pajaro.caer(this.getHeight());
        listaTubos.forEach(x -> x.aumentarX(velocidadLateral));
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        actualizarCosas();
        comprobarColisiones();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //Dibujar fondo
        g.setColor(new Color(135, 206, 235));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.YELLOW);
        g.fillOval(pajaro.x,pajaro.y,pajaro.ancho,pajaro.altura);

        g.setColor(Color.green);
        for (Tubo tubo: listaTubos){
            g.fillRect(tubo.x,tubo.y,tubo.ancho,tubo.altura);
        }
    }

    private void comprobarColisiones () {
        Rectangle hitboxPajaro = pajaro.getHitbox();
        for(Tubo tubo:listaTubos){
            if(tubo.getHitbox().intersects(hitboxPajaro)){
                JOptionPane.showMessageDialog(this,"Se acabo lil","Se acabo",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }
}
