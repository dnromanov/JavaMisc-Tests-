package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JTextField txtInput;
	private JTextField txtJitstepgmailcom;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtInput = new JTextField();
		txtInput.setText("input/file_example_XLS_1000_NEW.xlsx");
		txtInput.setBounds(94, 23, 231, 20);
		contentPane.add(txtInput);
		txtInput.setColumns(10);
		
		txtJitstepgmailcom = new JTextField();
		txtJitstepgmailcom.setText("j2819itstep@gmail.com");
		txtJitstepgmailcom.setBounds(94, 54, 127, 20);
		contentPane.add(txtJitstepgmailcom);
		txtJitstepgmailcom.setColumns(10);
		
		JLabel lblFilePath = new JLabel("FILE PATH");
		lblFilePath.setBounds(28, 26, 86, 14);
		contentPane.add(lblFilePath);
		
		JLabel lblTo = new JLabel("TO:");
		lblTo.setBounds(28, 57, 46, 14);
		contentPane.add(lblTo);
		
		JButton btnRep = new JButton("REP #1");
		btnRep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRep.setBounds(25, 97, 89, 23);
		contentPane.add(btnRep);
		
		JButton btnRep_1 = new JButton("REP #2");
		btnRep_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRep_1.setBounds(132, 97, 89, 23);
		contentPane.add(btnRep_1);
		
		JButton btnSend = new JButton("SEND");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSend.setBounds(25, 136, 89, 23);
		contentPane.add(btnSend);
	}
}
