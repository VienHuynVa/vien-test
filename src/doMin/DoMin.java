package doMin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.util.concurrent.Flow;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DoMin extends JFrame implements MouseListener {
	private JPanel mainpJPanel, p1, p2;
	private JButton bt1, btSetting;
	private JButton[][] arrButton;
	private int[][] arr;
	private boolean[][] arrCo, arrEnd, click;
	private int soMin;
	private JTextArea ta;

	public DoMin(JButton[][] mang, int soMin) {
		this.arrButton = mang;
		this.soMin = soMin;

		// arrbutton về phần button
		// arr là mảng số để dò tìm
		// arrEnd để kết thúc trò chơi
		// arrCo để cắm cờ
		// click để xử lý khi click nó rồi thì ko đc click nữa

		arr = new int[arrButton.length][arrButton[0].length];
		arrEnd = new boolean[arr.length][arr[0].length];
		arrCo = new boolean[arr.length][arr[0].length];
		click = new boolean[arr.length][arr[0].length];

		taoSwing();

		pack();
		setResizable(false);
		setTitle("Dò mìn");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void taoSwing() {

		// main panel

		mainpJPanel = new JPanel();
		add(mainpJPanel);
		mainpJPanel.setLayout(new BoxLayout(mainpJPanel, BoxLayout.Y_AXIS));
		mainpJPanel.add(p1 = new JPanel(), BorderLayout.NORTH);
		mainpJPanel.add(p2 = new JPanel(), BorderLayout.CENTER);

		// p2

		p2.setLayout(new GridLayout(arrButton.length, arrButton[0].length));

		// p1

		btSetting = new JButton(new ImageIcon("image/setting.png"));
		bt1 = new JButton(new ImageIcon("image/12.png"));
		bt1.setPreferredSize(new Dimension(100, 100));
		btSetting.setPreferredSize(new Dimension(100, 100));
		bt1.setBackground(Color.gray);
		btSetting.setBackground(Color.gray);
		p1.add(btSetting);
		p1.add(bt1);
		p1.setBackground(Color.gray);
		p1.add(ta = new JTextArea());
		ta.setPreferredSize(new Dimension(100, 100));
		ta.setEditable(false);
		ta.setForeground(Color.red);
		ta.setFont(new Font(null, Font.BOLD, 80));
		ta.setText(soMin + " ");

		Dimension bt = new Dimension(49, 47);

		// add button

		for (int i = 0; i < arrButton.length; i++) {
			for (int j = 0; j < arrButton[i].length; j++) {
				arrButton[i][j] = new JButton();
				arrButton[i][j].addMouseListener(this);
				arrButton[i][j].setIcon(new ImageIcon("image/o1.png"));
				arrButton[i][j].setPreferredSize(bt);
				p2.add(arrButton[i][j]);
			}
		}

		// even setting

		btSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting st = new Setting();
				setVisible(false);
				st.setVisible(true);
			}
		});

		// tạo mảng số để xử lý

		duaMinVao(arr, soMin);
		dienSo(arr);

		// in ra console

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + "  ");
			}
			System.out.println();
		}

		// eveen reset

		bt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int a = JOptionPane.showConfirmDialog(null, "Bạn có muốn chơi lại ván mới ?", "New Game",
						JOptionPane.YES_NO_OPTION);
				if (a == JOptionPane.YES_OPTION) {
					setVisible(false);
					new DoMin(arrButton, soMin);
				}
			}
		});
	}

	public void duaMinVao(int[][] arr, int soMin) {
		Random rd = new Random();
		int count = 0;
		while (count < soMin) {
			count = 0;
			int a = rd.nextInt(arr.length);
			int b = rd.nextInt(arr[0].length);
			arr[a][b] = 10;
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == 10)
						count++;
				}
			}
		}
	}

	public void dienSo(int[][] arr) {
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j] != 10) {
					for (int j2 = i - 1; j2 <= i + 1; j2++) {
						for (int k = j - 1; k <= j + 1; k++) {
							if (j2 >= 0 && j2 < arr.length && k >= 0 && k < arr[i].length)
								if (arr[j2][k] == 10)
									count++;
						}
					}
					arr[i][j] = count;
					count = 0;
				}
			}
		}
	}

	// mở hết ô boom

	public void moHet(int i, int j) {
		for (int j2 = 0; j2 < arr.length; j2++) {
			for (int k = 0; k < arr[i].length; k++) {
				if (arr[j2][k] == 10) {
					arrButton[j2][k].setIcon(new ImageIcon("image/bom.png"));
				}
			}
		}
		for (int j2 = 0; j2 < arr.length; j2++) {
			for (int k = 0; k < arr[i].length; k++) {
				click[j2][k] = true;
			}
		}
	}

	public void moRong(int a, int b) {
		for (int i = a - 1; i <= a + 1; i++) {
			for (int j = b - 1; j <= b + 1; j++) {
				if (i >= 0 && i < arr.length && j >= 0 && j < arr[i].length && arr[i][j] == 0 && arrEnd[i][j] == false
						&& click[i][j] == false) {
					arrEnd[i][j] = true;
					click[i][j] = true;
					arrButton[i][j].setIcon(new ImageIcon("image/o2.png"));
					moRong(i, j);
				}
				if (i >= 0 && i < arr.length && j >= 0 && j < arr[i].length && arr[i][j] != 0) {
					int g = arr[i][j];
					if (g == 1) {
						arrButton[i][j].setIcon(new ImageIcon("image/so1.png"));
					} else if (g == 2) {
						arrButton[i][j].setIcon(new ImageIcon("image/so2.png"));
					} else if (g == 3) {
						arrButton[i][j].setIcon(new ImageIcon("image/so3.png"));
					} else if (g == 4) {
						arrButton[i][j].setIcon(new ImageIcon("image/so4.png"));
					} else if (g == 5) {
						arrButton[i][j].setIcon(new ImageIcon("image/so5.png"));
					} else if (g == 6) {
						arrButton[i][j].setIcon(new ImageIcon("image/so6.png"));
					} else if (g == 7) {
						arrButton[i][j].setIcon(new ImageIcon("image/so7.png"));
					} else if (g == 8) {
						arrButton[i][j].setIcon(new ImageIcon("image/so8.png"));
					}
					arrEnd[i][j] = true;
					click[i][j] = true;
				}
			}
		}

	}

	public void end() {
		int a = JOptionPane.showConfirmDialog(null, "You lose !! Play again?", "END GAME", JOptionPane.YES_NO_OPTION);
		if (a == JOptionPane.YES_OPTION) {
			setVisible(false);
			new DoMin(arrButton, soMin);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

		// chạy hết mảng

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {

				// chọn trúng bom

				if (e.getSource() == arrButton[i][j] && e.getButton() == 1 && arr[i][j] == 10 && click[i][j] == false
						&& arrCo[i][j] == false) {
					moHet(i, j);
					arrButton[i][j].setIcon(new ImageIcon("image/bom2.png"));
					bt1.setIcon(new ImageIcon("image/11.png"));
					int a = JOptionPane.showConfirmDialog(null, "You lose !! Play again?", "END GAME",
							JOptionPane.YES_NO_OPTION);
					if (a == JOptionPane.YES_OPTION) {
						setVisible(false);
						new DoMin(arrButton, soMin);
					}
				}
				// cắm cờ

				if (e.getSource() == arrButton[i][j] && e.getButton() == 3 && arrEnd[i][j] == false
						&& click[i][j] == false) {
					if (arrCo[i][j] == false) {
						arrButton[i][j].setIcon(new ImageIcon("image/co.png"));
						arrCo[i][j] = true;
					} else {
						arrButton[i][j].setIcon(new ImageIcon("image/o1.png"));
						arrCo[i][j] = false;
					}
				}

				// ô = 0

				if (e.getSource() == arrButton[i][j] && e.getButton() == 1 && arr[i][j] == 0 && click[i][j] == false
						&& arrCo[i][j] == false) {
					moRong(i, j);
				}

				// ô nomarl

				if (e.getSource() == arrButton[i][j] && e.getButton() == 1 && arr[i][j] != 0 && arr[i][j] != 10
						&& click[i][j] == false && arrCo[i][j] == false) {
					int a = arr[i][j];
					if (a == 1) {
						arrButton[i][j].setIcon(new ImageIcon("image/so1.png"));
					} else if (a == 2) {
						arrButton[i][j].setIcon(new ImageIcon("image/so2.png"));
					} else if (a == 3) {
						arrButton[i][j].setIcon(new ImageIcon("image/so3.png"));
					} else if (a == 4) {
						arrButton[i][j].setIcon(new ImageIcon("image/so4.png"));
					} else if (a == 5) {
						arrButton[i][j].setIcon(new ImageIcon("image/so5.png"));
					} else if (a == 6) {
						arrButton[i][j].setIcon(new ImageIcon("image/so6.png"));
					} else if (a == 7) {
						arrButton[i][j].setIcon(new ImageIcon("image/so7.png"));
					} else if (a == 8) {
						arrButton[i][j].setIcon(new ImageIcon("image/so8.png"));
					}
					arrEnd[i][j] = true;
					click[i][j] = true;
				}
				if (e.getClickCount() == 2 && e.getSource() == arrButton[i][j] && checkCo(i, j) == true
						&& arrEnd[i][j] == true) {
					int check = 0;
					for (int j2 = i - 1; j2 <= i + 1; j2++) {
						for (int k = j - 1; k <= j + 1; k++) {
							if (k >= 0 && k < arr[i].length && j2 >= 0 && j2 < arr.length && arrEnd[j2][k] == false) {
								if (arrCo[j2][k] == true && arr[j2][k] == 10) {

								} else if (arrCo[j2][k] == true && arr[j2][k] != 10) {
									arrButton[j2][k].setIcon(new ImageIcon("image/co2.png"));
									check++;
								} else if (arrCo[j2][k] == false && arr[j2][k] == 10) {
									check++;
									arrButton[j2][k].setIcon(new ImageIcon("image/bom2.png"));
								} else {
									int a = arr[j2][k];
									if (a == 0)
										moRong(j2, k);
									else if (a == 1) {
										arrButton[j2][k].setIcon(new ImageIcon("image/so1.png"));
									} else if (a == 2) {
										arrButton[j2][k].setIcon(new ImageIcon("image/so2.png"));
									} else if (a == 3) {
										arrButton[j2][k].setIcon(new ImageIcon("image/so3.png"));
									} else if (a == 4) {
										arrButton[j2][k].setIcon(new ImageIcon("image/so4.png"));
									} else if (a == 5) {
										arrButton[j2][k].setIcon(new ImageIcon("image/so5.png"));
									} else if (a == 6) {
										arrButton[j2][k].setIcon(new ImageIcon("image/so6.png"));
									} else if (a == 7) {
										arrButton[j2][k].setIcon(new ImageIcon("image/so7.png"));
									} else if (a == 8) {
										arrButton[j2][k].setIcon(new ImageIcon("image/so8.png"));
									}
									arrEnd[j2][k] = true;
									click[j2][k] = true;
								}
							}
						}
					}
					if (check != 0) {
						moHet(i, j);
						for (int j2 = i - 1; j2 <= i + 1; j2++) {
							for (int k = j - 1; k <= j + 1; k++) {
								if (k >= 0 && k < arr[i].length && j2 >= 0 && j2 < arr.length
										&& arrEnd[j2][k] == false) {
									if (arrCo[j2][k] == true && arr[j2][k] == 10) {
										arrButton[j2][k].setIcon(new ImageIcon("image/co.png"));
									} else if (arrCo[j2][k] == true && arr[j2][k] != 10) {
										arrButton[j2][k].setIcon(new ImageIcon("image/co2.png"));
									} else if (arrCo[j2][k] == false && arr[j2][k] == 10) {
										arrButton[j2][k].setIcon(new ImageIcon("image/bom2.png"));
									} else {
										int a = arr[j2][k];
										if (a == 0)
											moRong(j2, k);
										else if (a == 1) {
											arrButton[j2][k].setIcon(new ImageIcon("image/so1.png"));
										} else if (a == 2) {
											arrButton[j2][k].setIcon(new ImageIcon("image/so2.png"));
										} else if (a == 3) {
											arrButton[j2][k].setIcon(new ImageIcon("image/so3.png"));
										} else if (a == 4) {
											arrButton[j2][k].setIcon(new ImageIcon("image/so4.png"));
										} else if (a == 5) {
											arrButton[j2][k].setIcon(new ImageIcon("image/so5.png"));
										} else if (a == 6) {
											arrButton[j2][k].setIcon(new ImageIcon("image/so6.png"));
										} else if (a == 7) {
											arrButton[j2][k].setIcon(new ImageIcon("image/so7.png"));
										} else if (a == 8) {
											arrButton[j2][k].setIcon(new ImageIcon("image/so8.png"));
										}
										arrEnd[j2][k] = true;
										click[j2][k] = true;
									}
								}
							}
						}
						end();
					}
				}
			}
		}

		// xử lý teext area
		int t = 0;
		for (int k = 0; k < arrCo.length; k++) {
			for (int k2 = 0; k2 < arrCo[k].length; k2++) {
				if (arrCo[k][k2] == true)
					t++;
			}
		}
		ta.setText((soMin - t) + " ");
		t = 0;

		// Xử lý win

		int count = arr.length * arr[0].length;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arrEnd[i][j] == true)
					count--;
			}
		}
		if (count == soMin) {
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == 10) {
						arrButton[i][j].setIcon(new ImageIcon("image/co.png"));
					}
				}
			}
			bt1.setIcon(new ImageIcon("image/10.png"));
			int a = JOptionPane.showConfirmDialog(null, "You Win !! Play again?", "END GAME", JOptionPane.YES_NO_OPTION);
			if (a == JOptionPane.YES_OPTION) {
				setVisible(false);
				new DoMin(arrButton, soMin);
			}
		}
	}

	public boolean checkCo(int i, int j) {
		int count = 0;
		for (int j2 = i - 1; j2 <= i + 1; j2++) {
			for (int k = j - 1; k <= j + 1; k++) {
				if (j2 >= 0 && j2 < arr.length && k >= 0 && k < arr[i].length)
					if (arrCo[j2][k] == true) {
						count++;
					}
			}
		}
		if (count == arr[i][j])
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		JButton[][] arr = new JButton[10][10];
		int soMin = 10;
		DoMin test = new DoMin(arr, soMin);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
