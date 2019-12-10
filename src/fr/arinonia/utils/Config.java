package fr.arinonia.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import fr.arinonia.AriLibFX;

public class Config {

	private File file;
	private Properties properties;
	public Config() {
		this.properties = new Properties();

		if (file.exists())
			try {
				load();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public Config(File file) {
		this.properties = new Properties();
		this.file = file;
		if (file.exists())
			try {
				load();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void set(String key, String value) {
		properties.setProperty(key, value);
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String get(String key) {
		return properties.getProperty(key);
	}

	public String get(String key, String def) {
		String value = properties.getProperty(key);
		return value == null ? def : value;
	}

	public void save() throws IOException {
		try {
			properties.store(new BufferedWriter(new FileWriter(file)), AriLibFX.getProgramName()+" | developed by arinonia");
		} catch (Throwable t) {
			throw new IOException("Can't save the config", t);
		}
	}

	public void load() throws IOException {
		try {
			properties.load(new FileInputStream(file));
		} catch (Throwable t) {
			throw new IOException("Can't load the config", t);
		}
	}
	
	public void setRootDirectory(File file) {
		this.file = file;
	}
}
