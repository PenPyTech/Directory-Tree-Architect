package com.penpytech.DTA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JFileChooser;

public class DTAController implements DTAControllerInterface {
	private FileWriter LogFile;

	private FileWriter getLogWriter(Date LogTime) throws IOException {
		String time_now = new SimpleDateFormat(Messages.getString("DTAController.0")).format(LogTime); //$NON-NLS-1$
		File my_docs = new File(System.getProperty(Messages.getString("DTAController.1")) + File.separator + Messages.getString("DTAController.2") + File.separator //$NON-NLS-1$ //$NON-NLS-2$
				+ Messages.getString("DTAController.3") + time_now + Messages.getString("DTAController.4")); //$NON-NLS-1$ //$NON-NLS-2$
		if (my_docs.exists()) {
			LogFile = new FileWriter(my_docs, true);
			LogFile.write(Messages.getString("DTAController.5")); //$NON-NLS-1$
		} else {
			LogFile = new FileWriter(my_docs);
		}
		return LogFile;
	}

	public void LogFileclose() throws IOException {
		this.LogFile.close();
	}

	public void dispatcher_function(String event, File source_dir, File dest_dir) throws IOException {
		Date currently = new Date(0);
		this.LogFile = getLogWriter(currently);
		String time_now = new SimpleDateFormat(Messages.getString("DTAController.6")).format(currently); //$NON-NLS-1$
		LogFile.write(Messages.getString("DTAController.7") + time_now); //$NON-NLS-1$
		LogFile.write(Messages.getString("DTAController.8") + event); //$NON-NLS-1$
		LogFile.write(Messages.getString("DTAController.9") + source_dir.getAbsolutePath() + Messages.getString("DTAController.10")); //$NON-NLS-1$ //$NON-NLS-2$
		if (!event.equals(Messages.getString("DTAController.11")) & !event.equals(Messages.getString("DTAController.12"))) { //$NON-NLS-1$ //$NON-NLS-2$
			LogFile.write(Messages.getString("DTAController.13") + dest_dir.getAbsolutePath() + Messages.getString("DTAController.14")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		process(source_dir, event, source_dir, dest_dir);
		LogFile.flush();
		source_dir = null;
		dest_dir = null;
	}

	public boolean get_directory(javax.swing.JTextField target_field) {
		boolean result = false;
		JFileChooser pathesfile = new JFileChooser(Messages.getString("DTAController.15")); //$NON-NLS-1$
		pathesfile.changeToParentDirectory();
		pathesfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int d_result = pathesfile.showOpenDialog(null);
		if (d_result == JFileChooser.APPROVE_OPTION) {
			try {
				target_field.setText(pathesfile.getSelectedFile().getCanonicalPath());
				result = true;
			} catch (Exception e) {
				target_field.setText(e.getMessage());
			}
		}
		return result;
	}

	public void compare(File to_comp, File source_dir, File dest_dir) throws IOException {
		File Target_check = new File(
				to_comp.getAbsolutePath().replace(source_dir.getAbsolutePath(), dest_dir.getAbsolutePath()));
		if (Target_check.exists()) {
			if (Target_check.length() == to_comp.length()) {
				{
					if (to_comp.delete()) {
						LogFile.write(Messages.getString("DTAController.16") + to_comp.getAbsolutePath().toString()); //$NON-NLS-1$
					} else {
						LogFile.write(Messages.getString("DTAController.17")); //$NON-NLS-1$
					}
				}
			} else {
				LogFile.write(
						Messages.getString("DTAController.18") + Target_check.getAbsolutePath().toString()); //$NON-NLS-1$
			}
		}
	}

	public void rename(File to_rename) throws IOException {
		byte[] target = new byte[500], source = to_rename.getName().getBytes();
		int limit = 0;
		target[0] = source[0];
		for (int i = 1; i < source.length - 4; i++) {
			if (source[i] > 64 & source[i] < 91) {
				target[i + limit] = 32;
				limit++;
			}
			target[i + limit] = source[i];
		}
		for (int i = source.length - 4; i < source.length; i++)
			target[i + limit] = source[i];
		String result = Messages.getString("DTAController.19"); //$NON-NLS-1$
		for (int i = 0; i < source.length + limit; i++)
			result += (char) target[i];
		result = to_rename.getParent() + Messages.getString("DTAController.20") + result.replace(Messages.getString("DTAController.21"), Messages.getString("DTAController.22")).replace(Messages.getString("DTAController.23"), Messages.getString("DTAController.24")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		File newName = new File(result);
		if (to_rename.renameTo(newName))
			LogFile.write(Messages.getString("DTAController.25") + to_rename.getName() + Messages.getString("DTAController.26") + newName.getName() + Messages.getString("DTAController.27")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		else
			LogFile.write(Messages.getString("DTAController.28") + to_rename.getName() + Messages.getString("DTAController.29")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void process(File tbc, String action, File source_dir, File dest_dir) throws IOException {
		if (tbc.exists()) {
			if (tbc.isDirectory()) {
				if (action.equals(Messages.getString("DTAController.30"))) { //$NON-NLS-1$
					File Target_copy = new File(
							tbc.getAbsolutePath().replace(source_dir.getAbsolutePath(), dest_dir.getAbsolutePath()));
					if (!Target_copy.exists())
						if (Target_copy.mkdirs())
							LogFile.write(Messages.getString("DTAController.31") + Target_copy.toString() + Messages.getString("DTAController.32")); //$NON-NLS-1$ //$NON-NLS-2$
				}

				String[] source_list = tbc.list();
				int index = 0;
				while (index < source_list.length) {
					File child = new File(tbc.getAbsolutePath(), source_list[index]);
					process(child, action.toString(), source_dir, dest_dir);
					if (action.equals(Messages.getString("DTAController.33")) & child.isDirectory()) { //$NON-NLS-1$
						if (child.delete()) {
							LogFile.write(Messages.getString("DTAController.34") + child.getName() + Messages.getString("DTAController.35")); //$NON-NLS-1$ //$NON-NLS-2$
						} else
							LogFile.write(Messages.getString("DTAController.36") + child.getName() + Messages.getString("DTAController.37")); //$NON-NLS-1$ //$NON-NLS-2$
					}
					index++;
				}
			} else {
				if (!action.equals(Messages.getString("DTAController.38"))) { //$NON-NLS-1$
					if (action.equals(Messages.getString("DTAController.39"))) //$NON-NLS-1$
						rename(tbc);
					else
						compare(tbc, source_dir, dest_dir);
				}
			}
		}
	}

}
