package com.lezo.iscript.yeam.server.event.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lezo.iscript.yeam.io.IoRequest;
import com.lezo.iscript.yeam.server.event.RequestEvent;

public abstract class AbstractEventHandler implements RequestEventHandler {
	private static Logger logger = LoggerFactory.getLogger(AbstractEventHandler.class);

	@Override
	public void handle(RequestEvent event) {
		try {
			if (!isAccept(event)) {
				return;
			}
			doHandle(event);
		} catch (Exception e) {
			String msg = "Cause from " + this.getClass().getName() + ",\n" + ExceptionUtils.getStackTrace(e);
			logger.warn(msg);
		}
	}

	protected abstract void doHandle(RequestEvent event);

	protected boolean isAccept(RequestEvent event) {
		return true;
	}

	protected final IoRequest getIoRequest(RequestEvent event) {
		Object dataObject = event.getMessage();
		if (dataObject == null) {
			return null;
		}
		if (dataObject instanceof IoRequest) {
		} else {
			return null;
		}
		return (IoRequest) dataObject;
	}

}