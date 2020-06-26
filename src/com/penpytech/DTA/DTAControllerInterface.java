package com.penpytech.DTA;

import java.io.File;
import java.io.IOException;

public interface DTAControllerInterface {
	public boolean get_directory(javax.swing.JTextField target_field);

	public void LogFileclose() throws IOException;

	public void dispatcher_function(String event, File source_dir, File dest_dir) throws IOException;

}
