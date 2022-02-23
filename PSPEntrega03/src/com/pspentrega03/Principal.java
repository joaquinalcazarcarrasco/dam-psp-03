package com.pspentrega03;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Principal {
	

	public static void main(String[] args) {
		
		//Instancias de Marco y Capa. Añado capa a marco
		Marco marco = new Marco();
		Capa capa = new Capa();
		marco.add(capa);
		
		//Ajusto visibilidad y opción de cierre por defecto para el marco
		marco.setVisible(true);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

class Marco extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Constructor
	public Marco() {
		
		//Obtengo el tipo de pantalla que esté usando la persona usuaria
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		
		//Obtengo las dimensiones de la pantalla
		Dimension dimensiones = pantalla.getScreenSize();	
		int anchuraPantalla = dimensiones.width;
		int alturaPantalla = dimensiones.height;
		
		//Indico el la posición (centrado en pantalla) y el tamaño de mi frame
		setBounds(anchuraPantalla/4, alturaPantalla/4, 200, 250);
		
		//título
		setTitle("Contador");
		
		//Indico que no se pueda redimensionar el frame
		setResizable(false);	
		
	}
}

class Capa extends JPanel implements Runnable, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonActivar = new JButton("Iniciar Contador");
	private JButton botonParar = new JButton("Parar Contador");
	private Font fuente = new Font("Arial", Font.BOLD, 40);
	private Integer contador = 0;
	private Thread hilo = null;
	private boolean parar;
	
	//Constructor
	public Capa() {
		
		//Añaddo los botones y les paso el manejador de eventos sobre este contexto
		add(botonActivar);
		add(botonParar);
		botonActivar.addActionListener(this);
		botonParar.addActionListener(this);

	}
	
	//Método que inicia el hilo	
	public void start() {
		
		//Si el hilo es null, se le da valor y se ejecuta
		if(hilo==null) {
			
			hilo = new Thread(this);
			hilo.start();
		}
	}
	
	@Override
	public void run() {
		
		parar = false;
		
		//Se le asigna a hiloActual el valor del hilo en ejecución
		Thread hiloActual = Thread.currentThread();
		
		//mientras hiloActual sea hilo y parar sea falso se ejecutará (bucle infinito)
		while(hiloActual==hilo && !parar) {
			
			
			try {
				
				Thread.sleep(300);
				
			}catch(InterruptedException e) {
				
				e.printStackTrace();
			}
			
			repaint();//Se pinta de nuevo con la información actualizada
			contador++;//Incremento contador
			
		}
		
		
		
	}
	
	//método para parar hilo
	public void stop() { hilo = null; }
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Borro trozo del marco
		g.clearRect(5, 5, 190, 210);
		
		//Ajusto el color de fondo
		setBackground(Color.pink);
		
		//borde marco
		g.drawRect(5, 5, 190, 210);
		
		//Fuente y se pinta contador
		g.setFont(fuente);
		g.drawString(contador.toString(), 80, 150);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Se obtiene botón sobre el que se ha realizado el evento
		JButton pulsado = (JButton) e.getSource();
		
		//Según qué botón fue pulsado
		if(pulsado == botonActivar) {
			
			//Cambio texto de botón
			botonActivar.setText("Continuar");
			
			//Si el hilo es null o no está vivo
			if(hilo==null || !hilo.isAlive()) {
				
				//Se crea nuevo hilo y se ejecuta
				hilo = new Thread(this);
				hilo.start();
				
			}
			
		}else if(pulsado == botonParar) {
		
				//Se cambiar valor de parar para que el hilo pare.
				parar = true;
				
			
		}
		
	}
	
}
