package com.gongyu.application.distribute.game.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gongyu.service.distribute.game.common.enums.CommEnum;
import com.gongyu.service.distribute.game.common.enums.PayStatusEnum;
import com.gongyu.service.distribute.game.model.entity.Config;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import com.gongyu.service.distribute.game.model.entity.Users;
import com.gongyu.service.distribute.game.service.ConfigService;
import com.gongyu.service.distribute.game.service.PigOrderService;
import com.gongyu.service.distribute.game.service.UsersService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Slf4j
@Aspect
@Component
@ServerEndpoint(value = "/websocket/{code}")
public class WebSocketServerController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private PigOrderService pigOrderService;
	@Autowired
	private ConfigService configService;
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<WebSocketServerController> webSocketSet = new CopyOnWriteArraySet<WebSocketServerController>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private Integer code;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(@PathParam("code") Integer code, Session session) {
		this.session = session;
		this.code = code;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		log.info("有新连接加入！当前在线人数为" + getOnlineCount());
		try {
			JSONObject json = new JSONObject();
			json.put("message", "连接成功");
			json.put("isMessage", true);
			sendMessage(json.toString());
		} catch (IOException e) {
			log.error("websocket IO异常");
		}
	}

	@Scheduled(fixedRate = 5000)
	public void runTask() {
		try {
			sendInfo("订单状态跟踪 task延时执行");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Scheduled(fixedRate = 6000) 
//	public void runTask2() {
//		try {
//			sendInfo2("版本check");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	// //连接打开时执行
	// @OnOpen
	// public void onOpen(@PathParam("user") String user, Session session) {
	// currentUser = user;
	// System.out.println("Connected ... " + session.getId());
	// }

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("来自客户端的消息:" + message);

		// 群发消息
		for (WebSocketServerController item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 群发自定义消息
	 */
	public void sendInfo(String message) throws IOException {
		List<Users> allUsers = usersService.list();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		Long startTime = zero.getTime() / 1000;
		List<PigOrder> pigOrders = pigOrderService.list(new QueryWrapper<PigOrder>().ge("establish_time", startTime));

		for (WebSocketServerController item : webSocketSet) {
			try {
				Users user = (Users) CollectionUtils.find(allUsers,
						new BeanPropertyValueEqualsPredicate("code", item.code));

				Long userId = user.getId();
				// 获取需要付款的记录
				List<PigOrder> userShouldPayPigOrders = pigOrders.stream().filter(order -> {
					return userId.equals(order.getPurchaseUserId())
							&& order.getPayStatus() == PayStatusEnum.PROCESSING.getCode();
				}).collect(Collectors.toList());
				// 获取需要确认的记录
				List<PigOrder> userShouldConfirmOrders = pigOrders.stream().filter(order -> {
					return userId.equals(order.getSellUserId())
							&& order.getPayStatus() == PayStatusEnum.SUCCESS.getCode()
							&& order.getSellConfirmStatus() == CommEnum.FALSE.getCode();
				}).collect(Collectors.toList());

				JSONObject json = new JSONObject();
				json.put("shouldPayOrderCount", userShouldPayPigOrders.size());
				json.put("shouldConfirmOrderCount", userShouldConfirmOrders.size());
				json.put("allCount", userShouldConfirmOrders.size() + userShouldPayPigOrders.size());
				item.sendMessage(json.toString());
			} catch (IOException e) {
				continue;
			}
		}
	}

//	private void sendInfo2(String message) throws IOException  {
//		Config config = configService.getOne(new QueryWrapper<Config>().eq("config_name", "app_version"));
//		String value = config.getConfigValue();
//		for (WebSocketServerController item : webSocketSet) {
//			try {
//				JSONObject json = new JSONObject();
//				json.put("type", 1);
//				json.put("data", value);
//				item.sendMessage(json.toString());
//			} catch (IOException e) {
//				continue;
//			}
//		}
//	}
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketServerController.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketServerController.onlineCount--;
	}
}
