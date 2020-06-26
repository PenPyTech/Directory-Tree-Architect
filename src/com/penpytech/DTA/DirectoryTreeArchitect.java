package com.penpytech.DTA;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DirectoryTreeArchitect {

	private JFrame frmDirectoryTreeArchitect;
	private JTextField tfSourceDir;
	private JTextField tfDestDir;
	private DTAControllerInterface DTACont;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DirectoryTreeArchitect window = new DirectoryTreeArchitect();
					window.frmDirectoryTreeArchitect.setVisible(true);
					JOptionPane.showMessageDialog(window.frmDirectoryTreeArchitect,
							Messages.getString("DirectoryTreeArchitect.0") + System.getProperty(Messages.getString("DirectoryTreeArchitect.1"))); //$NON-NLS-1$ //$NON-NLS-2$
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DirectoryTreeArchitect() {
		initialize();
	}

	private void EventInputValidator(String event) {
		if (tfSourceDir.getText().equals(Messages.getString("DirectoryTreeArchitect.2")) | (event.equals(Messages.getString("DirectoryTreeArchitect.3")) & tfDestDir.getText().equals(Messages.getString("DirectoryTreeArchitect.4")))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			String error = (tfSourceDir.getText().equals(Messages.getString("DirectoryTreeArchitect.5"))) ? Messages.getString("DirectoryTreeArchitect.6") : Messages.getString("DirectoryTreeArchitect.7"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			JOptionPane.showMessageDialog(this.frmDirectoryTreeArchitect,
					Messages.getString("DirectoryTreeArchitect.8") + error + Messages.getString("DirectoryTreeArchitect.9")); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			File source_dir = new File(tfSourceDir.getText());
			File dest_dir = new File(tfDestDir.getText());
			try {
				DTACont.dispatcher_function(event, source_dir, dest_dir);
				JOptionPane.showMessageDialog(this.frmDirectoryTreeArchitect,
						Messages.getString("DirectoryTreeArchitect.10")); //$NON-NLS-1$
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this.frmDirectoryTreeArchitect,
						Messages.getString("DirectoryTreeArchitect.11") + e.getMessage()); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDirectoryTreeArchitect = new JFrame();
		DTACont = new DTAController();
		frmDirectoryTreeArchitect.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					DTACont.LogFileclose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frmDirectoryTreeArchitect.setResizable(false);
		frmDirectoryTreeArchitect.setTitle(Messages.getString("DirectoryTreeArchitect.12")); //$NON-NLS-1$
		frmDirectoryTreeArchitect.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Messages.getString("DirectoryTreeArchitect.13"))); //$NON-NLS-1$
		frmDirectoryTreeArchitect.setBounds(100, 100, 645, 213);
		frmDirectoryTreeArchitect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmDirectoryTreeArchitect.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel(Messages.getString("DirectoryTreeArchitect.14")); //$NON-NLS-1$
		lblNewLabel.setBounds(6, 23, 80, 14);
		panel.add(lblNewLabel);

		tfSourceDir = new JTextField();
		tfSourceDir.setBounds(117, 20, 426, 20);
		panel.add(tfSourceDir);
		tfSourceDir.setColumns(10);

		JButton btnBrowseSource = new JButton(Messages.getString("DirectoryTreeArchitect.15")); //$NON-NLS-1$
		btnBrowseSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTACont.get_directory(tfSourceDir);
			}
		});
		btnBrowseSource.setBounds(549, 19, 72, 23);
		panel.add(btnBrowseSource);

		JLabel lblNewLabel_1 = new JLabel(Messages.getString("DirectoryTreeArchitect.16")); //$NON-NLS-1$
		lblNewLabel_1.setBounds(6, 50, 105, 14);
		panel.add(lblNewLabel_1);

		tfDestDir = new JTextField();
		tfDestDir.setBounds(117, 47, 426, 20);
		panel.add(tfDestDir);
		tfDestDir.setColumns(10);

		JButton btnBrowseDest = new JButton(Messages.getString("DirectoryTreeArchitect.17")); //$NON-NLS-1$
		btnBrowseDest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTACont.get_directory(tfDestDir);
			}
		});
		btnBrowseDest.setBounds(549, 46, 72, 23);
		panel.add(btnBrowseDest);

		JButton btnRename = new JButton(Messages.getString("DirectoryTreeArchitect.18")); //$NON-NLS-1$
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfSourceDir.getText().isEmpty())
					JOptionPane.showMessageDialog(null,
							Messages.getString("DirectoryTreeArchitect.19")); //$NON-NLS-1$
				else
					EventInputValidator(Messages.getString("DirectoryTreeArchitect.20")); //$NON-NLS-1$
			}
		});
		btnRename.setBounds(59, 127, 89, 23);
		panel.add(btnRename);

		JButton btnCopyDirStruct = new JButton(Messages.getString("DirectoryTreeArchitect.21")); //$NON-NLS-1$
		btnCopyDirStruct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfSourceDir.getText().equals(tfDestDir.getText()))
					JOptionPane.showMessageDialog(null, Messages.getString("DirectoryTreeArchitect.22")); //$NON-NLS-1$
				else
					EventInputValidator(Messages.getString("DirectoryTreeArchitect.23")); //$NON-NLS-1$
			}
		});
		btnCopyDirStruct.setBounds(158, 127, 153, 23);
		panel.add(btnCopyDirStruct);

		JButton btnEmptyDirClean = new JButton(Messages.getString("DirectoryTreeArchitect.24")); //$NON-NLS-1$
		btnEmptyDirClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfSourceDir.getText().equals(tfDestDir.getText()))
					JOptionPane.showMessageDialog(null, Messages.getString("DirectoryTreeArchitect.25")); //$NON-NLS-1$
				else
					EventInputValidator(Messages.getString("DirectoryTreeArchitect.26")); //$NON-NLS-1$
			}
		});
		btnEmptyDirClean.setBounds(321, 127, 151, 23);
		panel.add(btnEmptyDirClean);

		JButton btnCompDir = new JButton(Messages.getString("DirectoryTreeArchitect.27")); //$NON-NLS-1$
		btnCompDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfSourceDir.getText().equals(tfDestDir.getText()))
					JOptionPane.showMessageDialog(null, Messages.getString("DirectoryTreeArchitect.28")); //$NON-NLS-1$
				else
					EventInputValidator(Messages.getString("DirectoryTreeArchitect.29")); //$NON-NLS-1$
			}
		});
		btnCompDir.setBounds(494, 127, 89, 23);
		panel.add(btnCompDir);
	}
}
