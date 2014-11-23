package com.lezo.iscript.yeam.server.event.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.mina.core.future.WriteFuture;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lezo.iscript.utils.JSONUtils;
import com.lezo.iscript.yeam.io.IoConstant;
import com.lezo.iscript.yeam.io.IoRequest;
import com.lezo.iscript.yeam.io.IoRespone;
import com.lezo.iscript.yeam.server.event.ClientEvent;
import com.lezo.iscript.yeam.tasker.cache.TaskCacher;
import com.lezo.iscript.yeam.tasker.cache.TaskQueue;
import com.lezo.iscript.yeam.writable.TaskWritable;

public class TaskLackEventHandler extends AbstractEventHandler {
	private static Logger logger = LoggerFactory.getLogger(TaskLackEventHandler.class);
	private static final int PER_OFFER_SIZE = 20;
	private static final int MIN_TASK_SIZE = 5;

	@Override
	protected void doHandle(ClientEvent event) {
		long start = System.currentTimeMillis();
		IoRequest request = getIoRequest(event);
		JSONObject hObject = JSONUtils.getJSONObject(request.getHeader());

		TaskCacher taskCancher = TaskCacher.getInstance();
		List<String> typeList = taskCancher.getNotEmptyTypeList();
		List<TaskWritable> taskOffers = new ArrayList<TaskWritable>(PER_OFFER_SIZE);
		int limit = 0;
		if (!typeList.isEmpty()) {
			// shuffle type to offer task random
			Collections.shuffle(typeList);
			logger.info(String.format("Ready type:%s", typeList));
			int cycle = 0;
			limit = getLimitSize(typeList);
			int remain = PER_OFFER_SIZE;
			while (remain > 0 && ++cycle <= 3) {
				for (String type : typeList) {
					TaskQueue taskQueue = taskCancher.getQueue(type);
					limit = limit > remain ? remain : limit;
					synchronized (taskQueue) {
						List<TaskWritable> taskList = taskQueue.pollDecsLevel(limit);
						if (!CollectionUtils.isEmpty(taskList)) {
							remain -= taskList.size();
							taskOffers.addAll(taskList);
							if (remain < 1) {
								break;
							}
						}
					}
				}
			}
			if (!taskOffers.isEmpty()) {
				IoRespone ioRespone = new IoRespone();
				ioRespone.setType(IoConstant.EVENT_TYPE_TASK);
				ioRespone.setData(taskOffers);
				WriteFuture writeFuture = event.getSession().write(ioRespone);
				if (!writeFuture.awaitUninterruptibly(IoConstant.WRITE_TIMEOUT)) {
					String msg = "fail to offer tasks:" + taskOffers.size();
					logger.warn(msg, writeFuture.getException());
				}
			}
		}
		long cost = System.currentTimeMillis() - start;
		String msg = String.format("Offer %s task for client:%s,[tactive:%s,Largest:%s,tsize:%s](%s),cost:%s", taskOffers.size(), JSONUtils.getString(hObject, "name"), JSONUtils.getString(hObject, "tactive"), JSONUtils.getString(hObject, "tmax"), JSONUtils.getString(hObject, "tsize"), limit, cost);
		logger.info(msg);

	}

	private int getLimitSize(List<String> typeList) {
		if (typeList.size() < 3) {
			TaskCacher taskCancher = TaskCacher.getInstance();
			int total = 0;
			for (String type : typeList) {
				total += taskCancher.getQueue(type).size();
			}
			if (total < PER_OFFER_SIZE * 20) {
				return 2;
			}
		}
		int limit = PER_OFFER_SIZE / typeList.size();
		limit = limit <= 1 ? 2 : limit;
		return limit;
	}

	@Override
	protected boolean isAccept(ClientEvent event) {
		if (IoConstant.EVENT_TYPE_CONFIG == event.getType()) {
			return false;
		}
		IoRequest ioRequest = getIoRequest(event);
		if (ioRequest == null) {
			return false;
		}
		JSONObject hObject = JSONUtils.getJSONObject(ioRequest.getHeader());
		if (hObject == null) {
			logger.warn("get an empty header..");
			return false;
		}
		Integer tactive = JSONUtils.getInteger(hObject, "tactive");
		Integer tsize = JSONUtils.getInteger(hObject, "tsize");
		if (tactive + tsize < MIN_TASK_SIZE) {
			return true;
		}
		return false;
	}
}