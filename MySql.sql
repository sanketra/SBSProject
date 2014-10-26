CREATE TABLE `pitchforkbank`.`user` (
  `userId` VARCHAR(36) NOT NULL,
  `emailId` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `dob` DATE NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(10) NOT NULL,
  `ssn` VARCHAR(45) NOT NULL,
  `phoneno` VARCHAR(45) NOT NULL,
  `enabled` INT NOT NULL,
  `ques1` VARCHAR(100) NOT NULL,
  `answer1` VARCHAR(100) NOT NULL,
  `ques2` VARCHAR(100) NOT NULL,
  `answer2` VARCHAR(100) NOT NULL,
  `ques3` VARCHAR(100) NOT NULL,
  `answer3` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `emailId_UNIQUE` (`emailId` ASC),
  UNIQUE INDEX `ssn_UNIQUE` (`ssn` ASC),
  UNIQUE INDEX `phoneno_UNIQUE` (`phoneno` ASC));




CREATE TABLE `pitchforkbank`.`account` (
  `userId` VARCHAR(36) NOT NULL,
  `accountNum` INT NOT NULL,
  `accountType` VARCHAR(45) NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`accountNum`),
  CONSTRAINT `userId`
    FOREIGN KEY (`userId`)
    REFERENCES `pitchforkbank`.`user` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);



CREATE TABLE `pitchforkbank`.`requests` (
  `fromUserId` VARCHAR(36) NOT NULL,
  `toUserId` VARCHAR(36) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`fromUserId`, `toUserId`),
  INDEX `toUserId_idx` (`toUserId` ASC),
  CONSTRAINT `fromUserId`
    FOREIGN KEY (`fromUserId`)
    REFERENCES `pitchforkbank`.`user` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `toUserId`
    FOREIGN KEY (`toUserId`)
    REFERENCES `pitchforkbank`.`user` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `pitchforkbank`.`transaction` (
  `transactionId` VARCHAR(36) NOT NULL,
  `fromAcountNum` INT NOT NULL,
  `toAccountNum` INT NOT NULL,
  `transactionType` VARCHAR(45) NOT NULL,
  `transactionAmount` INT NOT NULL,
  `transactionTime` DATETIME NOT NULL,
  `transactionStatus` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`transactionId`),
  INDEX `fromAccountNum_idx` (`fromAcountNum` ASC),
  INDEX `toAccountNum_idx` (`toAccountNum` ASC),
  CONSTRAINT `fromAccountNum`
    FOREIGN KEY (`fromAcountNum`)
    REFERENCES `pitchforkbank`.`account` (`accountNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `toAccountNum`
    FOREIGN KEY (`toAccountNum`)
    REFERENCES `pitchforkbank`.`account` (`accountNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `pitchforkbank`.`userotp` (
  `userId` VARCHAR(36) NOT NULL,
  `oneTimePassword` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `oneTimePassword_UNIQUE` (`oneTimePassword` ASC),
  CONSTRAINT `userId_otp`
    FOREIGN KEY (`userId`)
    REFERENCES `pitchforkbank`.`user` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `pitchforkbank`.`user_public_key` (
  `userId` VARCHAR(36) NOT NULL,
  `public key` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `public key_UNIQUE` (`public key` ASC));

ALTER TABLE `pitchforkbank`.`user` 
ADD COLUMN `role` VARCHAR(45) NOT NULL AFTER `phoneno`;