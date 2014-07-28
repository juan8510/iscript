package com.lezo.iscript.yeam.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lezo.iscript.common.storage.StorageBuffer;
import com.lezo.iscript.common.storage.StorageBufferFactory;
import com.lezo.iscript.service.crawler.dto.SessionHisDto;
import com.lezo.iscript.service.crawler.service.SessionHisService;
import com.lezo.iscript.spring.context.SpringBeanUtils;
import com.lezo.iscript.utils.JSONUtils;
import com.lezo.iscript.yeam.io.IoRequest;
import com.lezo.iscript.yeam.server.event.RequestProceser;
import com.lezo.iscript.yeam.server.event.RequestWorker;

public class IoServer extends IoHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(IoServer.class);
	private IoAcceptor acceptor;
	private StorageBuffer<SessionHisDto> storageBuffer;

	public IoServer(int port) throws IOException {
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		if (logger.isDebugEnabled()) {
			acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		}
		acceptor.setHandler(this);
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(port));
		this.storageBuffer = StorageBufferFactory.getStorageBuffer(SessionHisDto.class);
		SessionHisService sessionHisService = SpringBeanUtils.getBean(SessionHisService.class);
		sessionHisService.updateUpSessionToInterrupt();
		logger.info("start to listener port:" + port + " for IoServer..");
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		add2Attribute(session, SessionHisDto.REQUEST_SIZE, 1);
		if (message == null) {
			return;
		}
		addNewSession(session, message);
		RequestProceser.getInstance().execute(new RequestWorker(session, message));
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		add2Attribute(session, SessionHisDto.RESPONE_SIZE, 1);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		resetSession(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		SessionHisDto downDto = getSessionHisDto(session);
		if (!StringUtils.isEmpty(downDto.getClienName())) {
			logger.warn(String.format("close: %s", downDto));
			downDto.setStatus(SessionHisDto.STATUS_DOWN);
			this.storageBuffer.add(downDto);
		} else {
			logger.warn(String.format("close client,not found name for sessionId:%s", downDto.getSessionId()));
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		String key = SessionHisDto.ERROR_SIZE;
		int newValue = (Integer) session.getAttribute(key) + 1;
		session.setAttribute(key, newValue);
	}

	public void add2Attribute(IoSession session, String key, int num) {
		int newValue = (Integer) session.getAttribute(key) + num;
		session.setAttribute(key, newValue);
	}

	private void addNewSession(IoSession session, Object message) {
		if (message instanceof IoRequest && !session.containsAttribute(SessionHisDto.CLIEN_NAME)) {
			IoRequest firstRequest = (IoRequest) message;
			JSONObject hObject = JSONUtils.getJSONObject(firstRequest.getHeader());
			String name = JSONUtils.getString(hObject, SessionHisDto.CLIEN_NAME);
			if (!StringUtils.isEmpty(name)) {
				logger.info(String.format("add new client:%s", name));
				session.setAttribute(SessionHisDto.CLIEN_NAME, name);
				SessionHisDto newDto = getSessionHisDto(session);
				newDto.setStatus(SessionHisDto.STATUS_UP);
				this.storageBuffer.add(newDto);
			} else {
				logger.warn(String.format("can not found name from %s", hObject));
			}
		}
	}

	private SessionHisDto getSessionHisDto(IoSession session) {
		SessionHisDto dto = new SessionHisDto();
		String clientName = (String) session.getAttribute(SessionHisDto.CLIEN_NAME);
		dto.setCreateTime(new Date());
		dto.setUpdateTime(dto.getCreateTime());
		dto.setSessionId((String) session.getAttribute(SessionHisDto.SESSION_ID));
		dto.setClienName(clientName);
		dto.setRequestSize((Integer) session.getAttribute(SessionHisDto.REQUEST_SIZE));
		dto.setResponeSize((Integer) session.getAttribute(SessionHisDto.RESPONE_SIZE));
		dto.setErrorSize((Integer) session.getAttribute(SessionHisDto.ERROR_SIZE));
		dto.setSuccessNum((Integer) session.getAttribute(SessionHisDto.SUCCESS_NUM));
		dto.setFailNum((Integer) session.getAttribute(SessionHisDto.FAIL_NUM));
		return dto;
	}

	private void resetSession(IoSession session) {
		String newSessionId = UUID.randomUUID().toString();
		session.setAttribute(SessionHisDto.SESSION_ID, newSessionId);
		session.setAttribute(SessionHisDto.REQUEST_SIZE, 0);
		session.setAttribute(SessionHisDto.RESPONE_SIZE, 0);
		session.setAttribute(SessionHisDto.ERROR_SIZE, 0);
		session.setAttribute(SessionHisDto.SUCCESS_NUM, 0);
		session.setAttribute(SessionHisDto.FAIL_NUM, 0);
		session.setAttribute(SessionHisDto.SAVE_STAMP, System.currentTimeMillis());
	}

}