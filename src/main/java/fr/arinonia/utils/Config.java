package fr.arinonia.utils;

import fr.arinonia.AriLibFX;

import java.io.*;
import java.util.Properties;

public class Config
{
	private File file;
	private Properties properties;

	public Config()
	{
		this.properties = new Properties();

		if(this.file != null)
		{
			if (this.file.exists())
			{
				try
				{
					this.load();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	public Config(File file)
	{
		this.properties = new Properties();
		this.file = file;

		if (this.file.exists())
		{
			try
			{
				this.load();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void set(String key, String value)
	{
		this.properties.setProperty(key, value);

		try {
			this.save();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String get(String key)
	{
		return properties.getProperty(key);
	}

	public String get(String key, String def)
	{
		String value = properties.getProperty(key);
		return value == null ? def : value;
	}

	public void save() throws IOException
	{
		try
		{
			properties.store(new BufferedWriter(new FileWriter(file)), AriLibFX.getProgramName()+" | developed by arinonia");
		} catch (Throwable t)
		{
			throw new IOException("Impossible de sauvegarder le fichier config", t);
		}
	}

	public void load() throws IOException
	{
		try
		{
			properties.load(new FileInputStream(file));
		} catch (Throwable t)
		{
			throw new IOException("Impossible de charger le fichier config", t);
		}
	}
	
	public void setRootDirectory(File file)
	{
		this.file = file;
	}
}
