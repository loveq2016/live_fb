package com.fsmeeting.live.test.client;

import org.junit.Test;

import com.fastonz.live.sliceprotocol.model.LiveRoomModelHolder;

public class LiveRoomServiceTest extends LiveClientTest {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(LiveRoomServiceTest.class);
	// private static final Random RMD = new Random();

	@Test
	public void getLiveRoom() {
		int liveRoomId = 1;
		LiveRoomModelHolder room = new LiveRoomModelHolder();
		liveRoomService.getLiveRoom(liveRoomId, room);
		System.out.println(room.value.getConfRoomAddr());
	}

	@Test
	public void activeLiveRoom() {
		liveRoomService.activeLiveRoom(1);
	}
	
	@Test
	public void releaseLiveRoom() {
		liveRoomService.releaseLiveRoom(1);
	}
}
