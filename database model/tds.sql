CREATE DATABASE  IF NOT EXISTS `tds`;
USE `tds`;

/* create client table*/
DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `clientId` int(11) NOT NULL AUTO_INCREMENT,
  `hostName` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  PRIMARY KEY (`clientId`),
  UNIQUE KEY `clientId_UNIQUE` (`clientId`)
);


/* create task table*/
DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `taskId` int(11) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(45) NOT NULL,
  `taskParameter` json DEFAULT NULL,
  `taskPath` varchar(100) NOT NULL,
  `taskState` enum('PENDING','IN_PROGRESS','COMPLETED') NOT NULL,
  `userID` int(11) NOT NULL,
  `assignedNodeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  UNIQUE KEY `taskId_UNIQUE` (`taskId`),
  KEY `clientId_idx` (`userID`),
  KEY `nodeId_idx` (`assignedNodeId`),
  CONSTRAINT `clientId` FOREIGN KEY (`userID`) REFERENCES `client` (`clientid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `nodeId` FOREIGN KEY (`assignedNodeId`) REFERENCES `node` (`nodeid`)
) ;


/* create node table*/
DROP TABLE IF EXISTS `node`;

CREATE TABLE `node` (
   `nodeId` int(11) NOT NULL AUTO_INCREMENT,
  `nodeIp` int(11) unsigned NOT NULL,
  `nodePort` int(11) NOT NULL,
  `nodeStatus` enum('AVAILABLE','BUSY','NOT_OPERATIONAL') NOT NULL,
  PRIMARY KEY (`nodeId`),
  UNIQUE KEY `nodeId_UNIQUE` (`nodeId`),
  UNIQUE KEY `nodeIp_UNIQUE` (`nodeIp`)
);


/* create taskresult table*/
DROP TABLE IF EXISTS `taskresult`;

CREATE TABLE `taskresult` (
  `taskId` int(11) NOT NULL,
  `taskOutcome` enum('SUCCESS','FAILED') NOT NULL,
  `taskErrorCode` int(11) DEFAULT NULL,
  `taskErrorMsg` varchar(100) DEFAULT NULL,
  `taskResultBuffer` mediumblob,
  PRIMARY KEY (`taskId`),
  UNIQUE KEY `taskID_UNIQUE` (`taskId`),
  CONSTRAINT `taskIdKey` FOREIGN KEY (`taskId`) REFERENCES `task` (`taskId`) ON DELETE CASCADE
) ;

