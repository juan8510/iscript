package com.lezo.iscript.yeam.client;

import org.apache.log4j.Logger;

import com.lezo.iscript.yeam.ObjectBuilder;
import com.lezo.iscript.yeam.strategy.Strategyable;

public class TaskExecuter extends AbstractWorker {
	private static Logger log = Logger.getLogger(TaskExecuter.class);

	@Override
	public void doExecute() throws Exception {
		String className = "com.lezo.iscript.yeam.client.strategy.impl.ExecuteStrategyImpl";
		try {
			Strategyable strategy = (Strategyable) ObjectBuilder.newObject(className, true);
			strategy.doStrategy();
		} catch (Exception e) {
			log.warn(className, e);
			throw e;
		}
	}
}