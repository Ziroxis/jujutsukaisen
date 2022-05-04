package com.example.jujutsukaisen;


import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeoDebug
{
	private static Logger logger = Logger.getLogger(Main.MODID);
	
	public static boolean isDebug()
	{
		return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
	}

	// Of course this is from StackOverflow what were you expecting ? 
	// https://stackoverflow.com/a/11306854
	public static String getCallerClassName()
	{
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++)
		{
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(BeoDebug.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0)
			{
				return ste.getClassName();
			}
		}
		return null;
	}

	public static void debug(Object msg)
	{
		if(isDebug())
			logger.log(Level.INFO, getCallerClassName() + ": " + String.valueOf(msg));
	}
}
