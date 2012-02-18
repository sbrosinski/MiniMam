package mam.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.ehcache.CacheManager;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.log4j.Logger;

public class MamInitListener implements ServletContextListener
{

	private static Logger logger = Logger.getLogger(MamInitListener.class.getName());


	public void contextInitialized(ServletContextEvent sce)
	{
		initConfig();
		CacheManager.create();
	}

	public void contextDestroyed(ServletContextEvent sce)
	{
		// nop
	}

	private void initConfig()
	{
		InputStream configStream = this.getClass().getClassLoader().getResourceAsStream("mam-config.txt");
		try
		{
			// strip out comments before parsing as json
			StringBuilder config = new StringBuilder();
			List<String> configLines = IOUtils.readLines(configStream);
			for (String line : configLines)
			{
				if (!line.startsWith("#"))
				{
					config.append(line);
				}
			}

			Gson gson = new GsonBuilder().create();
			MamConfig gsaConfig = gson.fromJson(config.toString(), MamConfig.class);
			MamConfigManager.get().setConfig(gsaConfig);

			logger.info("Successfully loaded config from mam-config.txt, settings loaded:");
			logger.info(gsaConfig.toString());

		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("Couldn't init config.", e);
		}
		finally
		{
			IOUtils.closeQuietly(configStream);
		}
	}




}
