import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


//Prueba de modificacion


@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;

	private JButton b_start, b_stop;

	private Board board;

	// TODO update with number of group label. See practice statement.
	private final int N_BALL = 4+2;
	//Inicializamos el array con el n� de bolas. Posteriormente asignamos un objeto Ball a cada posicion del Array.
	private Ball[] balls = new Ball[N_BALL];
	
	//Creo un array de hilos para las bolas
	
	private Thread hilosBall[];

	public Billiards() {

		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));


		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("Práctica programación concurrente objetos móviles independientes");
		setResizable(false);
		setVisible(true);


		initBalls();
		board.setBalls(balls);
	}

	private Thread makeThread(final Ball ball) {
			Runnable runloop = new Runnable() {
		
					

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						while (true) {
							ball.move();
							board.repaint();
							Thread.sleep(20);
						}
					}
					catch (InterruptedException e) {
						return;
					}
				}
					

			};
				return new Thread(runloop);
								
	}
	
	private void initBalls() {
		// TODO init balls
	
		
		for (int i=0; i<N_BALL; i++) {
			balls[i] = new Ball();			
		}
	}

	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Code is executed when start button is pushed
			if (hilosBall == null) {
				board.setBalls(balls);
				hilosBall = new Thread[N_BALL];
				for (int i=0; i<N_BALL; i++ ) {
					hilosBall[i] = makeThread(balls[i]);
					hilosBall[i].start();
				}
			}
		}
	}

	private class StopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Code is executed when stop button is pushed
			if (hilosBall != null) {
				for (int i=0; i<N_BALL; i++ ) {
					hilosBall[i].interrupt();
				}
				hilosBall = null;
			}			

		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
}