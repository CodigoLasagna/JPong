import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class pongPanel extends JPanel implements ActionListener{
	private Timer timer;
	ball mainBall = new ball();
	paddle mainPaddle = new paddle(10, 200, 10, 80, 0);
	paddle secondPaddle = new paddle(10, 200, 10, 80, 2);
	int current_width = 0;
	int current_height = 0;
	boolean game_start = false;
	private Font font;
	Random rn = new Random();

	public pongPanel() {
		timer = new Timer(5, this);
		timer.start();
		
		addKeyListener(mainPaddle);
		addKeyListener(secondPaddle);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		
		
		mainPaddle.prepareThread();
		secondPaddle.prepareThread();
		setBackground(new Color(0x1a1a1a));

		try
		{
			font = Font.createFont(Font.TRUETYPE_FONT, new File("src/ka1.ttf")).deriveFont(50f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		}
		catch (IOException | FontFormatException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Lógica del juego: mover la pelota, verificar colisiones, etc.

		if (game_start)
		{
			mainBall.x += mainBall.spd_x;
			mainBall.y += mainBall.spd_y;
		}
		if (secondPaddle.type == 2)
		{
			if (mainPaddle.up || mainPaddle.down)
			{
				game_start = true;
			}
		}
		else
		{
			if (mainPaddle.up || mainPaddle.down || secondPaddle.up || secondPaddle.down)
			{
				game_start = true;
			}
		}

		// Verificar colisiones con las paredes
		if (mainBall.x < 0 || mainBall.x > current_width-mainBall.r) {
			resetGame();
			mainBall.changeHorDir();
			if (game_start == true)
			{
				if (mainBall.x > current_width / 2)
				{
					mainPaddle.score += 1;
				}
				else
				{
					secondPaddle.score += 1;
				}
			}
			game_start = false;
		}

		if (mainBall.y < 0 || mainBall.y > current_height-mainBall.r) {
			mainBall.changeVertDir();
		}

		// Verificar colisiones con la paleta
		if (mainBall.x >= 0 && mainBall.x <= mainBall.r && mainBall.y + mainBall.r >= mainPaddle.y && mainBall.y <= mainPaddle.y + mainPaddle.height && !mainBall.colision) {
			mainBall.changeHorDir();
			mainBall.colision = true;
		}
		else
		{
			mainBall.colision = false;
		}
		if (mainBall.x + mainBall.r >= current_width - mainBall.r && mainBall.x <= current_width && mainBall.y + mainBall.r >= secondPaddle.y && mainBall.y <= secondPaddle.y + secondPaddle.height && !mainBall.colision) {
			mainBall.changeHorDir();
			mainBall.colision = true;
		}
		else
		{
			mainBall.colision = false;
		}
		if (getWidth() != current_width)
		{
			current_width = getWidth();
			current_height = getHeight();
			secondPaddle.x = current_width-20;
			mainPaddle.limit_h = current_width;
			mainPaddle.limit_v = current_height;
			secondPaddle.limit_h = current_width;
			secondPaddle.limit_v = current_height;
			resetGame();
		}
		if (secondPaddle.type == 2 && game_start)
		{
			if (rn.nextInt(30) == 0)
			{
				if (mainBall.y > secondPaddle.y)
				{
					secondPaddle.up = false;
					secondPaddle.down = true;
				}
				else
				{
					secondPaddle.up = true;
					secondPaddle.down = false;
				}
			}
		}
		if (secondPaddle.type == 2 && !game_start)
		{
			secondPaddle.up = false;
			secondPaddle.down = false;
		}

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		requestFocusInWindow();

		// Dibujar la paleta
		g.setColor(new Color(0x9f9f9f));
		g.fillRect(mainPaddle.x, mainPaddle.y, mainPaddle.width, mainPaddle.height);
		g.fillRect(secondPaddle.x, secondPaddle.y, secondPaddle.width, secondPaddle.height);

		for (int i = 0; i < 10; i++)
		{
			g.fillRect(current_width / 2 - 5, i * 72 + 20, 10, 20);
		}

		// Dibujar la pelota
		g.fillOval((int) mainBall.x, (int) mainBall.y, mainBall.r, mainBall.r);
		g.setFont(font);
		g.drawString(""+mainPaddle.score, current_width / 2 - 100, 50);
		g.drawString(""+secondPaddle.score, current_width / 2 + 60, 50);
	}

	private void resetGame()
	{
		mainBall.x = current_width / 2 - 10;
		mainBall.y = current_height / 2 - 15;
		mainPaddle.y = current_height / 2 - 40;
		secondPaddle.y = current_height / 2 - 40;
	}
}
