package com.lezo.iscript.yeam.server.event;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lezo.iscript.service.crawler.dto.SessionHisDto;
import com.lezo.iscript.utils.JSONUtils;
import com.lezo.iscript.yeam.io.IoRequest;
import com.lezo.iscript.yeam.resultmgr.ResultHandlerCaller;
import com.lezo.iscript.yeam.resultmgr.ResultHandlerWoker;
import com.lezo.iscript.yeam.writable.ResultWritable;

public class DataAnalyzer implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(DataAnalyzer.class);
	private IoRequest ioRequest;
	private IoSession ioSession;

	public DataAnalyzer(IoRequest ioRequest, IoSession ioSession) {
		super();
		this.ioRequest = ioRequest;
		this.ioSession = ioSession;
	}

	@Override
	public void run() {
		Object rsObject = ioRequest.getData();
		@SuppressWarnings("unchecked")
		List<ResultWritable> rWritables = (List<ResultWritable>) rsObject;
		if (!CollectionUtils.isEmpty(rWritables)) {
			add2Session(ioSession, rWritables);
			// addTrackSession(event.getSession());
			ResultHandlerCaller caller = ResultHandlerCaller.getInstance();
			JSONObject hObject = JSONUtils.getJSONObject(ioRequest.getHeader());
			caller.execute(new ResultHandlerWoker(rWritables));
			ThreadPoolExecutor exec = caller.getExecutor();
			String msg = String.format("add %d result.Client:%s,Result.Caller[active:%d,Largest:%d,Queue:%d]",
					rWritables.size(), JSONUtils.getString(hObject, "name"), exec.getActiveCount(),
					exec.getLargestPoolSize(), exec.getQueue().size());
			logger.info(msg);
		}
	}

	private void add2Session(IoSession session, List<ResultWritable> rWritables) {
		int success = 0;
		int fail = 0;
		for (ResultWritable rw : rWritables) {
			if (ResultWritable.RESULT_SUCCESS == rw.getStatus()) {
				success++;
			} else {
				fail++;
			}
		}
		add2Attribute(session, SessionHisDto.SUCCESS_NUM, success);
		add2Attribute(session, SessionHisDto.FAIL_NUM, fail);
	}

	public void add2Attribute(IoSession session, String key, int num) {
		int newValue = (Integer) session.getAttribute(key) + num;
		session.setAttribute(key, newValue);
	}
}