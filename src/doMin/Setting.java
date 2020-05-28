package doMin;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Setting extends JFrame implements ActionListener {
	private JPanel mainPanel, p1, p2, p3, p4;
	private JRadioButton rd1, rd2, rd3;
	private JButton btOk;

	public Setting() {

		setResizable(false);
		taoSwing();
		setTitle("Setting");
		setSize(250, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void taoSwing() {
		mainPanel = new JPanel();
		add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(p1 = new JPanel());
		mainPanel.add(p2 = new JPanel());
		mainPanel.add(p3 = new JPanel());
		mainPanel.add(p4 = new JPanel());
		mainPanel.setBackground(Color.green);
		p1.setBackground(Color.yellow);
		p2.setBackground(Color.orange);
		p3.setBackground(Color.red);
		p4.setBackground(Color.black);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(rd1 = new JRadioButton("Dễ"));
		p2.add(rd2 = new JRadioButton("Trung Bình"));
		p3.add(rd3 = new JRadioButton("Khó"));
		p4.add(btOk = new JButton("OK"));
		btOk.setBackground(Color.white);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rd1);
		bg.add(rd2);
		bg.add(rd3);
		rd1.setBackground(Color.yellow);
		rd2.setBackground(Color.orange);
		rd3.setBackground(Color.red);
		rd1.setForeground(Color.blue);
		rd2.setForeground(Color.blue);
		rd3.setForeground(Color.blue);
		rd1.addActionListener(this);
		rd2.addActionListener(this);
		rd3.addActionListener(this);
		btOk.addActionListener(this);
	}

	public static void main(String[] args) {
		new Setting();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btOk)) {
			JButton[][] arr =new JButton[10][10];
			int soMin =10;
			if(rd1.isSelected()) {
				arr = new JButton[7][7];
				soMin=5;
			}else if(rd2.isSelected()) {
				arr = new JButton[10][20];
				soMin=30;
			}
			else if(rd3.isSelected()) {
				arr = new JButton[12][27];
				soMin=60;
			}
			this.setVisible(false);
			new DoMin(arr, soMin);
		}
	}
}
