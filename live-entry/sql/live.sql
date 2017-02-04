CREATE DATABASE `live` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `t_liveroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `liveName` varchar(64) NOT NULL COMMENT '直播名称',
  `startTime` datetime NOT NULL COMMENT '开始时间',
  `endTime` datetime NOT NULL COMMENT '结束时间',
  `maxUserCount` int(11) DEFAULT NULL COMMENT '允许最大用户数',
  `verifyMode` tinyint(1) DEFAULT '0' COMMENT '1：需要邀请码。0：不需要',
  `inviteCode` varchar(9) DEFAULT NULL COMMENT '邀请码',
  `confRoomId` int(11) DEFAULT NULL COMMENT '会议室ID',
  `directorId` int(11) DEFAULT NULL COMMENT '导播ID',
  `liveURI` varchar(64) DEFAULT NULL COMMENT '直播URI',
  `liveAddress` varchar(64) DEFAULT NULL COMMENT '直播地点',
  `liveInfo` varchar(1024) DEFAULT NULL COMMENT '直播信息',
  `companyId` int(11) DEFAULT NULL COMMENT '企业ID',
  `depId` int(11) DEFAULT NULL COMMENT '部门ID',
  `creationTime` datetime DEFAULT NULL COMMENT '创建时间',
  `creatorId` int(11) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='直播房间静态信息表';
