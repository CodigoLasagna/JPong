import javax.swing.*;

public class main
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() ->
			{
				JFrame frame = new JFrame("ULTRA SUPREME HARDCORE JAVA PONG");
				frame.setResizable(false);
				frame.setSize(1280, 720);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new pongPanel());
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		);
	}
}
